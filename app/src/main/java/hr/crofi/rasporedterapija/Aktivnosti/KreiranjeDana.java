package hr.crofi.rasporedterapija.Aktivnosti;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.pdf.PdfDocument;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

import android.view.View;
import android.widget.Toast;

import hr.crofi.rasporedterapija.Aktivnosti.Popup.DodavnjeKlijenta;
import hr.crofi.rasporedterapija.Aktivnosti.Popup.KreiranjeRasporeda;
import hr.crofi.rasporedterapija.Baza.Baza;
import hr.crofi.rasporedterapija.Baza.Podaci;
import hr.crofi.rasporedterapija.Kilometri.KorisniciLokacije;
import hr.crofi.rasporedterapija.MainActivity;
import hr.crofi.rasporedterapija.OpisneKlase.Dan;

import hr.crofi.rasporedterapija.OpisneKlase.Klijent;
import hr.crofi.rasporedterapija.Raspored.Pdf;
import hr.crofi.rasporedterapija.R;
public class KreiranjeDana extends AppCompatActivity implements DodavnjeKlijenta.ExampleDialogListener, KreiranjeRasporeda.ExampleDialogListener {
    Button odaberiDat;
    Button dodajK;
    Button brisiK;
    TextView t1;
    TextView t2;
    TextView t3, t4, t5,t6, t7, t8, t9;
    Spinner spin;
    Baza con;
    ArrayList<String> users = new ArrayList<String>();
    ArrayList<Klijent> users2 = new ArrayList<Klijent>();
    //Bitmap bmp, scalebmp;
    private static final int STORAGE_PERMISSION_CODE = 101;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setBackgroundDrawable(
                new ColorDrawable(Color.parseColor("#64C9A1")));
        getSupportActionBar().setTitle(" ");
        Typeface type = Typeface.createFromAsset(getAssets(),"NBold.otf");

