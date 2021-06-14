package com.kotlin.laksnsystemtest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentTransaction;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

public class MainActivity extends AppCompatActivity {
    CheckBox pizza,coffe,burger;
    Button buttonOrder,generate_qr_code,scann_qr;
    String contents;
    LinearLayout linear_layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addListenerOnButtonClick();
    }
    public void addListenerOnButtonClick(){
        // Checking permission for camera and storage
        camerapermission();
        //Getting instance of CheckBoxes and Button from the activty_main.xml file
        pizza=(CheckBox)findViewById(R.id.checkBox);
        linear_layout=findViewById(R.id.linear_layout);
        coffe=(CheckBox)findViewById(R.id.checkBox2);
        burger=(CheckBox)findViewById(R.id.checkBox3);
        buttonOrder=(Button)findViewById(R.id.button);
        generate_qr_code=findViewById(R.id.generate_qr_code);
        scann_qr=findViewById(R.id.scann_qr);
        generate_qr_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                scann_qr.setVisibility(View.VISIBLE);
              Intent intent= new Intent(MainActivity.this,QRGenerateActivity.class);
              startActivity(intent);
            }
        });
        scann_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IntentIntegrator integrator = new IntentIntegrator(MainActivity.this);
                integrator.initiateScan(IntentIntegrator.QR_CODE_TYPES);
            }
        });
        //Applying the Listener on the Button click
        buttonOrder.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                int totalamount=0;
                if(!coffe.isChecked() && !pizza.isChecked() && !burger.isChecked()){
                    Toast.makeText(getApplicationContext(),"Please Select atleast one item from menu", Toast.LENGTH_LONG).show();
                }
                StringBuilder result=new StringBuilder();
                result.append("Selected Items:");
                if(pizza.isChecked()){
                    result.append("\nPizza Rs.150");
                    totalamount+=150;
                    Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
                }
                if(coffe.isChecked()){
                    result.append("\nCoffe Rs.60");
                    totalamount+=60;

                    Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
                }
                if(burger.isChecked()){
                    result.append("\nBurger Rs.180");
                    totalamount+=180;
                    Toast.makeText(getApplicationContext(), result.toString(), Toast.LENGTH_LONG).show();
                }if(coffe.isChecked()&&pizza.isChecked()&&burger.isChecked()){
                    totalamount+=totalamount*10/100;
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(MainActivity.this);
                    result.append("\nTotal ammount with 10% tip:" +"Rs."+totalamount);
                    builder1.setMessage( result.toString());
                    builder1.setCancelable(true);
                    builder1.setPositiveButton(
                            "Yes",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alert11 = builder1.create();
                    alert11.show();
                }

            }

        });
    }

    private void camerapermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)== PackageManager.PERMISSION_DENIED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.CAMERA,Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},101);
            }

        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);
        if (result != null) {
            contents = result.getContents();
            if (contents != null) {
                linear_layout.setVisibility(View.VISIBLE);
            }


//        if (result != null) {
//            contents = result.getContents();
//            if (contents != null && contents.equalsIgnoreCase(str_gymName)) {
//                showDialog(R.string.result_succeeded, result.toString());
//            } else {
//                AlertDialog.Builder builder = new AlertDialog.Builder(this);
//                builder.create();
//                builder.setMessage(R.string.result_failed);
//                builder.setMessage(R.string.result_failed_why);
//                builder.setPositiveButton(R.string.ok_button, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.dismiss();
//                    }
//                });
//                builder.show();
//
//            }
//        }
        }
    }
}