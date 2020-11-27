package com.example.lb2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;

import androidx.constraintlayout.widget.Group;

public class GroupDatabaseHelper extends SQLiteOpenHelper {
    private static final String DB_NAME = "groups";
    private static final int DB_VERSION = 2;

    public GroupDatabaseHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlText = "CREATE TABLE Groups (\n"+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"+
                "number         TEXT(10) NOT NULL,\n"+
                "facultyName    TIME(100),\n"+
                "rockLevel      INTEGER,\n"+
                "deathMetal     BOOLEAN,\n"+
                "popMetal       BOOLEAN\n"+
                ");";
        sqLiteDatabase.execSQL(sqlText);

        updateShema(sqLiteDatabase,0);

        populateDB(sqLiteDatabase);
    }

    private void populateDB(SQLiteDatabase db) {
        for(RockGroup group: RockGroup.getGroups()){
            insertRow(db,group);
        }
        populateGroups(db);
    }



    private void insertRow(SQLiteDatabase db, RockGroup group) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("number",group.getNumber());
        contentValues.put("facultyName",group.getFacultyName());
        contentValues.put("rockLevel",group.getRockLevel());
        contentValues.put("deathMetal",group.isDeathMetal());
        contentValues.put("popMetal",group.isPopMetal());
        db.insert("Groups",null, contentValues);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        updateShema(db,i);
    }

    private void updateShema(SQLiteDatabase db, int ver){
        if(ver < 2){
            db.execSQL("CREATE TABLE Rock (\n"+
                    "id INTEGER PRIMARY KEY AUTOINCREMENT,\n"+
                    "name         TEXT(100) NOT NULL,\n"+
                    "group_id    INTEGER REFERENCES Groups(id) ON DELETE RESTRICT\n"+
                    "                                          ON UPDATE RESTRICT\n"+
                    ");");
            populateGroups(db);
        }
    }

    private void populateGroups(SQLiteDatabase db){
        for(Groups groups:Groups.getGroups()){
            insertRowToGroups(db,groups);
        }
    }

    private void insertRowToGroups(SQLiteDatabase db, Groups groups){
        db.execSQL("insert into rock(name, group_id)\n"+
                "select '"+groups.getName()+"',id\n"+
                "from groups\n"+
                "where number='"+groups.getGroupNubmer()+"'");
    }
}
