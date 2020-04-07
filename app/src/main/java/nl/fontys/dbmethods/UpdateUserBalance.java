package nl.fontys.dbmethods;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import nl.fontys.bikehub.LogInPage;
import nl.fontys.bikehub.R;

public class UpdateUserBalance extends AsyncTask<String, Void, Void> {

    private Activity activity;
    private int userID;
    private double addOrSubtractBalance;

    public UpdateUserBalance(Activity activity, int userID, double addOrSubtractBalance) {
        this.activity = activity;
        this.userID = userID;
        this.addOrSubtractBalance = addOrSubtractBalance;

    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(String... strings) {
        try {
                // GET CURRENT BALANCE
            String link = "https://hubbikeappfontys.eu-gb.mybluemix.net/api/Balanceusers/" + userID; // + not id should be later username

            URL url = new URL(link);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.connect();
            InputStream is = conn.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

            StringBuilder jsonString = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonString.append(line);
            }

            JSONObject object = new JSONObject(jsonString.toString());
            final double currentBalance = object.getInt("currentbalance");
            final int userid            = object.getInt("userid");
            final int subsdaysleft       = object.getInt("subsdaysleft");
            final int id                = object.getInt("id");
            final int responseCode = conn.getResponseCode();                                        // GET RESPONSE CODE
            System.out.println("GET USER BALANCE RESPONSE CODE: " + responseCode);
            conn.disconnect();

            if(responseCode == 200){
                HttpURLConnection conn2 = (HttpURLConnection) url.openConnection();
                conn2.setRequestMethod("PUT");
                conn2.setDoOutput(true);
                conn2.setRequestProperty("Content-Type", "application/json");
                conn2.setRequestProperty("Accept","application/json");
                conn2.setRequestProperty("X-Device-Token", LogInPage.FirebaseDeviceToken); // MIRJAN
                if(addOrSubtractBalance > 0){
                    conn2.setRequestProperty("X-Message-Body", String.valueOf(addOrSubtractBalance) + "€ added to your account.") ; // MIRJAN
                }
                else{
                    conn2.setRequestProperty("X-Message-Body", String.valueOf(addOrSubtractBalance) + "€ subtracted from your account.") ; // MIRJAN
                }
                conn2.connect();

                final StringBuilder jsonBuildString = new StringBuilder();
                jsonBuildString.append("{")
                        .append("\"userid\":\"").append(userid).append("\",")
                        .append("\"currentbalance\":\"").append(currentBalance + addOrSubtractBalance).append("\",")
                        .append("\"subsdaysleft\":\"").append(subsdaysleft).append("\",")
                        .append("\"id\":\"").append(id) //doesnt exist in db anymore, remove all methodes
                        .append("\"}");

                DataOutputStream dos = new DataOutputStream(conn2.getOutputStream());
                dos.writeBytes(jsonBuildString.toString());
                dos.flush();
                dos.close();

                final int responseCodePush = conn2.getResponseCode();
                System.out.println("Updated User Response Code: " + responseCodePush);
                conn2.disconnect();
            }
            else{
                System.out.println("Transaction Failed! " );

            }



        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (NumberFormatException | IOException e) {
            e.printStackTrace();
           // final TextView text = (TextView) activity.findViewById(R.id.textView);
            // Thread is a functional interface, thus next call is valid as well
            //activity.runOnUiThread(new Thread() {
             //   @Override
             //   public void run() {
              //      text.setText("Transaction failed!");
             //   }
          //  });
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


