package nl.fontys.dbmethods;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import nl.fontys.bikehub.LogInPage;
import nl.fontys.bikehub.R;

public class PostMethode extends AsyncTask<String, Void, Void> {

    private Activity activity;
    private AppUser appUser;

    public PostMethode(Activity activity, AppUser appUser) {
        this.activity = activity;
        this.appUser = appUser;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
            String link = "https://hubbikeappfontys.eu-gb.mybluemix.net/api/Bikeusers";

            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection)url.openConnection();
            // by default the request is a GET request
            // hence we have to configure a POST request
            // POST should be used for creation, because posting the same object again
            // will result in a 500 response, which basically means an error
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Accept","application/json");
            conn.setRequestProperty("X-Device-Token", LogInPage.FirebaseDeviceToken); // MIRJAN
            conn.setRequestProperty("X-Message-Body", "Congratulations " + appUser.getPostfirstName() + " " +  appUser.getPostlastName() + ", successfully registered :)."); // MIRJAN
            conn.connect();
            System.out.println("IN POST METHOD" + LogInPage.FirebaseDeviceToken);
            System.out.println(appUser.getPostfirstName());

            // use a StringBuilder to build the json string
            final StringBuilder jsonBuildString = new StringBuilder();
            jsonBuildString.append("{")
                    .append("\"firstname\":\"").append(appUser.getPostfirstName()).append("\",")
                    .append("\"lastname\":\"").append(appUser.getPostlastName()).append("\",")
                    .append("\"username\":\"").append(appUser.getPostusername()).append("\",")
                    .append("\"password\":\"").append(appUser.getPostpassword()).append("\",")
                    .append("\"email\":\"").append(appUser.getPostemail()).append("\",")
                    .append("\"birthday\":\"").append(appUser.getPostbirthday()).append("\",")
                    .append("\"address\":\"").append(appUser.getPostaddress()).append("\",")
                    .append("\"gender\":\"").append(appUser.getPostgender())
                    .append("\"}");

            DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(jsonBuildString.toString());
            dos.flush();
            dos.close();

            final int responseCode = conn.getResponseCode();
            System.out.println(responseCode);
            // returns 200 in case the post was successful i.e. an object with the same id
            // not yet exists, otherwise 500 in that case an object with the same id exists.


           /*

           //that was a check to test the post methode
            final TextView text = (TextView) activity.findViewById(R.id.tvpostresults);

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    text.setText("Post executed: " + responseCode + jsonBuildString.toString());
                }
            });

            */

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
