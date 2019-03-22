package main;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashSet;

import Mp3Song.Album;
import SearchTag.Search;
import Mp3Song.Artist;
import Exception.DirectoryNotFoundException;
import WriteFile.FileWiterClass;

public class Main {


    public static void main(String[] args) throws IOException, NoSuchAlgorithmException, DirectoryNotFoundException, InterruptedException {
        long startTime = System.currentTimeMillis();

        String[] file = {"D:\\музыка"};
        File myFolder;
        File[] files;

        TagArrays arraysTag = new TagArrays();
        Search search = new Search();

        //Цикл который проходит по директориям и записывает теги,
        //пути и названия песен в массив
        for (String aFile : file) {
            myFolder = new File(aFile);
            if (!myFolder.exists() || !myFolder.isDirectory()) {
                throw new DirectoryNotFoundException(aFile);
            }
            files = myFolder.listFiles();
            search.fileFind(files, arraysTag);
        }


        //Ждем завершение потоков поиска тегов
        for (Thread thread : arraysTag.threads) {
            try {
                thread.join();
            } catch (InterruptedException e) { }
        }


        //создаем объекты артистов
        for (String art : arraysTag.artistHashSet)
            artistObj.add(new Artist(art));


        //создаем объекты альбомов в соотвествующих им объектах артистов
        for (Artist art : artistObj) {
            for (int i = 0; i < arraysTag.artist.size(); i++) {
                // добавлем сначала в hushset потому что у артиста не может быть 2 одинаковы альбома
                if (art.getName().equals(arraysTag.artist.get(i))) {
                    albumHashSet.add(arraysTag.album.get(i));
                }
            }
            for (String alb : albumHashSet)
                art.setAlbum(alb);
            albumHashSet.clear();
        }

        //создаем объекты песен в соотвествующих им объектах артистов и албьомов
        for (Artist art : artistObj) {
            for (Album albCl : art.getAlbum()) {
                for (int i = 0; i < arraysTag.artist.size(); i++) {
                    if (art.getName().equals(arraysTag.artist.get(i)) && albCl.getName().equals(arraysTag.album.get(i))) {
                        albCl.setSong(arraysTag.title.get(i), arraysTag.duration.get(i), arraysTag.location.get(i), arraysTag.tagSumm.get(i));
                    }
                }
            }
        }

        // Цикл нахождения повторяющихся контрольных сумм и запись их в tagSummRecurring
        for (int i = 0; i < arraysTag.tagSumm.size() - 1; i++) {
            for (int j = i + 1; j < arraysTag.tagSumm.size(); j++) {
                if (arraysTag.tagSumm.get(i).equals(arraysTag.tagSumm.get(j))) {
                    tagSummRecurring.add(arraysTag.tagSumm.get(i));
                    arraysTag.tagSumm.remove(j);
                }
            }
        }


        FileWiterClass write = new FileWiterClass();
        // Запись в первый файл информации о артистах их альбомах и песнях
        write.writeAllSong(artistObj);
        //запись во второй файл дубликатов по контрольеой сумме
        write.writeCheckSumm(tagSummRecurring, artistObj);
        //запись в третий файл дубликатов по тегу артиста альбома и названию песни
        write.writeTagDublicate(artistObj);

        long timeSpent = System.currentTimeMillis() - startTime;
        System.out.println("программа выполнялась " + timeSpent + " миллисекунд");

    }

    // листы хранящие уникальные контрольные суммы
    private static ArrayList<String> tagSummRecurring = new ArrayList<>();
    // сет хранящий альбомы
    private static HashSet<String> albumHashSet = new HashSet<>();
    //лист хранящий объекты артистов
    private static ArrayList<Artist> artistObj = new ArrayList<>();

}
