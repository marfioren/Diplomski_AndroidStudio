package hr.crofi.rasporedterapija.Kilometri;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import hr.crofi.rasporedterapija.Baza.Baza;
import hr.crofi.rasporedterapija.OpisneKlase.Klijent;

public class KorisniciLokacije {
    private double kilometri=0;
    public String TraziKorisnike( ArrayList<Klijent> Klijenti){
      Object duljina= new Object();
      for(int i=0;i<Klijenti.size()-1;i++){
           String pLat=Klijenti.get(i).getLat();
           String Plon=Klijenti.get(i).getLon();
           String zLat=Klijenti.get(i+1).getLat();
           String Zlon=Klijenti.get(i+1).getLon();
              try {
              duljina=new IzracunavanjeKm().execute(pLat, Plon, zLat, Zlon).get();
              String km=duljina.toString().replace("km","");
              double d=Double.parseDouble(km);
              kilometri=kilometri+d;
              } catch (ExecutionException e) {
                  e.printStackTrace();
              } catch (InterruptedException e) {
                  e.printStackTrace();
              }

      }
      String result = String.format("%.2f", kilometri);
      return result;
    }



}
