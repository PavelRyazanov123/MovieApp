package com.example.movieapp.ui;


import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.movieapp.R;


public class MainActivity extends AppCompatActivity implements HomeFragment.OnFragmentChange {

    FragmentManager fragmentManager = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            fragmentManager.beginTransaction()
                    .add(R.id.host, new HomeFragment())
                    .commit();
        }
    }

    @Override
    public void changeFragment(Bundle bundle) {
        DetailedFragment detailedFragment = new DetailedFragment();
        detailedFragment.setArguments(bundle);
        fragmentManager
                .beginTransaction()
                .replace(R.id.host, detailedFragment)
                .addToBackStack(null)
                .commit();
    }
}
