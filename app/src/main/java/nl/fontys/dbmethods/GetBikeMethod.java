package nl.fontys.dbmethods;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import nl.fontys.bikehub.BikeInputPage;
import nl.fontys.bikehub.QRScanPage;
import nl.fontys.bikehub.R;


public class GetBikeMethod extends AsyncTask<String, Void, Void> {
    private Activity activity;
    private int bikeBarCode;
    BikeInputPage bip = null;
    QRScanPage qr = null;
    int origin;

    //if origin = 1, get method is called from BikeInputPage, if origin = 0, method is called from QRScanPage
    public GetBikeMethod(Activity activity, int bikeBarCode, BikeInputPage bip,int origin) {
        this.activity = activity;
        this.bikeBarCode = bikeBarCode;
        this.bip = bip;
        this.origin = origin;
    }

    public GetBikeMethod(Activity activity, int bikeBarCode, QRScanPage qr, int origin) {
        this.activity = activity;
        this.bikeBarCode = bikeBarCode;
        this.qr = qr;
        this.origin = origin;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... strings) {

        try{
            final String link = "http://bikehubappfontys.eu-gb.mybluemix.net/api/Bikes?filter=%7B%22where%22%3A%7B%22barcode%22%3A%22"+bikeBarCode+"%22%7D%7D";

            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // by default the request is a GET request
            conn.connect();

            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONArray object = new JSONArray(jsonString.toString());

            final JSONObject jBike = (JSONObject)object.get(0);
            int id = jBike.getInt("id");
            int barcode = jBike.getInt("barcode");
            int lastlocklat = jBike.getInt("lastlocklat");
            int lastlocklng = jBike.getInt("lastlocklng");
            int currentlocklat = jBike.getInt("currentlocklat");
            int currentlocklng = jBike.getInt("currentlocklng");
            boolean lockstatus = jBike.getBoolean("lockstatus");
            int storagehub = jBike.getInt("storagehub");

            Bike bike = new Bike(id, barcode, lastlocklat, lastlocklng,
                    currentlocklat, currentlocklng, lockstatus, storagehub);

            if (origin == 0){
                bip.checkBike(bike);
            }else if (origin == 1){

            }



            conn.disconnect();


        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
            //final TextView text = (TextView) activity.findViewById(R.id.textView);
            // Thread is a functional interface, thus next call is valid as well
            activity.runOnUiThread(new Thread() {
                @Override
                public void run() {
                    //text.setText("not present");
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
