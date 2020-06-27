package com.example.myapplication1.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication1.R;

import org.json.JSONException;
import org.json.JSONObject;

public class registerActivity extends AppCompatActivity {

    EditText usernameEditText,passwordEditText,cnf_passwordEditText,rollnoEditText;
    Spinner spinner1,spinner2;
    Button registerButton;
    TextView loginTextView;
    private RequestQueue queue;
    JsonObjectRequest objectRequest;
    JSONObject data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        usernameEditText = (EditText) findViewById(R.id.username);
        passwordEditText = (EditText)findViewById(R.id.password);
        cnf_passwordEditText = (EditText)findViewById(R.id.cnf_password);
        rollnoEditText = (EditText) findViewById(R.id.rollno);
        registerButton = (Button) findViewById(R.id.button_register);
        loginTextView = (TextView)findViewById(R.id.text_login);

        loginTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent loginIntent = new Intent(registerActivity.this,LoginActivity.class);
                startActivity(loginIntent);
            }
        });




        spinner1= (Spinner) findViewById(R.id.branchsp);
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(this,R.array.Branch,android.R.layout.simple_spinner_item);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner1.setAdapter(adapter1);
        spinner1.setPrompt("Select Branch");

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinner1.setSelection(i);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Object bran = spinner1. getSelectedItem();

        spinner2= (Spinner) findViewById(R.id.semestersp);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this,R.array.Semester,android.R.layout.simple_spinner_item);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner2.setAdapter(adapter2);
        spinner2.setPrompt("Select Semester");

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int p, long l) {
                spinner2.setSelection(p);

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Object sem = spinner2.getSelectedItem();

        String URL ="http://cbit-qp-api.herokuapp.com/user-register";
        data = new JSONObject();

        try {

            data.put("uname",usernameEditText.getText().toString());
            data.put("password",passwordEditText.getText().toString());
            data.put("rno",rollnoEditText.getText().toString());
            data.put("b_id",sem);
            data.put("sem_no",bran);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        queue = Volley.newRequestQueue(this);
        objectRequest = new JsonObjectRequest(Request.Method.POST,
                URL,
                data,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        String accessTkn = response.getString("access_token");
                        MainActivity2.text.setText(accessTkn);


                    }

                },
                new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast toast = Toast.makeText(getApplicationContext(),error.toString(),Toast.LENGTH_LONG);
                        toast.show();


                    }
                });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (passwordEditText.getText().toString().equals(cnf_passwordEditText.getText().toString())){
                    queue.add(objectRequest);
                    openActivity2();

                }
                else {
                    Toast.makeText(registerActivity.this,"passwords dont match",Toast.LENGTH_SHORT).show();
                }





            }
        });




    }
    private void openActivity2() {
        Intent loginIntent = new Intent(this,MainActivity2.class);
        startActivity(loginIntent);

    }
}