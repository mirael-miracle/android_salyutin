package com.example.lb2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class RockGroupActivity extends AppCompatActivity {

    public static final String GROUP_NUMBER ="grpNumb";

    private RockGroup group;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rock_group2);

        Intent intent = getIntent();
        int grpNumber = intent.getIntExtra(RockGroupActivity.GROUP_NUMBER,0);
        group = null;
        SQLiteOpenHelper sqliteHelper = new GroupDatabaseHelper(this);
        try{
            SQLiteDatabase db = sqliteHelper.getReadableDatabase();
            Cursor cursor = db.query("groups",
                    new String[] {"number","facultyName","rockLevel",
                            "deathMetal","popMetal","id"},
                    "id=?",new String[]{Integer.toString(grpNumber)},null,null,null);
            if(cursor.moveToFirst()){
                group = new RockGroup(
                                cursor.getInt(5),
                                cursor.getString(0),
                                cursor.getString(1),
                                cursor.getInt(2),
                                (cursor.getInt(3)>0),
                                (cursor.getInt(4)>0)
                );
            }else{
                Toast toast = Toast.makeText(this,"Can`t find group whit id "+Integer.toString(grpNumber),Toast.LENGTH_SHORT);
                toast.show();
            }
            cursor.close();
            db.close();
        }catch (SQLiteException e){
            Toast toast = Toast.makeText(this,"Database unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }
        if(group!=null) {
            EditText txtGrpNumber = (EditText) findViewById(R.id.grpNumberEdit);
            txtGrpNumber.setText(group.getNumber());

            EditText txtFacultyName = (EditText) findViewById(R.id.facultyEdit);
            txtFacultyName.setText(group.getFacultyName());

            if (group.getRockLevel() == 0) {
                ((RadioButton) findViewById(R.id.edu_level_bachelor)).setChecked(true);
            } else {
                ((RadioButton) findViewById(R.id.edu_level_master)).setChecked(true);
            }

            ((CheckBox) findViewById(R.id.contract_flg)).setChecked(group.isDeathMetal());
            ((CheckBox) findViewById(R.id.privilege_flg)).setChecked(group.isPopMetal());
        }
    }
    public void onOkBtnClick(View view){
       SQLiteOpenHelper sqliteHelper = new GroupDatabaseHelper(this);

        ContentValues contentValues = new ContentValues();
        contentValues.put("number",
                ((TextView) findViewById(R.id.grpNumberEdit)).getText().toString()
        );
        contentValues.put("facultyName",
                ((TextView) findViewById(R.id.facultyEdit)).getText().toString()
        );
        contentValues.put("rockLevel",
                ((RadioButton) findViewById(R.id.edu_level_master)).isChecked()?1:0
        );
        contentValues.put("deathMetal",
                ((CheckBox) findViewById(R.id.contract_flg)).isChecked()
        );
        contentValues.put("popMetal",
                ((CheckBox) findViewById(R.id.privilege_flg)).isChecked()
        );
        Intent intent = getIntent();
        int grpNumber = intent.getIntExtra(GROUP_NUMBER,0);

        try{
            SQLiteDatabase db = sqliteHelper.getReadableDatabase();
            db.update("groups",
                    contentValues,
                    "id=?",
                    new String[]{Integer.toString(grpNumber)}
                    );
            db.close();
            NavUtils.navigateUpFromSameTask(this);
        }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this,"DB unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    public void onBtnStudListClick(View view){
        Intent newIntent = new Intent(this, GroupsListActivity.class);
        newIntent.putExtra(GroupsListActivity.GROUP_NUMBER, group.getId());
        startActivity(newIntent);
    }
    public void onDelete(View view){
        SQLiteOpenHelper sqliteHelper = new GroupDatabaseHelper(this);

        Intent intent = getIntent();
        int grpNumber = intent.getIntExtra(GROUP_NUMBER,0);

        try{
            SQLiteDatabase db = sqliteHelper.getReadableDatabase();
            db.delete("groups",
                    "id=?",
                    new String[]{Integer.toString(grpNumber)});
            db.close();
            NavUtils.navigateUpFromSameTask(this); }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this,
                    "DB unavaliable",
                    Toast.LENGTH_SHORT);
            toast.show();   
        }
    }
}