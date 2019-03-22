package Mp3Song;

import java.util.ArrayList;

public class Album {
    private String albumName;


    private   ArrayList<Song> Song = new ArrayList<>();

    Album(String name)
    {
        albumName = name;
    }

    public String getName()
    {
        return albumName;
    }



    public void setSong(String name, String duration,String path, String summ) {
        Song.add(new Song(name,duration,path,summ));
    }


    public ArrayList<Song> getSong(){
        return  Song;
    }
}
