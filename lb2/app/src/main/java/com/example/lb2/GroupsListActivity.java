package com.example.lb2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class GroupsListActivity extends AppCompatActivity {
    public static final String GROUP_NUMBER = "grpNumb";
    private Cursor cursor;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_list);
        Intent intent = getIntent();
        int grpNumb = intent.getIntExtra(GROUP_NUMBER,0);

        ListView listView = (ListView) findViewById(R.id.groupsList);
        SimpleCursorAdapter adapter = getDataFromDB(grpNumb);
        if(adapter!=null){
            listView.setAdapter(adapter);
        }
    }


    private SimpleCursorAdapter getDataFromDB(int groupId){
        SimpleCursorAdapter listAdapter = null;

        SQLiteOpenHelper sqLiteHelper = new GroupDatabaseHelper(this);
        try{
            db = sqLiteHelper.getReadableDatabase();
            cursor=db.rawQuery("select r.id _id, name, number\n"+
                    "from rock r inner join groups g on r.group_id = g.id\n"+
                    "where g.id = ?", new String[]{Integer.toString(groupId)});
            listAdapter = new SimpleCursorAdapter(this,android.R.layout.simple_list_item_1,
                    cursor,
                    new String[]{"name"},
                    new int[]{android.R.id.text1},
                    0); }
        catch (SQLiteException e){
            Toast toast = Toast.makeText(this,
                    "Database unavaliable",
                    Toast.LENGTH_SHORT);
            toast.show();
        }
        return listAdapter;
    }


    public void onBtnSendClick(View view){
        TextView textView =(TextView) findViewById(R.id.text);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,textView.getText().toString());
        intent.putExtra(Intent.EXTRA_SUBJECT,"Список групп");
        startActivity(intent);
    }
}