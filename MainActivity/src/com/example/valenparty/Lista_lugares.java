package com.example.valenparty;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class Lista_lugares extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_lugares);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_lista_lugares, menu);
        return true;
    }
}
