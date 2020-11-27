package com.example.lb2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GroupsListActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_groups_list2);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int group = ((RockGroup) adapterView.getItemAtPosition(i)).getId();
                Intent intent = new Intent(GroupsListActivity2.this,
                        RockGroupActivity.class);
                intent.putExtra(RockGroupActivity.GROUP_NUMBER,group);
                startActivity(intent);
            }
        };
        ListView listView = (ListView) findViewById(R.id.groups_list);
        listView.setOnItemClickListener(listener);

    }
    @Override
    protected void onStart(){
        super.onStart();
        ListView listView = (ListView) findViewById(R.id.groups_list);
        ArrayAdapter<RockGroup> adapter = new ArrayAdapter<RockGroup>(this,
                android.R.layout.simple_list_item_1,
                getDataFromDB()
        );
        listView.setAdapter(adapter);
    }

//    public void onGrpAddClick(View view) {
//        startActivity(new Intent(this,activity_add_students_group.class));
//    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.groups_menu,menu);
        String text ="";
        for(RockGroup group: RockGroup.getGroups()){
            text += group.getNumber()+"\n";
        }
        MenuItem menuItem = menu.findItem(R.id.action_share);
        ShareActionProvider shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        Intent intent = new Intent (Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,text);
        shareActionProvider.setShareIntent(intent);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.action_add_group:
                startActivity(new Intent(this,activity_add_students_group.class));
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private ArrayList<RockGroup> getDataFromDB(){
        ArrayList<RockGroup> groups = new ArrayList<RockGroup>();

        SQLiteOpenHelper sqliteHelper = new GroupDatabaseHelper(this);
        try{
            SQLiteDatabase db = sqliteHelper.getReadableDatabase();
            Cursor cursor = db.query("groups",
                    new String[] {"number","facultyName","rockLevel",
                                "deathMetal","popMetal","id"},
                    null,null,null,null,"number");
            while(cursor.moveToNext()){
                groups.add(
                        new RockGroup(
                                cursor.getInt(5),
                                cursor.getString(0),
                                cursor.getString(1),
                                cursor.getInt(2),
                                (cursor.getInt(3)>0),
                                (cursor.getInt(4)>0)
                        )
                );
            }
            cursor.close();
            db.close();
        }catch (SQLiteException e){
            Toast toast = Toast.makeText(this,"Database unavailable",Toast.LENGTH_SHORT);
            toast.show();
        }
        return groups;
    }
}