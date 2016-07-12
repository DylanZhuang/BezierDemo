package com.dylan.bezierdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    private BezierView bezierView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        bezierView = (BezierView) findViewById(R.id.bezier_view);
//        bezierView.setBezierType(BezierView.CUBIC_BEZIER);
//
//        SeekBar seekBar = (SeekBar) findViewById(R.id.seekBar);
//        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//            @Override
//            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//                bezierView.setRadius(progress);
//            }
//
//            @Override
//            public void onStartTrackingTouch(SeekBar seekBar) {
//
//            }
//
//            @Override
//            public void onStopTrackingTouch(SeekBar seekBar) {
//
//            }
//        });

//        QuadraticBezierView quadraticBezierView = new QuadraticBezierView(this);
//        setContentView(quadraticBezierView);

        CubicBezierView cubicBezierView = new CubicBezierView(this);
        setContentView(cubicBezierView);
    }
}
