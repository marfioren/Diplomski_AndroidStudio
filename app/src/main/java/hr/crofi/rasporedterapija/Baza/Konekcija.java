package hr.crofi.rasporedterapija.Baza;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import hr.crofi.rasporedterapija.OpisneKlase.Dan;
import hr.crofi.rasporedterapija.OpisneKlase.Klijent;

public class Konekcija{
 private int zadatak;

 public String SviKlijenti(String podaci){
     List<String> razdvojeniPodaci=new ArrayList<String>();
     razdvojeniPodaci=RazdvojiPodatke(podaci);
    Baza con=Baza.getInstance();
     if(con.getStavljeno()==0){
         con.StaviSveKlijente(razdvojeniPodaci);
     }
     List<Klijent> K=con.getSviKlijenti();
     String t="OK";
     return t;
 }
public String SviDani(String podaci){
        List<String> razdvojeniPodaci=new ArrayList<String>();
        if(podaci.length()!=0) {
            razdvojeniPodaci = RazdvojiPodatke(podaci);
            Baza con = Baza.getInstance();
            if (con.getStavljeno() != 2) {
                con.StaviSveDane(razdvojeniPodaci);
            }
            List<Dan> D = con.getSviDani();
        }
        String t="OK";
        return t;
    }
public String DohvatiPodatke(String link){
     try{
         URL url = new URL(link);
         HttpClient client = new DefaultHttpClient();
         HttpGet request = new HttpGet();
         request.setURI(new URI(link));
         HttpResponse response = client.execute(request);
         BufferedReader in = new BufferedReader(new
         InputStreamReader(response.getEntity().getContent()));
         StringBuffer sb = new StringBuffer("");
         String line="";
         while ((line = in.readLine()) != null) {
             sb.append(line);
             break;
         }
         in.close();
         return sb.toString();
     } catch(Exception e){
         return "error";
     }

 }

private List<String> RazdvojiPodatke(String podaci){
    List<String> razdvojeniPodaci=new ArrayList<String>(Arrays.asList(podaci.split("///")));
    return razdvojeniPodaci;

}

public void setZadatak(int zadatak) {
        this.zadatak = zadatak;
    }
}
