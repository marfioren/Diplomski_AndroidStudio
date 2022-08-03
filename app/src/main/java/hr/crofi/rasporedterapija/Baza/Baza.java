package hr.crofi.rasporedterapija.Baza;

import android.content.Context;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import hr.crofi.rasporedterapija.OpisneKlase.Dan;
import hr.crofi.rasporedterapija.OpisneKlase.Klijent;

public class Baza {
    private List<Klijent> sviKlijenti;
    private List<Dan> sviDani;//iz tog mjeseca
    private static Baza instanca = null;
    private Context context;
    private int stavljeno=0;
    private static int Ulogiranizaposlenik;

    public static Baza getInstance() {
        if (instanca == null) {
            instanca = new Baza();
        }
        return instanca;
    }

    private Baza() {
        sviKlijenti=new ArrayList<Klijent>();
        sviDani=new ArrayList<Dan>();

    }
    public void StaviSveKlijente(List<String> SviKlijenti){
        List<String> podaci=new ArrayList<String>();
        podaci=SviKlijenti;
        for(int i=0;i<podaci.size();i++){
            String[] polje = podaci.get(i).split("#",6);
            Klijent k=new Klijent(polje[0],polje[1], polje[2], polje[3], polje[4], polje[5]);
            sviKlijenti.add(k);
        }
        stavljeno=1;
    }

    public void StaviSveDane(List<String> SviDani){
        List<String> podaci=new ArrayList<String>();
        podaci=SviDani;
        for(int i=0;i<podaci.size();i++){
            String[] polje = podaci.get(i).split("#",8);
            String[] Popis=polje[5].split(",");
            List<String> list = Arrays.asList(Popis);

            List<Klijent> dodaniKorisnici=new ArrayList<Klijent>();
            for(int j=0;j<list.size();j++){

                Klijent k=new Klijent("","", "", "", "", "");
                int r=0;
                boolean ok=false;
                while(ok=false || r<sviKlijenti.size()){
                    String t=sviKlijenti.get(r).getId();
                    String s=list.get(j);
                    if(s.equals(t)){
                        k=sviKlijenti.get(r);
                        dodaniKorisnici.add(k);
                        ok=true;
                    }
                    r++;

                }
            }
            Dan d=new Dan(polje[0], polje[1], polje[2], polje[3], polje[4], dodaniKorisnici, Integer.parseInt(polje[6]), Integer.parseInt(polje[7]));
            sviDani.add(d);
        }
        stavljeno=2;
    }

    public Klijent vratiKlijente(String klijent){
        Klijent k=new Klijent("","", "", "", "", "");
        for(int i=0;i<sviKlijenti.size();i++){
                if(klijent==sviKlijenti.get(i).getPrezime()){
                    k=sviKlijenti.get(i);
                }
        }

        return k;
    }

    public List<Klijent> getSviKlijenti() {
        return sviKlijenti;
    }


    public List<Dan> getSviDani() {
        return sviDani;
    }

    public int getStavljeno() {
        return stavljeno;
    }

    public int getUlogiranizaposlenik() {
        return Ulogiranizaposlenik;
    }

    public void setUlogiranizaposlenik(int ulogiranizaposlenik) {
        Ulogiranizaposlenik = ulogiranizaposlenik;
    }

    public Dan provjeriDatum(String datum){
        for(int i=0;i<sviDani.size();i++){
            String dat=sviDani.get(i).getDatum();
            if(datum.equals(dat)){
                return sviDani.get(i);
            }
        }
        List<Klijent> popis=new ArrayList<Klijent>();
        Dan d= new Dan("", datum, "0", "0", "0", popis, 1,2022);
        sviDani.add(d);
        return d;
    }

    public void updateDan(String datum, String km, ArrayList<Klijent> popis){
        int i=0;
        String kilometri=km.replace("km","");
        boolean ok=false;
        while(ok=false || i<sviDani.size()){
            if(datum.equals(sviDani.get(i).getDatum())){
                sviDani.get(i).setKm(kilometri);
                sviDani.get(i).setPopis(popis);
                ok=true;
            }
            i++;
        }


    }
}
