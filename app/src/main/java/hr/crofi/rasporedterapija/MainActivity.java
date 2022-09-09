package hr.crofi.rasporedterapija;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import hr.crofi.rasporedterapija.Aktivnosti.KreiranjeDana;
import hr.crofi.rasporedterapija.Baza.Baza;
import hr.crofi.rasporedterapija.Baza.Podaci;

public class MainActivity extends AppCompatActivity{
    Button mybtn;
    TextView t1, t2, t3;
    Baza con;
    private EditText username,password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        Typeface type = Typeface.createFromAsset(getAssets(),"NBold.otf");
        setContentView(R.layout.activity_main);
        con=Baza.getInstance();
        mybtn = (Button)findViewById(R.id.button);
        mybtn.setTypeface(type);
        t1= (TextView) findViewById(R.id.textView2);
        t2= (TextView) findViewById(R.id.textView);
        t2.setTypeface(type);
        t3= (TextView) findViewById(R.id.textView3);
        t3.setTypeface(type);
        username = (EditText)findViewById(R.id.korime);
        username.setTypeface(type);
        password = (EditText)findViewById(R.id.pass);
        password.setTypeface(type);

    }
    public void GetTxtFromSQL(View view){
        String u = username.getText().toString();
        String p = password.getText().toString();
        String link1 = "http://crofi.com/assets/assets/images/RasporedBaza/Logiranje.php?username="+u+"&password="+p;
        String link2 = "http://crofi.com/assets/assets/images/RasporedBaza/Svilijenti.php";
        Object result= null;
        String rezultat=null;
        Podaci p1=new Podaci(this,t1, 1);
        try {
            result=p1.execute(link1).get();
            rezultat=p1.result();
            if(rezultat.equals("error")||rezultat.equals("No")||rezultat==null) {
                Toast.makeText(this, "Krivi podaci", Toast.LENGTH_LONG).show();
            }
            else{
                con.PrijaviZaposlenika(rezultat);
                int idKor=con.getUlogiraniZaposlenik().getId();
                String link3 = "http://crofi.com/assets/assets/images/RasporedBaza/SviDani.php?id="+idKor;
                new Podaci(this, t1, 2).execute(link2).get();
                new Podaci(this, t1, 3).execute(link3).get();
                Intent intent = new Intent(this, KreiranjeDana.class);
                startActivity(intent);
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
