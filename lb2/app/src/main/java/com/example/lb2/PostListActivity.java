package com.example.lb2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;


public class PostListActivity extends AppCompatActivity {

    public static final String POST_ID = "postid";

    class Post{
        public int id;
        public String title;

        public Post(int id, String title){
            this.id = id;
            this.title = title;
        }

        @Override
        public String toString(){return title;}
    }

    private ArrayList<Post> getListData() {
        ArrayList<Post> arr = new ArrayList<>();
        String res = new HttpDataGetter(
                "http://jsonplaceholder.typicode.com/posts"
        ).getData();
        try {
            JSONArray jsonArray = new JSONArray(res);
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                arr.add(
                        new Post(
                                obj.getInt("id"),
                                obj.getString("title")
                        )
                );
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return arr;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_list);

        ListView listView = (ListView) findViewById(R.id.postList);
        ArrayAdapter<Post> adapter = new ArrayAdapter<Post>(
                this,
                android.R.layout.simple_list_item_1,
                getListData()
        );
        listView.setAdapter(adapter);

        AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Post post = (Post) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(PostListActivity.this,
                        OnePostActivity.class);
                intent.putExtra(PostListActivity.POST_ID,post.id);
                startActivity(intent);
            }
        };
        listView.setOnItemClickListener(listener);
    }
}
