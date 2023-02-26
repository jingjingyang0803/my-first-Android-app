package com.example.todolist;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.Queue;

public class todoListActivity extends AppCompatActivity {

    private RequestQueue queue;
    private int userid;
    private Queue<String> todolist = new LinkedList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todo_list);

        Intent intent = getIntent();
        userid = intent.getIntExtra("USER_ID", 1);


        TextView idTv = findViewById(R.id.idTextView);
        idTv.setText("User ID: " + userid);

        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);

        String url = "https://jsonplaceholder.typicode.com/todos";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.d("User_App", response);
                    parseJsonAndUpdateUI(response);
                }, error -> {
            Log.d("User_App", error.toString());
        });

        queue.add(stringRequest);
    }


    private void parseJsonAndUpdateUI(String response) {
        try {
            JSONArray todoResponse = new JSONArray(response);
            for (int i = 0; i < todoResponse.length(); i++) {
                JSONObject TODO = todoResponse.getJSONObject(i);
                if (TODO.getInt("userId") == userid && TODO.getBoolean("completed") == false) {
                    todolist.add(TODO.getString("title"));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String result = "";
        int counter = 1;
        TextView todolistTextView = findViewById(R.id.listTextView);
        for (String todo : todolist) {
            result += counter + ".  " + todo + "\n\n";
            counter++;
        }
        todolistTextView.setText(result);
    }

    public void openMainActivity(View view) {
        Intent openMain = new Intent(this, MainActivity.class);

        openMain.putExtra("USER_ID", userid);
        startActivity(openMain);
    }
}