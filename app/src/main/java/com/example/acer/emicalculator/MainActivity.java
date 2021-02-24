package com.example.acer.emicalculator;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.time.Month;

public class MainActivity extends AppCompatActivity
{

    Button emiCalcBtn,ClrBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText P = (EditText)findViewById(R.id.principal);
        final EditText I = (EditText)findViewById(R.id.interest);
        final EditText Y = (EditText)findViewById(R.id.years);
        final EditText result = (EditText)findViewById(R.id.emi);
        final EditText TI = (EditText)findViewById(R.id.interest_total);

        emiCalcBtn = (Button)findViewById(R.id.btn_calculate);
        ClrBtn = (Button)findViewById(R.id.btn_reset);

        emiCalcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String st1 = P.getText().toString();
                String st2 = I.getText().toString();
                String st3 = Y.getText().toString();

                if(TextUtils.isEmpty(st1) && TextUtils.isEmpty(st2) && TextUtils.isEmpty(st3))
                {
                    P.setError("Enter principle amount");
                    P.requestFocus();
                    I.setError("Enter Interest");
                    I.requestFocus();
                    Y.setError("Enter years");
                    Y.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(st1) && TextUtils.isEmpty(st2))
                {
                    P.setError("Enter principle amount");
                    P.requestFocus();
                    I.setError("Enter Interest");
                    I.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(st1)  && TextUtils.isEmpty(st3))
                {
                    P.setError("Enter principle amount");
                    P.requestFocus();
                    Y.setError("Enter years");
                    Y.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(st2) && TextUtils.isEmpty(st3))
                {
                    I.setError("Enter Interest");
                    I.requestFocus();
                    Y.setError("Enter years");
                    Y.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(st1))
                {
                    P.setError("Enter principle amount");
                    P.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(st2))
                {

                    I.setError("Enter Interest");
                    I.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(st3))
                {
                    Y.setError("Enter years");
                    Y.requestFocus();
                    return;
                }

                float p = Float.parseFloat(st1);
                float i = Float.parseFloat(st2);
                float y = Float.parseFloat(st3);

                float Principal = calPrinc(p);

                float Rate = calInt(i);

                float Months = calMnth(y);

                float Dvdnt = calDvdnt(Rate,Months);

                float FD = calFinalDvdnt(Principal,Rate,Dvdnt);

                float D = calDivider(Dvdnt);

                float emi =  calEmi(FD,D);

                float TA = calTa(emi,Months);

                float ti = calTi(TA,Principal);

                result.setText(String.valueOf(emi));

                TI.setText(String.valueOf(ti));

            }
        });

        //reset button code

        ClrBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                P.setText("");
                I.setText("");
                Y.setText("");
                result.setText("");
                TI.setText("");

            }
        });

    }

    @Override
    public void onBackPressed() {


        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        MainActivity.super.onBackPressed();

                    }
                })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {

                        dialog.cancel();
                    }
                });


        AlertDialog alertDialog = builder.create();
        alertDialog.show();




    }

    //Set icons to actionbar


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if(id==R.id.title3)
        {
            //Toast.makeText(this,"Item 3 clicked",Toast.LENGTH_SHORT).show();
            openDialog();
        }
        if(id==R.id.title4)
        {
           // Toast.makeText(this,"Item 4 clicked",Toast.LENGTH_SHORT).show();
            Intent myIntent = new Intent(Intent.ACTION_SEND);
            myIntent.setType("Text/plain");
            String shareBody = "EMI Calculator";
            String shareSub = "This EMI app is convinient to handle";
            myIntent.putExtra(Intent.EXTRA_TEXT,shareBody);
            myIntent.putExtra(Intent.EXTRA_SUBJECT,shareSub);
            startActivity(Intent.createChooser(myIntent,"Share Using"));


        }


        return super.onOptionsItemSelected(item);
    }

    private void openDialog() {

        ExampleDialog exampleDialog = new ExampleDialog();
        exampleDialog.show(getSupportFragmentManager(),"examle dialog");

    }

    private float calTi(float TA, float Principal) {

            return (float)(TA - Principal);

    }

    private float calTa(float emi, float Months) {

        return (float)(emi * Months);
    }

    private float calEmi(float FD, float D) {

        return  (float) (FD/D);
    }

    private float calDivider(float Dvdnt) {

        return  (float) (Dvdnt - 1);
    }

    private float calFinalDvdnt(float Principal, float Rate, float Dvdnt) {

        return (float) (Principal * Rate * Dvdnt);

    }

    private float calDvdnt(float Rate, float Months) {

        return (float) (Math.pow(1+Rate, Months));
    }

    private float calMnth(float y) {

        return (float)(y * 12);
    }

    private float calInt(float i) {

        return (float)(i/12/100);
    }

    private float calPrinc(float p) {

        return (float)(p);
    }
}
