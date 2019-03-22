package main;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Класс хранящий листы тегов и
 */

public class TagArrays {
    public   ArrayList<String> artist = new ArrayList<>();
    public   ArrayList<String> title = new ArrayList<>();
    public   ArrayList<String> album = new ArrayList<>();
    public   ArrayList<String> duration = new ArrayList<>();
    public   ArrayList<String> location = new ArrayList<>();
    public   ArrayList<String> tagSumm = new ArrayList<>();
    // сет хранящий артистов для быстрого создания объектов этих артистов
    public  HashSet<String> artistHashSet = new HashSet<>();
    //Лист хранящий потоки
    public  ArrayList<Thread> threads = new ArrayList<>();

    public  int countSong = 0; //счетчик песен получаемых из файла
}
