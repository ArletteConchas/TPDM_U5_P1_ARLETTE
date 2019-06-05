package com.example.tpdm_u5_p1_arletteconchas;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText telefono, mensaje;
    Button enviar;

    ConectarBD base;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        telefono = findViewById(R.id.edtel);
        mensaje = findViewById(R.id.edmen);
        enviar = findViewById(R.id.btenv);

        solicitarPermisos();

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                solicitarPermisos();
                enviarSMS();
            }
        });
    }

    private void enviarSMS() {
        try{
            String t = telefono.getText().toString();
            String m = mensaje.getText().toString();

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(t,null,m,null,null);
            Toast.makeText(this, "SE ENVIO CORRECTAMENTE", Toast.LENGTH_SHORT).show();
            telefono.setText("");mensaje.setText("");
        }catch (Exception e){
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void solicitarPermisos() {

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_SMS)
                != PackageManager.PERMISSION_GRANTED){
            //Entra si el permiso esta denegado, ya que será diferente a permiso OTORGADO
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_SMS},3);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.RECEIVE_SMS)
                != PackageManager.PERMISSION_GRANTED){
            //Entra si el permiso esta denegado, ya que será diferente a permiso OTORGADO
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.RECEIVE_SMS},4);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
            //Entra si el permiso esta denegado, ya que será diferente a permiso OTORGADO
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.READ_PHONE_STATE},1);
        }
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)
                != PackageManager.PERMISSION_GRANTED){
            //Entra si el permiso esta denegado, ya que será diferente a permiso OTORGADO
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.SEND_SMS},2);
        }
    }
}
