package nl.fontys.dbmethods;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class PutBikeMethod extends AsyncTask<String, Void, Void> {
    private Activity activity;
    private Bike bike;

    public PutBikeMethod(Activity activity, Bike bike) {
        this.activity = activity;
        this.bike = bike;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... strings) {
        try{
            String link = "http://bikehubappfontys.eu-gb.mybluemix.net/api/Bikes";

            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            conn.setRequestMethod("PUT");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept","application/json");
            conn.connect();

            final StringBuilder jsonBuildString = new StringBuilder();
            jsonBuildString.append("{")
                    .append("\"barcode\":").append(bike.getBarCode()).append(",")
                    .append("\"lastlocklat\":").append(bike.getLastLockLat()).append(",")
                    .append("\"lastlocklng\":").append(bike.getCurrentLockLng()).append(",")
                    .append("\"currentlocklat\":").append(bike.getCurrentLockLat()).append(",")
                    .append("\"currentlocklng\":").append(bike.getCurrentLockLng()).append(",")
                    .append("\"lockstatus\":").append(bike.getLockStatus()).append(",")
                    .append("\"storagehub\":").append(bike.getHub()).append(",")
                    .append("\"id\":").append(bike.getId())
                    .append("}");

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(jsonBuildString.toString());
            dos.flush();
            dos.close();

            final int code = conn.getResponseCode();
            System.out.println("Responce Code: "+code);
            conn.disconnect();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
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
