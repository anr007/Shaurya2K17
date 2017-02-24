package red.shaurya2k17;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;



public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 500;


    SharedPreferences status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {


                status = getSharedPreferences("status", Context.MODE_PRIVATE);
                boolean  in= status.getBoolean("in",false);



                if(in) {
                    Intent intent = new Intent(SplashScreen.this, Home.class);
                    startActivity(intent);
                } else
                {
                    Intent intent = new Intent(SplashScreen.this, UserLoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, SPLASH_TIME_OUT);
    }


}




