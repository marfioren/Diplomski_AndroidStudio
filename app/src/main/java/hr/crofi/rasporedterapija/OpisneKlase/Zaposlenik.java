package hr.crofi.rasporedterapija.OpisneKlase;

public class Zaposlenik {
    private int Id;
    private String Ime;
    private String Prezime;
    private String Auto;
    private String Registracija;

    public Zaposlenik(int id, String ime, String prezime, String auto, String registracija) {
        Id = id;
        Ime = ime;
        Prezime = prezime;
        Auto = auto;
        Registracija = registracija;
    }

    public int getId() {
        return Id;
    }

    public String getIme() {
        return Ime;
    }

    public String getPrezime() {
        return Prezime;
    }

    public String getAuto() {
        return Auto;
    }

    public String getRegistracija() {
        return Registracija;
    }
}
