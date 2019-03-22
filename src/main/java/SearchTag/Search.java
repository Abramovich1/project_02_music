package SearchTag;

import main.TagArrays;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;

public class Search {
    /**
     * функция записи тегов mp3 файла в массив хранящий информацю о песнях
     *
     * @param fileLocation - путь к файлу из которого извлекаем теги
     * @param tag - объект, хранящий массивы тегов
     */
    public static void mp3Info(String fileLocation, TagArrays tag) throws NoSuchAlgorithmException {

        try {
            InputStream input = new FileInputStream(new File(fileLocation));
            InputStream input1 = new FileInputStream(new File(fileLocation));
            String md5 = org.apache.commons.codec.digest.DigestUtils.md5Hex(input1);
            ContentHandler handler = new DefaultHandler();
            Metadata metadata = new Metadata();
            Parser parser = new Mp3Parser();
            ParseContext parseCtx = new ParseContext();
            parser.parse(input, handler, metadata, parseCtx);


            if (metadata.get("xmpDM:artist") == null || metadata.get("xmpDM:artist").equals("") || metadata.get("xmpDM:artist").equals(null)) {
                tag.artist.add("Неизвестно");
                tag.artistHashSet.add("Неизвестно");
            } else {
                tag.artist.add(metadata.get("xmpDM:artist"));
                tag.artistHashSet.add(metadata.get("xmpDM:artist"));
            }

            System.out.println(tag.countSong);
            if (metadata.get("xmpDM:album") == null || metadata.get("xmpDM:album").equals(""))
                tag.album.add("Неизвестно");
            else
                tag.album.add(metadata.get("xmpDM:album"));

            if (metadata.get("title") == null || metadata.get("title").equals("") || metadata.get("title").equals(null))
                tag.title.add("Неизвестно");
            else
                tag.title.add(metadata.get("title"));

            tag.duration.add(metadata.get("xmpDM:duration"));
            tag.location.add(fileLocation);

            tag.tagSumm.add(md5);
            tag.countSong++;
            input.close();
            input1.close();

        } catch (IOException | SAXException | TikaException e) {
            e.printStackTrace();
        }
    }


    /**
     * функция нахождения mp3 файлов в указаных путях
     *@param arraysTag - объект, хранящий массивы тегов
     * @param files - папка в которой нужно найти все файлы нужного формата
     */
    public static void fileFind(File[] files, TagArrays arraysTag) throws NoSuchAlgorithmException, InterruptedException {
        String type;
        for (File object : files) {
            if (object.getName().lastIndexOf(".") != -1 && object.getName().lastIndexOf(".") != 0)
                type = object.getName().substring(object.getName().lastIndexOf(".") + 1);
            else
                type = "DIR";

            if (type.equals("mp3")) {
                //if ограничивает количество одновременно работающих потоков, для того что бы проект не завис из-за нагрузки на диск
                if (arraysTag.threads.size() % 4 == 0 && arraysTag.threads.size() != 0) {
                    for (int i = arraysTag.threads.size() - 4; i < arraysTag.threads.size(); i++) {
                        arraysTag.threads.get(i).join();
                        break;
                    }
                }
                Thread thread = new Thread("New Thread") {
                    public void run() {
                        try {
                            mp3Info(object.getPath(), arraysTag);
                        } catch (NoSuchAlgorithmException e) {
                            e.printStackTrace();
                        }
                    }
                };
                arraysTag.threads.add(thread);
                thread.start();
            }
            if (object.isDirectory()) {
                File[] files1 = object.listFiles();
                fileFind(files1, arraysTag);
            }
        }
    }
}
