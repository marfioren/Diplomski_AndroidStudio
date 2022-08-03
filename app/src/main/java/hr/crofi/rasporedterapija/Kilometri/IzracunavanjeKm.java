package hr.crofi.rasporedterapija.Kilometri;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
public class IzracunavanjeKm extends AsyncTask {

    String pLat, Plon, zLat, Zlon;

    public IzracunavanjeKm() {

    }

    protected void onPreExecute(){
    }
    @Override
    protected String doInBackground(Object[] objects) {
        this.pLat=objects[0].toString();
        this.Plon=objects[1].toString();
        this.zLat=objects[2].toString();
        this.Zlon=objects[3].toString();
        String link="https://maps.googleapis.com/maps/api/distancematrix/json?destinations="+zLat+"%2C"+Zlon+"&origins="+pLat+"%2C"+Plon+"&key=AIzaSyCiLTKcVOoWFXqSe-TpI0jyUty7dG4zFJw";
        String rez="";
        try {
            URL url=new URL(link);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.connect();
            int statuscode=con.getResponseCode();
            if(statuscode==HttpURLConnection.HTTP_OK)
            {
                BufferedReader br=new BufferedReader(new InputStreamReader(con.getInputStream()));
                StringBuilder sb=new StringBuilder();
                String line=br.readLine();
                while(line!=null)
                {
                    sb.append(line);
                    line=br.readLine();
                }
                String json=sb.toString();
                JSONObject root=new JSONObject(json);
                JSONArray array_rows=root.getJSONArray("rows");
                JSONObject object_rows=array_rows.getJSONObject(0);
                JSONArray array_elements=object_rows.getJSONArray("elements");
                JSONObject  object_elements=array_elements.getJSONObject(0);
                JSONObject object_distance=object_elements.getJSONObject("distance");
                rez= object_distance.getString("text");

            }
            } catch (MalformedURLException e) {
                rez="er1";
            } catch (IOException e) {
                rez="er1";
            } catch (JSONException e) {
                rez="er1";
            }

        return rez;
    }
    @Override
    protected void onPostExecute(Object o) {

    }

}
