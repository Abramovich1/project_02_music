package WriteFile;

import Mp3Song.Album;
import Mp3Song.Artist;
import Mp3Song.Song;
import org.apache.log4j.Logger;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;

public class FileWiterClass {

    /**
     * Метод для записи всех песен их исполнителей и альбомов в html файл
     * @param artistObj
     */
    public void writeAllSong(ArrayList<Artist> artistObj)
    {
        try {
            String writeFile = "html.html";
            PrintWriter writer = new PrintWriter(writeFile);
            for (Artist art : artistObj) {
                writer.println("<b><font color=\"red\" face=\"Arial\">Исполнитель : </b></font>" + art.getName());
                writer.println("</br>");
                for (Album albCl : art.getAlbum()) {
                    writer.println("<b>Альбом </b>:" + albCl.getName());
                    writer.println("<br>");
                    for (Song sng : albCl.getSong()) {
                        writer.println("<b>Имя: </b>" + sng.getNameSong());


                        float buffer1 = Float.parseFloat(sng.getDurationSong()) / 60000;
                        float buffer2 = Float.parseFloat(sng.getDurationSong()) / 1000;
                        Integer minut = Integer.parseInt(String.valueOf(buffer1).split("\\.")[0]);
                        String second = String.format("%.0f", buffer2 - minut * 60);


                        writer.println("<b>Длительность: </b>" + minut + ":" + second);
                        writer.println("<b>Путь: </b>" + sng.getPathSong());
                        writer.println("<br>");
                        writer.println("<br>");
                    }
                }
                writer.println("<br>");
            }
            writer.flush();
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Метод для записи всех песен с одинаковой контрольной суммой
     * @param artistObj
     */
    public void writeCheckSumm(ArrayList<String> tagSummUni,ArrayList<Artist> artistObj)
    {

        final Logger logmd5 = Logger.getLogger("log1");
        int dublicat=0;
        for(String md: tagSummUni)
        {
            dublicat++;
            logmd5.info("<b>Дубликаты</b> - "+dublicat + "<br>");
            for(Artist art: artistObj) {
                for(Album albCl: art.getAlbum()) {
                    for(Song sng: albCl.getSong()) {
                        if(md.equals( sng.getSummSong()))
                        {
                            logmd5.info(sng.getPathSong() + "<br>");
                        }
                    }
                }
            }
            logmd5.info("<br>");
        }
    }

    /**
     * Метод для записи всех песен с одинаковыми тегами артиста альбома и названия
     * @param artistObj
     */
    public void writeTagDublicate(ArrayList<Artist> artistObj)
    {
        final Logger logDuplicate = Logger.getLogger("log2");

        HashSet<String> dublicate_path = new HashSet<>(); //пути дубликатов
        String dublicate_name =""; // имя дубликата

        ArrayList<String> name = new ArrayList<>();
        ArrayList<String> path = new ArrayList<>();
        for(Artist art: artistObj) {
            for (Album albCl : art.getAlbum()) {
                for(Song sng: albCl.getSong()){
                    name.add(sng.getNameSong());
                    path.add(sng.getPathSong());
                }
                for(int i =0; i< name.size()-1; i++) {
                    for(int j =i+1; j< name.size(); j++) {
                        if(name.get(i).equals( name.get(j))) {
                            dublicate_name = name.get(i);
                            dublicate_path.add(path.get(i));
                            dublicate_path.add(path.get(j));
                            name.remove(j);
                            path.remove(j);
                            j--;
                        }
                    }
                    if(dublicate_path.size() != 0)
                    {
                        logDuplicate.info("<b><font color=\"red\" face=\"Arial\">Исполнитель : </b></font> "
                                + art.getName() + ", <b><font color=\"red\" face=\"Arial\">Альбом : </b></font> "
                                + albCl.getName() + ", <b><font color=\"red\" face=\"Arial\">Название : </b></font>  "
                                + dublicate_name +"<br>");
                        for(String dbl: dublicate_path) {
                            logDuplicate.info(dbl + "<br>");
                        }
                        logDuplicate.info("<br>");

                    }
                    dublicate_path.clear();
                }
                name.clear();
                path.clear();
            }
        }
    }
}
