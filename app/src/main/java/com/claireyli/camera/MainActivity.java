package com.claireyli.camera;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

    private CameraManager mCameraManager;
    private String mCameraId;
    private Button on_button;
    private Button off_button;
    private Button encrypt_button;
    Handler handler = new Handler();

    public void turnOnFlashLight() {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void turnOffFlashLight() {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                mCameraManager.setTorchMode(mCameraId, false);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    String getBinary(Integer x){

        return Integer.toBinaryString(x);
    }

    void beam(String binary){
        int k=0;
        final String bin=binary;
        while(k<bin.length()){
            if(bin.charAt(k)=='1'){
                turnOnFlashLight();
                try {
                    Thread.sleep(2000);
                    turnOffFlashLight();
                    Thread.sleep(100);
                    turnOnFlashLight();
                } catch (InterruptedException ie) {

                }
            }
            else{
                turnOffFlashLight();
                try {
                    Thread.sleep(2000);
                    turnOffFlashLight();
                    Thread.sleep(100);

                } catch (InterruptedException ie) {

                }
            }




//            handler.postDelayed(new Runnable() {
//
//                @Override
//                public void run() {
//                    //testToast2();
//                    turnOffFlashLight();
//                    //Toast.makeText("hello"+String.valueOf(bin.charAt(k)), Toast.LENGTH_LONG);
//                    turnOnFlashLight();
//                }
//            }, 2000);

            k++;
        }

        turnOffFlashLight();
//        try {
//            Thread.sleep(2000);
//            turnOffFlashLight();
//            Thread.sleep(100);
//
//        } catch (InterruptedException ie) {
//
//        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.activity_main);






        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //Do your operation
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);



        on_button = (Button) findViewById(R.id.btn_on);
        off_button = (Button) findViewById(R.id.btn_off);
        encrypt_button = (Button) findViewById(R.id.encrypt_btn);
        mCameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
        try {
            mCameraId = mCameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }



        on_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                turnOnFlashLight();
            }
        });

        off_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                turnOffFlashLight();
            }
        });

        encrypt_button.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {

                TextView entry1 = (EditText) findViewById(R.id.credit_1);
                String encrypt=entry1.getText().toString();
                int k=0;
                while(k<encrypt.length()) {

                    int x = Integer.parseInt(String.valueOf(encrypt.charAt(k)));
                    //Toast.makeText(getApplicationContext(),
                            //"Converting  " + x, Toast.LENGTH_SHORT).show();

                    String binary = getBinary(x);

                    //Toast.makeText(getApplicationContext(),
                            //"Now transmitting " + binary, Toast.LENGTH_SHORT).show();

                    beam(binary);

                    k++;
                }
            }
        });

    }


    void testToast(){
        Toast.makeText(this, "I am a delayed toast", Toast.LENGTH_LONG).show();
    }

    void testToast2(){
        Toast.makeText(this, "BLAH", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
