package hr.crofi.rasporedterapija.Kosarica;

import java.util.ArrayList;
import java.util.List;

public class Kosarica {
    private List<String> PopisNaredba=new ArrayList<String>();

    public List<String> getPopis() {
        return PopisNaredba;
    }

    public void SpremiNaredbu(String naredba){
        PopisNaredba.add(naredba);
    }

    public void SpremiPopis(){}

    public void ProvjeriMemoriju(){}
}
