package hr.crofi.rasporedterapija.Aktivnosti.Popup;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.widget.TextView;
import android.content.pm.PackageManager;
import hr.crofi.rasporedterapija.Baza.Baza;
import hr.crofi.rasporedterapija.OpisneKlase.Klijent;
import hr.crofi.rasporedterapija.R;
public class DodavnjeKlijenta extends AppCompatDialogFragment {
    Spinner popis;
    Baza con;
    List<Klijent> K;
    ArrayList<String> users = new ArrayList<String>();
    ArrayList<Klijent> DodUsers = new ArrayList<Klijent>();
    Button dodaj;
    private ExampleDialogListener listener;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder= new AlertDialog.Builder(getActivity());
        LayoutInflater inflater=getActivity().getLayoutInflater();
        View v=inflater.inflate(R.layout.dodavanje_klijenta, null);
        con=Baza.getInstance();
        K=con.getSviKlijenti();
        for(int i=0;i<K.size();i++){
            users.add(K.get(i).getPrezime());
        }
        popis=(Spinner) v.findViewById(R.id.sviKorisnici);
        dodaj=(Button) v.findViewById(R.id.dod);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(), android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        popis.setAdapter(adapter);
        dodaj.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dodavanjeKorisnika();
            }
        });
        builder.setView(v)
                .setTitle("Odaberi klijente:")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Spremi", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.dodajKorisnike(DodUsers);
                    }
                });

        return builder.create();
    }

    public void dodavanjeKorisnika(){
     Long prezime=popis.getSelectedItemId();
     int i=prezime.intValue();
     DodUsers.add(K.get(i));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ExampleDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "");
        }
    }

    public interface ExampleDialogListener {
        void dodajKorisnike(ArrayList<Klijent> Dodani);
    }
}
