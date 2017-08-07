package programming.mobile.uj.ac.za.tertiary_explore;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Registration and Login
        TextView txtReg = (TextView)findViewById(R.id.txtReg);
        txtReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            // redirect the user to the registration page
            startActivity(new Intent(getApplicationContext(),RegActivity.class));


            }
        });

        TextView txtLogin = (TextView)findViewById(R.id.txtlogin);
        txtLogin.setOnClickListener(new View.OnClickListener() {
            String email = "";
            String  password = "";
            @Override
            public void onClick(View v) {
                AlertDialog.Builder LoginDialog = new AlertDialog.Builder(MainActivity.this);
                final EditText txtEmailLogin = new EditText(getApplicationContext());
                final EditText txtPasswordLogin = new EditText(getApplicationContext());
                txtEmailLogin.setInputType(InputType.TYPE_CLASS_TEXT);
                LoginDialog.setTitle("Login");
                txtEmailLogin.setHint("Email...");
                LoginDialog.setView(txtEmailLogin);
                LoginDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(txtEmailLogin.getText().toString() != ""){
                            email = txtEmailLogin.getText().toString();
                            Toast.makeText(getApplicationContext(),"email captured", Toast.LENGTH_SHORT ).show();
                        }
                        //now take in the password
                        final AlertDialog.Builder passwrd = new AlertDialog.Builder(MainActivity.this);
                        passwrd.setTitle("Password");
                        txtPasswordLogin.setInputType(InputType.TYPE_CLASS_TEXT);
                        txtPasswordLogin.setHint("Password...");
                        passwrd.setView(txtPasswordLogin);
                        passwrd.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(txtPasswordLogin.getText().toString() != ""){
                                    password = txtPasswordLogin.getText().toString();
                                    new USSD().execute("http://mmabathosmartasp-001-site1.dtempurl.com/api/Default/userLogin?email="+ email+"&password="+passwrd);
                                    //Toast.makeText(getApplicationContext(),"password captured" + USSD.getLine(), Toast.LENGTH_SHORT ).show();
                                    if(USSD.getLine() != " "){
//                                        startActivity(new Intent(getApplicationContext(),welcome.class));
                                    }else{
                                        Toast.makeText(getApplicationContext(),"Invalid details provided, please try again" + USSD.getLine(), Toast.LENGTH_SHORT ).show();
                                    }

                                }
                                startActivity(new Intent(getApplicationContext(),welcome.class));
                            }
                        });
                        passwrd.show();
                    }
                });
                LoginDialog.show();
            }
        });
    }
}



class USSD extends AsyncTask<String, String, String> {
    //global objects and variables
    //private static StartSimulation startSimulation = new StartSimulation();
    //private static final Logger LOGGER = Logger.getLogger(StartSimulation.class.getName());
    private static HttpURLConnection connection = null;
    private static URL url = null;
    private static BufferedReader reader = null;
    private static String line = "";
    private static StringBuilder stringBuilder = new StringBuilder();

    public static String getLine() {
        return line;
    }

    private static void setLine(String newLineValue) {
        line = newLineValue;
    }

    /**
     * Runs asynchrouns background tasks whenever the excute method is call and passes the responses
     * to the onPostExecute method
     *
     * @param params variable length parameter list , allows you pass as many arguments as you want
     * @return returns the  correctly formatted responses , appended to a string builder since strings
     * are immutable
     */
    @Override
    protected String doInBackground(String... params) {

        return  readResponse(params[0]);
    }

    public static String readResponse(String params) {
        stringBuilder = new StringBuilder();
        try {
            /* get the first value in the variable parameter list , this is the url that is passed
               whenever the user makes a request based on the request value
             */
            url = new URL(params);
            //open the connection that the url connects to
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            /**
             * This is what will be presented to the user as long as the refresh button is not pressed
             *
             */
            //line , response from the server will be stored here
            while ((line = reader.readLine()) != null) {
                // the split function is used to break up the response and only take the useful data from it
                String[] incomingData = line.split("ussdstring");
                String[] result = incomingData[1].split("&#10");
                for (String index : result) {
                            /* once the excess data has been cut off , append each line to the string builder having replaced
                               all the unwanted characters
                             */
                    stringBuilder.append(index.replace("amp", "").replace(";", "").replace("\"", "").replace("/>", "").replace("=", "") + "\n\n");
                }
            }
        } catch (IOException e) {
            //  LOGGER.log(Level.SEVERE, e.toString(), e);
            e.printStackTrace();
        }
        connection.disconnect();
        return stringBuilder.toString();
    }
    /**
     * This method is called after the doInBackground has executed.
     *
     * @param result the argument passed by the doInBackground method , the result from having
     *               made a connection to an end point
     */
    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        //StartSimulation.getStartUpDialog().dismiss();
        setLine(result);
    }
}