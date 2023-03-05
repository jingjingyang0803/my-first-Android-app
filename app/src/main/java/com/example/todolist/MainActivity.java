package com.example.todolist;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private RequestQueue queue;
    private int userid = 1;
    private String name = "Leanne Graham",
            email = "Sincere@april.biz",
            phone = "1-770-736-8031 x56442",
            city = "Gwenborough";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Instantiate the RequestQueue.
        queue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        userid = intent.getIntExtra("USER_ID", 1);

        TextView information = findViewById(R.id.userInfoTextView);
        information.setText("User Id: " + userid +
                "\nName: " + name +
                "\nEmail: " + email +
                "\nPhone: " + phone +
                "\nCity: " + city);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("NAME", name);
        outState.putString("EMAIL", email);
        outState.putString("PHONE", phone);
        outState.putString("CITY", city);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        name = savedInstanceState.getString("NAME");
        email = savedInstanceState.getString("EMAIL");
        phone = savedInstanceState.getString("PHONE");
        city = savedInstanceState.getString("CITY");

        TextView information = findViewById(R.id.userInfoTextView);
        information.setText("User Id: " + userid +
                "\nName: " + name +
                "\nEmail: " + email +
                "\nPhone: " + phone +
                "\nCity: " + city);
    }

    public void userInfoUpdate(View view) {
        String url = "https://jsonplaceholder.typicode.com/users";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, response -> {
            Log.d("User_App", response);
            parseJsonAndUpdateUI(response);
        }, error -> {
            Log.d("User_App", error.toString());
        });

        queue.add(stringRequest);
    }

    private void parseJsonAndUpdateUI(String response) {
        EditText editTextUserID = findViewById(R.id.userIdEditView);
        String idAsString = editTextUserID.getText().toString();
        int userid_onsearch = 1;

        if (idAsString.equalsIgnoreCase("")) {
            Toast.makeText(MainActivity.this, "Enter a user ID...", Toast.LENGTH_SHORT).show();
        } else {
            userid_onsearch = Integer.valueOf(idAsString);

            if (userid_onsearch > 0 && userid_onsearch < 11) {
                userid = userid_onsearch;
                try {
                    JSONArray userResponse = new JSONArray(response);
                    JSONObject USER = userResponse.getJSONObject(userid - 1);

                    name = USER.getString("name");
                    email = USER.getString("email");
                    phone = USER.getString("phone");
                    city = USER.getJSONObject("address").getString("city");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                TextView information = findViewById(R.id.userInfoTextView);
                information.setText("User Id: " + userid +
                        "\nName: " + name +
                        "\nEmail: " + email +
                        "\nPhone: " + phone +
                        "\nCity: " + city);

            } else {
                Toast.makeText(MainActivity.this, "User Not Found..", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void makeCall(View view) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
        }
    }

    public void sendEmail(View view) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto: ")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        intent.putExtra(Intent.EXTRA_SUBJECT, "Hello! " + name);
        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
        }
    }

    public void openTodoListActivity(View view) {
        Intent openTodo = new Intent(this, todoListActivity.class);

        openTodo.putExtra("USER_ID", userid);
        startActivity(openTodo);
    }
}
