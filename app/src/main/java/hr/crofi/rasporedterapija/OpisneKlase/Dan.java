package hr.crofi.rasporedterapija.OpisneKlase;
import java.util.ArrayList;
import java.util.List;

public class Dan {
    private String Id;
    private String Datum;
    private List<Klijent> Popis=new ArrayList<Klijent>();
    private String Km;
    private int Mjesec;
    private int Godina;
    private String Pstanje;
    private String Zstanje;

    public Dan(String id, String datum, String pstanje, String zstanje, String km, List<Klijent> popis, int mjesec, int godina) {
        Id=id;
        Datum = datum;
        Popis = popis;
        Km = km;
        Mjesec = mjesec;
        Godina = godina;
        Pstanje = pstanje;
        Zstanje = zstanje;
    }

    public String getDatum() {
        return Datum;
    }

    public void setDatum(String datum) {
        Datum = datum;
    }

    public List<Klijent> getPopis() {
        return Popis;
    }

    public void setPopis(List<Klijent> popis) {
        Popis = popis;
    }

    public String getKm() {
        return Km;
    }

    public void setKm(String km) {
        Km = km;
    }

    public int getMjesec() {
        return Mjesec;
    }

    public void setMjesec(int mjesec) {
        Mjesec = mjesec;
    }

    public int getGodina() {
        return Godina;
    }

    public void setGodina(int godina) {
        Godina = godina;
    }

    public String getPstanje() {
        return Pstanje;
    }

    public void setPstanje(String pstanje) {
        Pstanje = pstanje;
    }

    public String getZstanje() {
        return Zstanje;
    }

    public void setZstanje(String zstanje) {
        Zstanje = zstanje;
    }
}
