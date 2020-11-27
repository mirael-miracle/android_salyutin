package com.example.lb2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Locale;


public class MainActivity extends AppCompatActivity {
   @Override
    protected void onCreate(Bundle savedInstanceState){
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_main);
   }
   public void onBtnShowGroupsClick(View view){
       Intent intent = new Intent(this,GroupsListActivity2.class);
       startActivity(intent);
   }
    public void onBtnSendClick(View view){
        TextView textView =(TextView) findViewById(R.id.text);

        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT,textView.getText().toString());
        intent.putExtra(Intent.EXTRA_SUBJECT,"Список групп");
        startActivity(intent);
    }

    public void showJsonData(View view){
       Intent intent = new Intent(this, PostListActivity.class);
       startActivity(intent);
    }

}