        setContentView(R.layout.activity_kreiranje_dana);
        con=Baza.getInstance();
        odaberiDat = (Button)findViewById(R.id.button3);
        odaberiDat.setTypeface(type);
        dodajK=(Button)findViewById(R.id.dodajK);
        dodajK.setTypeface(type);
        brisiK=(Button)findViewById(R.id.brisiK);
        brisiK.setTypeface(type);
        t1= (TextView) findViewById(R.id.dat);
        t1.setTypeface(type);
        t2=(TextView) findViewById(R.id.kilometri);
        t2.setTypeface(type);
        t3=(TextView) findViewById(R.id.textView6);
        t3.setTypeface(type);
        t4=(TextView) findViewById(R.id.textView7);
        t4.setTypeface(type);
        t5=(TextView) findViewById(R.id.textView9);
        t5.setTypeface(type);
        t6=(TextView) findViewById(R.id.textView8);
        t6.setTypeface(type);
        t7=(TextView) findViewById(R.id.textView10);
        t7.setTypeface(type);
        t8=(TextView) findViewById(R.id.pocetnoS);
        t8.setTypeface(type);
        t9=(TextView) findViewById(R.id.zavrsnoS);
        t9.setTypeface(type);
        spin = (Spinner) findViewById(R.id.spinner);
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH)+1;
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        String d=dayOfMonth + "/" + month + "/" + year;
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
        dodajK.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                unesiKlijente();
            }

        });
        brisiK.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                izbrisiKorisnika();
            }

        });
        odaberiDat.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v){
                spremiDan();
                odaberiDatum(v);
            }

        });
        t1.setText(d);
        provjeriKorisnikeDatuma(d);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch(item.getItemId()){
            case R.id.spremi:
                spremiDan();
                Toast.makeText(this, "Spremljene promjene", Toast.LENGTH_LONG).show();
                return true;
            case R.id.kreirajRas:
                kreirajRaspored();
                Toast.makeText(this, "Kreirana datoteka Raspored.pdf", Toast.LENGTH_LONG).show();
                return true;
            case R.id.odjavi:
                con.Odjava();
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
        }

      return true;
    }

    @Override
  public boolean onCreateOptionsMenu(Menu menu){
      MenuInflater inf=getMenuInflater();
      inf.inflate(R.menu.menu, menu);
        return true;
  }
    public void odaberiDatum(View view){
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(KreiranjeDana.this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month=month+1;
                        String datumDana=day + "/" + month + "/" + year;
                        t1.setText(datumDana);
                        provjeriKorisnikeDatuma(datumDana);
                    }
                }, year, month, dayOfMonth);

        datePickerDialog.show();
    }
    public void provjeriKorisnikeDatuma(String dat) {
        Dan d=con.provjeriDatum(dat);
        users = new ArrayList<String>();
        users2= new ArrayList<Klijent>();
        for(int i=0;i<d.getPopis().size();i++){
            int x=i+1;
            String id=x+".";
            users.add(id+d.getPopis().get(i).getPrezime());
            users2.add(d.getPopis().get(i));
        }
        updateSpinner();
        String km=d.getKm();
        String ps=d.getPstanje();
        String zs=d.getZstanje();
        t2.setText(km);
        t8.setText(ps);
        t9.setText(zs);
    }

    public void unesiKlijente(){
        DodavnjeKlijenta dk=new DodavnjeKlijenta();
        dk.show(getSupportFragmentManager(),"Izaberi klijenta");

    }

    public void kreirajRaspored(){
        KreiranjeRasporeda kr= new KreiranjeRasporeda();
        kr.show(getSupportFragmentManager(),"Odaberi datume");

    }
    public void updateSpinner(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, users);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(adapter);
    }

    public void izbrisiKorisnika(){
        Long broj=spin.getSelectedItemId();
        int id=broj.intValue();
        if(users.size()!=1 || users.size()!=0) {
            for (int i = 0; i < users.size(); i++) {
                if (i == id) {
                    users2.remove(i);
                    users.remove(i);
                }
            }
            for (int i = 0; i < users.size(); i++) {
                int noviBroj = i + 1;
                String kor = users2.get(i).getPrezime();
                String novi = noviBroj + "." + kor;
                users.set(i, novi);
            }
        }
        else{
            users = new ArrayList<String>();
            users2 = new ArrayList<Klijent>();
        }
        updateSpinner();
        KorisniciLokacije kl= new KorisniciLokacije();
        String km=kl.TraziKorisnike(users2);
        t2.setText(km);
    }

    public void spremiDan(){
        String datum=t1.getText().toString();
        String km=t2.getText().toString();
        con.updateDan(datum, km, users2);
        String lista="";
        for(int i=0;i<users2.size();i++){
            lista=lista+users2.get(i).getId();
            if(i!=users2.size()-1){
                lista=lista+",";
            }
        }
        String Datum = datum;
        String Poc = t8.getText().toString();
        String Zav = t9.getText().toString();
        String Pkm = km;
        String Popis = lista;
        Integer Mjesec = 9;
        Integer Godina = 2022;
        Integer Zaposlenik = con.getUlogiraniZaposlenik().getId();
        String link = "http://crofi.com/assets/assets/images/RasporedBaza/UpdateDan.php?Datum="+Datum+"&Poc="+Poc+"&Zav="+Zav+"&Pkm="+Pkm+"&Popis="+Popis+"&Mjesec="+Mjesec+"&Godina="+Godina+"&Zaposlenik="+Zaposlenik;
        Object result= null;
        try {
            result = new Podaci(this,null, 4).execute(link).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void dodajKorisnike(ArrayList<Klijent> Dodani) {
        int pocetni=users.size();
         for(int i=0;i<Dodani.size();i++){
             pocetni=pocetni+1;
             String id=String.valueOf(pocetni);
             users.add(id+"."+Dodani.get(i).getPrezime());
             users2.add(Dodani.get(i));
         }
        updateSpinner();
        KorisniciLokacije kl= new KorisniciLokacije();
        String km=kl.TraziKorisnike(users2);
        t2.setText(km);
    }

    public List<Dan> DaniRaspored(Date p, Date k){
        List<Dan> sviDani=con.getSviDani();
        List<Dan> Dani=new ArrayList<Dan>();
        final SimpleDateFormat form=new SimpleDateFormat("dd/MM/yyyy");
        for(int i=0;i<sviDani.size();i++){
            try {
                Date d=form.parse(sviDani.get(i).getDatum());
                if(d.after(p) && d.before(k)){
                    Dani.add(sviDani.get(i));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < Dani.size()-1; i++)
            for (int j = 0; j < Dani.size()-i-1; j++) {
                try {
                    Date d=form.parse(Dani.get(j).getDatum());
                    Date d2=form.parse(Dani.get(j+1).getDatum());
                    if (d.after(d2))
                    {
                        Dan dan=Dani.get(j);
                        Dani.set(j, Dani.get(j+1));
                        Dani.set(j+1, dan);
                    }
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        return Dani;
    }
    @Override
    public void proslijediDatume(String d1, String d2){
        System.out.println(d1+" "+d2);
        SimpleDateFormat form=new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date pocetak=form.parse(d1);
            Date kraj=form.parse(d2);
            List<Dan> SortiraniDani=DaniRaspored(pocetak, kraj);
            Pdf p=new Pdf();
            PdfDocument pdf= new PdfDocument();
            pdf=p.Kreirajpdf(SortiraniDani);
            File file=new File(Environment.getExternalStorageDirectory(),"/Raspored.pdf");
            pdf.writeTo(new FileOutputStream(file));
            pdf.close();
            Toast.makeText(KreiranjeDana.this, "Kreirana je datoteka pod imenom Raspored.pdf", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void checkPermission(String permission, int requestCode)
    {
        if (ContextCompat.checkSelfPermission(KreiranjeDana.this, permission) == PackageManager.PERMISSION_DENIED) {

            ActivityCompat.requestPermissions(KreiranjeDana.this, new String[] { permission }, requestCode);
        }
        else {
            Toast.makeText(KreiranjeDana.this, "Permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults)
    {
        super.onRequestPermissionsResult(requestCode,
                permissions,
                grantResults);

        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(KreiranjeDana.this, "Storage Permission Granted", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(KreiranjeDana.this, "Storage Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
