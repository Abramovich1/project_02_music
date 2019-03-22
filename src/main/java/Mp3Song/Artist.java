package Mp3Song;

import java.util.HashSet;

public class Artist {
    private String artistName;
    private    HashSet<Album> alb = new HashSet<>();


    public Artist(String name){
        artistName = name;
    }

    public  String  getName()
    {
        return  artistName;
    }

    public  void  setAlbum(String alb1)
    {
        alb.add(new Album(alb1));
    }

    public HashSet<Album> getAlbum()
    {
       return alb;
    }

}
