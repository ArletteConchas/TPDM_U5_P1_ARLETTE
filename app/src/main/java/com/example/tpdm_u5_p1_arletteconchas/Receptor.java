package com.example.tpdm_u5_p1_arletteconchas;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.SmsMessage;
import android.widget.Toast;

public class Receptor extends BroadcastReceiver {
    ConectarBD base;

    @Override
    public void onReceive(Context context, Intent intent) {
        base=new ConectarBD(context, "Base1", null, 1);
        insertar_datos();
        Bundle extras=intent.getExtras();
        Object[] pdus = (Object[]) extras.get("pdus");
        SmsMessage mensaje=SmsMessage.createFromPdu((byte[])pdus[0]);
        //Toast.makeText(context, "Telefono origen: "+mensaje.getOriginatingAddress() + "Contenido: "+mensaje.getMessageBody(),Toast.LENGTH_LONG).show();
        if(mensaje.getMessageBody().startsWith("PREDICCION")){
            if(mensaje.getMessageBody().split("-").length==2){
                String m =mensaje.getMessageBody().split("-")[1];
                Toast.makeText(context, "Prediccion para tu signo "+m+": "+buscarPorSigno(m),Toast.LENGTH_LONG).show();
                enviarSMS(mensaje.getOriginatingAddress(),"Prediccion para tu signo "+m+": "+buscarPorSigno(m),context);
            }
        } else{
            Toast.makeText(context, "El mensaje enviado no coincide con las caracteristicas solicitadas",Toast.LENGTH_LONG).show();
            enviarSMS(mensaje.getOriginatingAddress(),"El mensaje enviado no coincide con las caracteristicas solicitadas",context);
        }
    }

    public String buscarPorSigno(String sig){
        try{
            SQLiteDatabase base = this.base.getReadableDatabase();
            String[] claves = {sig};
            Cursor c = base.rawQuery("SELECT * FROM Horoscopo WHERE Signo = ?",claves);
            System.out.println(c.getCount());
            if(c.moveToFirst()){
                return(c.getString(1));
            } else {
                return("No se encontraron coincidencias");
            }
        } catch (SQLiteException e){
            return (e.getMessage());
        }
    }

    private void enviarSMS(String t, String m, Context c) {
        try{
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(t,null,m,null,null);
        }catch (Exception e){
            Toast.makeText(c, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    void insertar_datos(){
        SQLiteDatabase db = this.base.getWritableDatabase();
        db.execSQL("INSERT INTO Horoscopo VALUES('ARIES','MENSAJE ARIES')");
        db.execSQL("INSERT INTO Horoscopo VALUES('TAURO','MENSAJE TAURO')");
        db.execSQL("INSERT INTO Horoscopo VALUES('GEMINIS','MENSAJE GEMINIS')");
        db.execSQL("INSERT INTO Horoscopo VALUES('CANCER','MENSAJE CANCER')");
        db.execSQL("INSERT INTO Horoscopo VALUES('LEO','MENSAJE LEO')");
        db.execSQL("INSERT INTO Horoscopo VALUES('VIRGO','MENSAJE VIRGO')");
        db.execSQL("INSERT INTO Horoscopo VALUES('LIBRA','MENSAJE LIBRA')");
        db.execSQL("INSERT INTO Horoscopo VALUES('ACUARIO','MENSAJE ACUARIO')");
        db.execSQL("INSERT INTO Horoscopo VALUES('PISCIS','MENSAJE PISCIS')");
        db.execSQL("INSERT INTO Horoscopo VALUES('ESCORPIO','MENSAJE ESCORPIO')");
        db.execSQL("INSERT INTO Horoscopo VALUES('SAGITARIO','MENSAJE SAGITARIO')");
        db.execSQL("INSERT INTO Horoscopo VALUES('CAPRICORNIO','MENSAJE CAPRICORNIO')");
    }
}
