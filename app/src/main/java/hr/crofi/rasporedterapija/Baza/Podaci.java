package hr.crofi.rasporedterapija.Baza;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

public class Podaci extends AsyncTask{
    private Context context;
    private TextView statusField;//tu će se ispisivati korisnici
    private int zadatak;
    private String rezultat;
    public Podaci(Context context, TextView statusField, int zadatak) {
        this.context = context;
        this.statusField=statusField;
        this.zadatak=zadatak;
    }

    protected void onPreExecute(){
    }
    @Override
    protected String doInBackground(Object[] objects) {
        String link=objects[0].toString();
        Konekcija con= new Konekcija();
        String rez="";
        switch(this.zadatak){
            case 1:
                rez=con.DohvatiPodatke(link);
                rezultat=rez;
                break;
            case 2:
                String podaci=con.DohvatiPodatke(link);
                String d=con.SviKlijenti(podaci);
                rez=d;
                break;
            case 3:
                String podaci2=con.DohvatiPodatke(link);
                String dani=con.SviDani(podaci2);
                rez=dani;
                break;
            case 4:
                String status=con.DohvatiPodatke(link);
                System.out.println(status);
                rez=status;
                break;
        }

        return rez;
    }

    @Override
    protected void onPostExecute(Object o) {

        rezultat=o.toString();

    }
    public String result(){
        return rezultat;

    }
}
