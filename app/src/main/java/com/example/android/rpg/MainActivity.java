package com.example.android.rpg;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public String result;

    public void game(View view){
        EditText inputName = (EditText)findViewById(R.id.name);
        String name = inputName.getText().toString();
        String message = "";
        if ("".equals(inputName.getText().toString().trim())){
            message = "请输入姓名";
            displayMessage(message);
        } else {
            message = "开始";
            Adventure ad = new Adventure();
            result = ad.go(name);
            message = message + result;
            displayMessage(message);
        }
    }
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.message);
        priceTextView.setText(message);
    }
}
