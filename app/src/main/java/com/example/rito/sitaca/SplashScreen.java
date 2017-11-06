package com.example.rito.sitaca;

/**
 * Created by rito on 4/17/2015.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 2000;
    private UserDAO userDao;
    private TamanBacaDAO tamanBacaDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        userDao = new UserDAO(getApplicationContext());
        tamanBacaDAO = new TamanBacaDAO(getApplicationContext());
        new Handler().postDelayed(new Runnable() {

			/*
			 * Showing splash screen with a timer. This will be useful when you
			 * want to show case your app logo / company
			 */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                if(userDao.getAllUser().size()==0)//Tidak Ada User
                {
                    Intent i = new Intent(SplashScreen.this, BuatUserActivity.class);
                    startActivity(i);
                }
                else if(tamanBacaDAO.getAllTamanBaca().size()==0){
                    Intent i = new Intent(SplashScreen.this, BuatTamanBacaActivity.class);
                    startActivity(i);
                }
                else
                {
                    Intent i = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(i);
                }
                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

}

