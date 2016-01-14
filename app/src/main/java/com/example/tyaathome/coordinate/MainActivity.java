package com.example.tyaathome.coordinate;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;

public class MainActivity extends AppCompatActivity {

    private EditText etOriginX = null;
    private EditText etOriginY = null;
    private EditText etOriginDistance = null;
    private EditText etOriginAngle = null;
    private TextView tvFirstResult = null;
    private EditText etFirstDistance = null;
    private EditText etFirstAngle = null;
    private TextView tvSecondResult = null;

    private TextWatcher mWatcher0 = null;
    private TextWatcher mWatcher1 = null;

    private float fFirstX = Float.MIN_VALUE;
    private float fFirstY = Float.MIN_VALUE;

    DecimalFormat df = new DecimalFormat("0.00");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initView() {
        etOriginX = (EditText) findViewById(R.id.et_origin_x);
        etOriginY = (EditText) findViewById(R.id.et_origin_y);
        etOriginDistance = (EditText) findViewById(R.id.et_origin_distance);
        etOriginAngle = (EditText) findViewById(R.id.et_origin_angle);
        tvFirstResult = (TextView) findViewById(R.id.tv_first_coordinate);
        etFirstDistance = (EditText) findViewById(R.id.et_first_distance);
        etFirstAngle = (EditText) findViewById(R.id.et_first_angle);
        tvSecondResult = (TextView) findViewById(R.id.tv_result);
    }

    private void initEvent() {
        mWatcher0 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String x = etOriginX.getText().toString();
                String y = etOriginY.getText().toString();
                String distance = etOriginDistance.getText().toString();
                String angle = etOriginAngle.getText().toString();
                if (!TextUtils.isEmpty(x) && !TextUtils.isEmpty(y) && !TextUtils.isEmpty(distance) && !TextUtils.isEmpty(angle)) {
                    String result = "第一点坐标:";
                    float fX = stringToFloat(x);
                    float fY = stringToFloat(y);
                    float fDistance = stringToFloat(distance);
                    float fAngle = stringToFloat(angle);
                    fFirstX = (float) (fX + fDistance * Math.sin(angleToRadians(fAngle)));
                    fFirstY = (float) (fY + fDistance * Math.cos(angleToRadians(fAngle)));
                    result += df.format(fFirstX) + " ," + df.format(fFirstY);
                    tvFirstResult.setText(result);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        mWatcher1 = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String distance = etFirstDistance.getText().toString();
                String angle = etFirstAngle.getText().toString();
                if (fFirstX != Float.MIN_VALUE && fFirstY != Float.MIN_VALUE && !TextUtils.isEmpty(distance) && !TextUtils.isEmpty(angle)) {
                    String result = "第二点坐标:";
                    float fDistance = stringToFloat(distance);
                    float fAngle = stringToFloat(angle);
                    float fX = (float) (fFirstX + fDistance * Math.sin(angleToRadians(fAngle)));
                    float fY = (float) (fFirstY + fDistance * Math.cos(angleToRadians(fAngle)));
                    result += df.format(fX) + " ," + df.format(fY);
                    tvSecondResult.setText(result);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        etOriginX.addTextChangedListener(mWatcher0);
        etOriginY.addTextChangedListener(mWatcher0);
        etOriginDistance.addTextChangedListener(mWatcher0);
        etOriginAngle.addTextChangedListener(mWatcher0);
        etFirstDistance.addTextChangedListener(mWatcher1);
        etFirstAngle.addTextChangedListener(mWatcher1);

    }

    /**
     * 角度转弧度
     * @param angle
     * @return
     */
    private double angleToRadians(float angle) {
        return Math.PI / 180.d * angle;
    }

    private float stringToFloat(String str) {
        try {
            NumberFormat format = NumberFormat.getInstance();
            Number number = format.parse(str);
            float f = number.floatValue();
            return f;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0f;
    }

}
