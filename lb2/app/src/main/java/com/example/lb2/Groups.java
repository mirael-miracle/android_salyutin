package com.example.lb2;

import java.util.ArrayList;
import java.util.Arrays;

public class Groups {
    private String name;
    private String groupNubmer;

    public Groups(String name, String groupNubmer) {
        this.name = name;
        this.groupNubmer = groupNubmer;
    }


    public String getName() {
        return name;
    }

    public String getGroupNubmer() {
        return groupNubmer;
    }

    private final static ArrayList<Groups> groups = new ArrayList<Groups>(
            Arrays.asList(
                    new Groups("Оливер Сайкс", "Bring Me the Horizon"),
                    new Groups("Джордан Фиш", "Bring Me the Horizon"),
                    new Groups("Мэтт Николлс", "Bring Me the Horizon"),
                    new Groups("Мэтт Кин", "Bring Me the Horizon"),
                    new Groups("Ли Малия", "Bring Me the Horizon"),
                    new Groups("Ноа Себастьян", "Bad Omens"),
                    new Groups("Дэнни Уорсноп","Asking Alexandria"),
                    new Groups("Бен Брюс","Asking Alexandria"),
                    new Groups("Джеймс Касселлс","Asking Alexandria"),
                    new Groups("Камерон Лиддел","Asking Alexandria"),
                    new Groups("Сэм Беттли","Asking Alexandria")
            )
    );
    public static ArrayList<Groups> getGroups(){
        return getGroups("");
    }
    public static ArrayList<Groups> getGroups(String groupNubmer){
        ArrayList<Groups> stList = new ArrayList<>();
        for (Groups s: groups){
            if(s.getGroupNubmer().equals(groupNubmer) || (groupNubmer == "")){
                stList.add(s);
            }
        }
        return stList;
    }
    @Override
    public String toString(){
        return name;
    }
}