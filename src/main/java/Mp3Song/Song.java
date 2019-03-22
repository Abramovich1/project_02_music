package Mp3Song;


public class Song {
    private String name,path,summ,duration ;

    Song(String sName, String durationNew, String pathNew,String  newSumm){
        name = sName;
        duration = durationNew;
        path = pathNew;
        summ =newSumm;
    }


    public String getNameSong()
    {
        return  name;
    }

    public String getSummSong()
    {
        return  summ;
    }

    public String getPathSong()
    {
        return  path;
    }

    public String getDurationSong()
    {
        return  duration;
    }
}
