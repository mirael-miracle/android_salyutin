package com.example.lb2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class activity_add_students_group extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_students_group);
    }
    public void onGrpAddClick(View view){
        EditText number =(EditText) findViewById(R.id.addGroupNumber);
        EditText faculty =(EditText) findViewById(R.id.addFaculty);

        SQLiteOpenHelper sqliteHelper = new GroupDatabaseHelper(this);
        try{
            SQLiteDatabase db = sqliteHelper.getReadableDatabase();
            ContentValues contentValues = new ContentValues();

            contentValues.put("number",number.getText().toString());
            contentValues.put("facultyName",faculty.getText().toString());
            contentValues.put("rockLevel",0);
            contentValues.put("deathMetal",0);
            contentValues.put("popMetal",0);

            db.insert("groups",null, contentValues);
            db.close();
            NavUtils.navigateUpFromSameTask(this); }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this,"DB unavailable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}