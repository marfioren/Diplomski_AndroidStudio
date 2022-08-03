package hr.crofi.rasporedterapija.OpisneKlase;

public class Klijent {

    private String id;
    private String ime;
    private String prezime;
    private String Adresa;
    private String Lat;
    private String Lon;
    public Klijent(String id, String ime, String prezime, String adresa, String Lat, String Lon) {
        this.id = id;
        this.ime = ime;
        this.prezime = prezime;
        this.Adresa = adresa;
        this.Lat=Lat;
        this.Lon=Lon;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getAdresa() {
        return Adresa;
    }

    public void setAdresa(String adresa) {
        Adresa = adresa;
    }

    public String getLat() {
        return Lat;
    }

    public void setLat(String lat) {
        Lat = lat;
    }

    public String getLon() {
        return Lon;
    }

    public void setLon(String lon) {
        Lon = lon;
    }
}
