package hr.crofi.rasporedterapija.Raspored;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;

import java.util.ArrayList;
import java.util.List;

import hr.crofi.rasporedterapija.OpisneKlase.Dan;

public class Pdf {
    List<Dan> Dani=new ArrayList<Dan>();
    List<Integer> index=new ArrayList<Integer>();

    public PdfDocument Kreirajpdf(List<Dan> SortiraniDan){
        System.out.println("broj dana: "+SortiraniDan.size());
        Dani=SortiraniDan;
        PdfDocument pdf= new PdfDocument();
        int b=0;
        int bDani=0;
        for(int i=0;i<Dani.size();i++){
            int brojLjudi=b+Dani.get(i).getPopis().size();
            System.out.println("broj ljudi u danu : "+Dani.get(i).getDatum()+" "+brojLjudi);
            if(brojLjudi<55) {
                b = b + Dani.get(i).getPopis().size();
                if(Dani.get(i).getPopis().size()==0){
                    b=b+3;
                }
                bDani=bDani+1;
            }
            else{
                b=0;
                bDani=0;
                System.out.println("dodani index: "+i);
                index.add(i);
            }
        }
        if(index.size()<1){
            index.add(Dani.size());
        }
        int stranice=index.size()+1;
        System.out.println("broj strainica: "+stranice);
        for(int i=0;i<index.size();i++) {
            if(i!=0) {
                KreirajStranicu(i + 1, pdf, index.get(i-1), index.get(i));
            }
            else{
                KreirajStranicu(i + 1, pdf, 0, index.get(i));
            }
        }
        KreirajStranicu(stranice, pdf, index.get(index.size()-1), Dani.size());
        return pdf;
    }

 public void KreirajStranicu(int broj, PdfDocument pdf, int brojIndexaP, int brojIndexaZ){
     PdfDocument.PageInfo pi= new PdfDocument.PageInfo.Builder(1200,2010,broj).create();
     PdfDocument.Page myPage=pdf.startPage(pi);
     int pageWidth=1200;
     int pageHeight=2010;
     Paint paint=new Paint();
     Paint title=new Paint();
     Canvas c= myPage.getCanvas();
     title.setTextAlign(Paint.Align.LEFT);
     title.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
     title.setTextSize(25);
     c.drawText("USTANOVA ZA NJEGU U KUĆI DOMNIUS, JARUŠČICA 9E, ZAGREB",150, 100, title);
     c.drawText("EVIDENCIJA O KRETANJU PRIVATNOG AUTOMOBILA ZA RAZDOBLJE",150, 160, title);
     c.drawText("OD "+ Dani.get(0).getDatum()+" DO "+ Dani.get(Dani.size()-1).getDatum()+" godine U SLUŽBENE SVRHE",150, 190, title);
     c.drawText("Korisnik: Andreja Stjepanović",150, 250, title);
     c.drawText("Marka automobila: Citroen",150, 280, title);
     c.drawText("Registarski broj automobila: 4864876584",150, 310, title);
     //informacije u desnom kutu
     paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.BOLD));
     paint.setTextSize(30f);
     paint.setTextAlign(Paint.Align.RIGHT);
     c.drawText("Call: 093530953",1160,40,paint);
     c.drawText("Call: 093530953",1160,80,paint);
     //tablica
     title.setStyle(Paint.Style.STROKE);
     title.setStrokeWidth(3);
     c.drawRect(20,400,pageWidth-20, 480, title);
     title.setTextAlign(Paint.Align.LEFT);
     title.setStyle(Paint.Style.FILL);
     paint.setTextAlign(Paint.Align.LEFT);
     paint.setStyle(Paint.Style.FILL);
     c.drawText("Datum",60,450,paint);
     c.drawText("Naziv lokacija",260,450,paint);
     c.drawText("Broj prijeđenih km",570,450,paint);
     c.drawText("Izvješće o radu",890,450,paint);
     c.drawLine(20,400, 20, pageHeight-200,title);
     c.drawLine(180,400, 180, pageHeight-200,title);
     c.drawLine(550,400, 550, pageHeight-200,title);
     c.drawLine(830,400, 830, pageHeight-200,title);
     c.drawLine(pageWidth-20,400, pageWidth-20, pageHeight-200,title);
     //podaci
     paint.setTextSize(25f);
     paint.setTypeface(Typeface.create(Typeface.DEFAULT,Typeface.NORMAL));
     int y=510;
     int pocetak=brojIndexaP;
     int zavrsetak=brojIndexaZ;
     for(int i=pocetak;i<zavrsetak;i++){
         y=y+10;
         String datumDana=Dani.get(i).getDatum();
         String kilometri=Dani.get(i).getKm();
         c.drawText(datumDana,30,y,paint);
         c.drawText(kilometri,580,y,paint);
         System.out.println("veliciina popisa: "+Dani.get(i).getPopis().size());
         if(Dani.get(i).getPopis().size()>3) {
             int red=0;
             String lokacije="";
             String text="";
             for (int j = 0; j < Dani.get(i).getPopis().size(); j++) {
                 if(red<2&&j<Dani.get(i).getPopis().size()-1){
                     lokacije=lokacije+Dani.get(i).getPopis().get(j).getAdresa()+", ";
                     text=text+Dani.get(i).getPopis().get(j).getPrezime()+", ";
                     red++;
                 }
                 else{
                     red=0;
                     lokacije=lokacije+Dani.get(i).getPopis().get(j).getAdresa();
                     text=text+Dani.get(i).getPopis().get(j).getPrezime();
                     if(j!=Dani.get(i).getPopis().size()-1){
                         lokacije=lokacije+", ";
                         text=text+", ";
                     }
                     c.drawText(lokacije, 190, y, paint);
                     c.drawText(text, 840, y, paint);
                     text="";
                     lokacije="";
                     y = y + 35;
                 }
             }
         }
         else{
             String lokacije="";
             String text="";
             for (int j = 0; j < Dani.get(i).getPopis().size(); j++) {
                 lokacije=lokacije+Dani.get(i).getPopis().get(j).getAdresa();
                 text=text+Dani.get(i).getPopis().get(j).getPrezime();
                 if(j!=Dani.get(i).getPopis().size()-1){
                     lokacije=lokacije+", ";
                     text=text+", ";
                 }
             }
             c.drawText(lokacije, 190, y, paint);
             c.drawText(text, 840, y, paint);
             y = y + 35;
         }
         c.drawLine(20,y, pageWidth-20, y,title);
         y=y+20;
     }

     pdf.finishPage(myPage);

 }
}
