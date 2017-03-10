package red.shaurya2k17;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;


public class SplashScreen extends Activity {

    private static int SPLASH_TIME_OUT = 50;


    SharedPreferences status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            //window.setNavigationBarColor(getResources().getColor(R.color.colorPrimary));
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimary));

        }


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




