package com.example.lb2;
import java.util.ArrayList;
import java.util.Arrays;
public class RockGroup {
    private String number;
    private String facultyName;
    private int rockLevel;
    private boolean deathMetal;
    private boolean popMetal;
    private int id;

    public int getId(){
        return id;
    }
    public RockGroup(String number, String facultyName, int rockLevel, boolean deathMetal, boolean popMetal){
        this.number = number;
        this.facultyName = facultyName;
        this.rockLevel = rockLevel;
        this.deathMetal = deathMetal;
        this.popMetal = popMetal;

    }
    public RockGroup(int id,String number, String facultyName, int rockLevel, boolean deathMetal, boolean popMetal ){
        this(number, facultyName, rockLevel, deathMetal, popMetal);
        this.id = id;
    }
    public String getNumber(){
        return number;
    }
    public String getFacultyName(){
        return facultyName;
    }
    public int getRockLevel(){
        return rockLevel;
    }

    public boolean isDeathMetal() {
        return deathMetal;
    }

    public boolean isPopMetal() {
        return popMetal;
    }

    private static ArrayList<RockGroup> groups = new ArrayList<RockGroup>(
            Arrays.asList(
                    new RockGroup("Bring Me the Horizon", "Rock", 0,true,false),
                    new RockGroup("Bad Omens", "Rock", 0, true,true),
                    new RockGroup("Asking Alexnadria", "Rock", 0, false, true),
                    new RockGroup("Falling in reverse","Rock",0,false,true)
            )
    );
    public static RockGroup getGroup(String groupNumber){
        for(RockGroup g:groups){
            if(g.getNumber().equals(groupNumber)){
                return g;
            }
        }
        return null;
    }
    public static ArrayList<RockGroup> getGroups(){
        return groups;
    }
    @Override
    public String toString(){
        return number;
    }
    public static void addGroup(RockGroup group){groups.add(group);}
}
