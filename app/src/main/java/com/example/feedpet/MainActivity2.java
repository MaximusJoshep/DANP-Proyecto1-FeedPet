package com.example.feedpet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

public class MainActivity2 extends AppCompatActivity {
    RegisterFragment registerFragment=new RegisterFragment();
    LoginFragment login = new LoginFragment();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Bundle parametro=this.getIntent().getExtras();
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_container,new HomeFragment()).commit();

        int fragment=this.getIntent().getIntExtra("fragment",0);
        if(fragment==1)
        {
            Log.d("TAG", "LOGIN");
            loadFragment(login);
        }

        if(fragment==2)
        {
            Log.d("TAG", "REGISTER");
            loadFragment(registerFragment);
        }
    }
    public void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.commit();
    }
}