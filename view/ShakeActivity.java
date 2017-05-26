package com.jiyun.kaiyuanzhongguo.view;
import java.io.IOException;
import java.net.URL;

import com.jiyun.kaiyuanzhongguo.R;
import com.jiyun.kaiyuanzhongguo.model.callback.MyCallback;
import com.jiyun.kaiyuanzhongguo.model.http.NewsModel;

import android.annotation.SuppressLint;
 import android.app.Activity;
 import android.graphics.Bitmap;
 import android.graphics.BitmapFactory;
 import android.graphics.drawable.Drawable;
 import android.hardware.Sensor;
 import android.hardware.SensorEvent;
 import android.hardware.SensorEventListener;
 import android.hardware.SensorManager;
 import android.os.Bundle;
 import android.os.Handler;
 import android.os.Message;
 import android.os.Vibrator;
 import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


@SuppressLint("HandlerLeak")
 public class ShakeActivity extends Activity {
     private TextView textView;
    private ImageView imageView;
     private SensorManager sensorManager;
     private Sensor sensor;
     private Vibrator vibrator;
     private static final int UPTATE_INTERVAL_TIME = 50;
     private static final int SPEED_SHRESHOLD = 50;//这个值调节灵敏度
     private long lastUpdateTime;
     private float lastX;
     private float lastY;
     private float lastZ;
     @Override
     protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         setContentView(R.layout.activity_shake);
         sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
         vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
         textView= (TextView) findViewById(R.id.shake_text);
         imageView= (ImageView) findViewById(R.id.shake_finish);
         imageView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 finish();
             }
         });
     }


            @Override
     protected void onResume() {
        // TODO Auto-generated method stub
        super.onResume();
        if (sensorManager != null) {
                        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                   }
              if (sensor != null) {
                    sensorManager.registerListener(sensorEventListener,
                            sensor,
                    SensorManager.SENSOR_DELAY_GAME);//这里选择感应频率
                    }
         }


             /**
     173 * 重力感应监听
     174 */
             private SensorEventListener sensorEventListener = new SensorEventListener() {


                 @Override
         public void onSensorChanged(SensorEvent event) {
             long currentUpdateTime = System.currentTimeMillis();
             long timeInterval = currentUpdateTime - lastUpdateTime;
             if (timeInterval < UPTATE_INTERVAL_TIME) {
                 return;
                 }
             lastUpdateTime = currentUpdateTime;
             // 传感器信息改变时执行该方法
             float[] values = event.values;
             float x = values[0]; // x轴方向的重力加速度，向右为正
             float y = values[1]; // y轴方向的重力加速度，向前为正
             float z = values[2]; // z轴方向的重力加速度，向上为正
             float deltaX = x - lastX;
             float deltaY = y - lastY;
             float deltaZ = z - lastZ;


            lastX = x;
            lastY = y;
             lastZ = z;
             double speed = (Math.sqrt(deltaX * deltaX + deltaY * deltaY
                    + deltaZ * deltaZ) / timeInterval) * 100;
             if (speed >= SPEED_SHRESHOLD) {
                 vibrator.vibrate(300);
                 textView.setText("抽奖活动已结束，请期待下次活动");
                 }
             }


                 @Override
         public void onAccuracyChanged(Sensor sensor, int accuracy) {

             }
         };

}
