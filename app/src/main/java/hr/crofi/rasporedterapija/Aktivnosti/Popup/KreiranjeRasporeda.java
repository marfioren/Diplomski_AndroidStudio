package hr.crofi.rasporedterapija.Aktivnosti.Popup;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import hr.crofi.rasporedterapija.Aktivnosti.KreiranjeDana;
import hr.crofi.rasporedterapija.R;

public class KreiranjeRasporeda extends AppCompatDialogFragment {
    private KreiranjeRasporeda.ExampleDialogListener listener;
    Button promjeni1;
    Button promjeni2;
    TextView t1;
    TextView t2;
    String d1;
    String d2;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.raspored, null);
        promjeni1=(Button)v.findViewById(R.id.promjenid1);
        promjeni2=(Button)v.findViewById(R.id.promjenid2);
        t1= (TextView) v.findViewById(R.id.dat1);
        t2=(TextView) v.findViewById(R.id.dat2);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date = new Date();
        String d=formatter.format(date);
        t1.setText(d);
        t2.setText(d);
        promjeni1.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                odaberiDatum(v,1);
            }

        });
        promjeni2.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                odaberiDatum(v,2);
            }

        });
        builder.setView(v)
                .setTitle("Odaberi datume")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Kreiraj", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        d1=t1.getText().toString();
                        d2=t2.getText().toString();
                        listener.proslijediDatume(d1,d2);
                    }
                });
        return builder.create();
    }

    public void odaberiDatum(View view, int dat){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        final int x=dat;
        DatePickerDialog datePickerDialog = new DatePickerDialog(this.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month=month+1;
                        String datumDana=day + "/" + month + "/" + year;
                        if(x==1){
                            t1.setText(datumDana);
                        }
                        else{
                            t2.setText(datumDana);
                        }
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (KreiranjeRasporeda.ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }

    public interface ExampleDialogListener {
        void proslijediDatume(String d1, String d2);
    }
}
