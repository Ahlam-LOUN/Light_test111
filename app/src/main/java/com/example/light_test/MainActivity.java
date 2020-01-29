package com.example.light_test;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static android.hardware.Sensor.TYPE_LIGHT;

public class MainActivity extends AppCompatActivity  {
    ImageView bgapp, clover;
    LinearLayout textsplash, texthome, menus;
    Animation frombottom;

    //Declaration de capteur
    private SensorManager   sensorManager;
    private Sensor lightSensor;
    private SensorEventListener sevnt;
    private Float maxvalues;
  private TextView textView,textView1,textView2,textView3;

  ////Declaration du media player
  public  MediaPlayer mediaPlayer;
  private final static int MAX_VOLUME = 100;
  float volume = 0.5f;
  float vivo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
   //les phrases de l'entré de l'application
        textView1 = (TextView) findViewById(R.id.salutation);
        textView1.setText(R.string.Salutation);
        textView2 = (TextView) findViewById(R.id.entrer);
        textView2.setText(R.string.entrer);
        textView3 = (TextView) findViewById(R.id.bonjour);
        textView3.setText(R.string.bonjour);


   //les images utilisés dans l'animation
        frombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        bgapp = (ImageView) findViewById(R.id.bgapp);
        clover = (ImageView) findViewById(R.id.clover);
        textsplash = (LinearLayout) findViewById(R.id.textsplash);
        texthome = (LinearLayout) findViewById(R.id.texthome);
        menus = (LinearLayout) findViewById(R.id.menus);
        bgapp.animate().translationY(-1900).setDuration(800).setStartDelay(300);
        clover.animate().alpha(0).setDuration(800).setStartDelay(600);
        textsplash.animate().translationY(140).alpha(0).setDuration(800).setStartDelay(300);
        texthome.startAnimation(frombottom);
        menus.startAnimation(frombottom);



     //Initialisation de mediaPlayer
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bbip);
     //Acceder au capteur
        sensorManager =(SensorManager) getSystemService(SENSOR_SERVICE);
        //S'abonner aux evenements du capteur de type light
        lightSensor=  sensorManager.getDefaultSensor(TYPE_LIGHT);
        //Faire le test d'existence de capteur light
        if(lightSensor==null){
            Toast.makeText(this,"The device has not sensor :(",Toast.LENGTH_LONG).show();
            finish();
        }



        //Recevoir les notification depuis Sensor manager
        sevnt=new SensorEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onSensorChanged(SensorEvent event) {
                //La valeur capter par le capteur
                float value = event.values[0];
                //getSupportActionBar().setTitle("Limunosity is hhhhh : " + value + " LX");
                volume = (float) (volume + (value/10));
                vivo=(float) (value/50);
                //J'adapte le volume du média player avec la valeur qu'on a (Values[0])
                mediaPlayer.setVolume(vivo, vivo);


            }



//Appeler quand la précision du capteur change
            @Override
            public void onAccuracyChanged(Sensor sensor, int accuracy) {

            }



        };


}



//Arreter  media player ou bien la redemarer
public void play(View v){
    if(mediaPlayer.isPlaying()){
        mediaPlayer.stop();
        Toast.makeText(this,"Stop playing.",Toast.LENGTH_SHORT).show();}
else
        mediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.bbip);
    mediaPlayer.setLooping(true);
    mediaPlayer.start();

}

@Override
    public void onResume(){
        super.onResume();
        sensorManager.registerListener(sevnt,lightSensor,SensorManager.SENSOR_DELAY_FASTEST);
    }

    @Override
 public void onPause(){
        super.onPause();
        sensorManager.unregisterListener(sevnt);

    }


}