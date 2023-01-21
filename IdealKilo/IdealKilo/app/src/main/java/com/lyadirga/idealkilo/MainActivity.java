package com.lyadirga.idealkilo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

public class MainActivity extends AppCompatActivity {

    private EditText editText;
    private TextView boy_tv,durum_tv,ideal_tv,kilo_tv;
    private SeekBar seekBar;
    private RadioGroup radioGroup;
    private boolean erkekmi = true;
    private double boy = 0.0;
    private int kilo = 50;
    InterstitialAd mInterstitialAd;
    private RadioGroup.OnCheckedChangeListener radioGroupOlayIsleyicisi=new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {

            if (checkedId==R.id.bay)
                erkekmi=true;
            else if (checkedId==R.id.bayan)
                erkekmi=false;

            guncelle();

        }
    };
    private SeekBar.OnSeekBarChangeListener seekBarOlayIsleyicisi=new SeekBar.OnSeekBarChangeListener() {
        @Override
        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

          kilo=30+progress;
            guncelle();
        }

        @Override
        public void onStartTrackingTouch(SeekBar seekBar) {

        }

        @Override
        public void onStopTrackingTouch(SeekBar seekBar) {

        }
    };
    private TextWatcher editTextOlayIsleyicisi= new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            try {
                boy=Double.parseDouble(s.toString())/100.0;

            }catch (NumberFormatException e){
               boy=0.0;
            }

            guncelle();

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText= (EditText) findViewById(R.id.editText);
        boy_tv= (TextView) findViewById(R.id.boy_tv);
        durum_tv= (TextView) findViewById(R.id.durum_tv);
        ideal_tv= (TextView) findViewById(R.id.ideal_tv);
        kilo_tv= (TextView) findViewById(R.id.kilo_tv);
        radioGroup= (RadioGroup) findViewById(R.id.radioGrup);
        seekBar= (SeekBar) findViewById(R.id.seekBar);

        editText.addTextChangedListener(editTextOlayIsleyicisi);
        seekBar.setOnSeekBarChangeListener(seekBarOlayIsleyicisi);
        radioGroup.setOnCheckedChangeListener(radioGroupOlayIsleyicisi);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        reklamiYukle();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                super.onAdClosed();

                reklamiYukle();
            }

            @Override
            public void onAdLoaded() {
                super.onAdLoaded();
                mInterstitialAd.show();
            }
        });


        guncelle();
    }

    private void reklamiYukle() {
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mInterstitialAd.loadAd(adRequest);

    }


    private void guncelle() {

        kilo_tv.setText(String.valueOf(kilo)+" kg");
        boy_tv.setText(String.valueOf(boy)+" m");

        int ideal_kiloBay= (int) (50+2.3*(boy*100*0.4-60));
        int ideal_kiloBayan= (int) (45.5+2.3*(boy*100*0.4-60));
        double vki=kilo/(boy*boy);


        if (erkekmi){
          //erkek ise
         ideal_tv.setText(String.valueOf(ideal_kiloBay));
            if (vki<=20.7){
                durum_tv.setBackgroundResource(R.color.zayif);
                durum_tv.setText(R.string.zayif);
            }else if (20.7<vki && vki <= 26.4){
                //ideal kilo
                durum_tv.setText(R.string.ideal);
                durum_tv.setBackgroundResource(R.color.durum_ideal);

            }else if (26.4<vki && vki <= 27.8){
                //normal kilodan fazla
                durum_tv.setText(R.string.normalden_fazla);
                durum_tv.setBackgroundResource(R.color.durum_idealden_fazla);

            }else if (27.8<vki && vki <= 31.1){
                // fazla kilolu
                durum_tv.setText(R.string.fazla_kilolu);
                durum_tv.setBackgroundResource(R.color.durum_fazla_kilolu);

            }else if (31.1<vki && vki <= 34.9){
                // obez
                durum_tv.setText(R.string.obez);
                durum_tv.setBackgroundResource(R.color.durum_obez);
            }else{
                //doktor tedavisi
                durum_tv.setText(R.string.doktora);
                durum_tv.setBackgroundResource(R.color.durum_doktora);

            }


        }else{
          //bayan ise
            ideal_tv.setText(String.valueOf(ideal_kiloBayan));

            if (vki <= 19.1){
                // zayıf
                durum_tv.setText(R.string.zayif);
                durum_tv.setBackgroundResource(R.color.zayif);

            }else if (19.1<vki && vki <= 25.8){
                //ideal kilo
                durum_tv.setText(R.string.ideal);
                durum_tv.setBackgroundResource(R.color.durum_ideal);

            }else if (25.8<vki && vki <= 27.3){
                //normal kilodan fazla
                durum_tv.setText(R.string.normalden_fazla);
                durum_tv.setBackgroundResource(R.color.durum_idealden_fazla);
            }else if (27.3<vki && vki <= 32.3){
                // fazla kilolu
                durum_tv.setText(R.string.fazla_kilolu);
                durum_tv.setBackgroundResource(R.color.durum_fazla_kilolu);

            }else if (32.3<vki && vki <= 34.9){
                // obez
                durum_tv.setText(R.string.obez);
                durum_tv.setBackgroundResource(R.color.durum_obez);
            }else{
                //doktor tedavisi
                durum_tv.setText(R.string.doktora);
                durum_tv.setBackgroundResource(R.color.durum_doktora);

            }


        }
    }
}
