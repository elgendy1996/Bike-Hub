package nl.fontys.dbmethods;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import nl.fontys.bikehub.LogInPage;
import nl.fontys.bikehub.R;

public class GetMethode extends AsyncTask<String, Void, Void> {

    private Activity activity;
    private String username;
    private String password;
    public static int chillcounter = 0;


    public static boolean loginAllowed = false;

    public GetMethode(Activity activity, String username, String password) {
        this.activity = activity;
        this.username = username;
        this.password = password;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {

            final String link = "https://hubbikeappfontys.eu-gb.mybluemix.net/api/bikeusers?filter=%7B%22where%22%3A%7B%22username%22%3A%22" + username + "%22%2C%22password%22%3A%22" + password + "%22%7D%7D";


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
            //final int objectId = object.getInt("id");
            final Object username = object.get(0);
            //final String username = object.getString("username");
            //final String password = object.getString("password");


            setLoginAllowed();
            chillcounter++;



            // final TextView text = (TextView) activity.findViewById(R.id.tvInfo);



            // final TextView text = (TextView) activity.findViewById(R.id.tvInfo);

            conn.disconnect();



        } catch (MalformedURLException e) {
            e.printStackTrace();

        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
            final TextView text = (TextView) activity.findViewById(R.id.tvInfo);
            // Thread is a functional interface, thus next call is valid as well
           /* activity.runOnUiThread(new Thread() {
                @Override
                public void run() {
                  //  text.setText("not present");
                }

            });
            */
        } catch (JSONException e) {
            e.printStackTrace();
            final TextView text = (TextView) activity.findViewById(R.id.tvInfo);
            /*
            activity.runOnUiThread(new Thread() {
                @Override
                public void run() {
                //    text.setText("invalid user");

                }
            });
            */
        }


        return null;

    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(Void aVoid) {



}

    public static void setLoginAllowed(){
        loginAllowed = true;
    }

    public static boolean loginAllowed() {
        return loginAllowed;
    }

    public static void resetLogin() { loginAllowed = false; }

    public static int getChillcounter() {
        return chillcounter;
    }

}
