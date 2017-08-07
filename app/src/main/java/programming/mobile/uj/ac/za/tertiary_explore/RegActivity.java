package programming.mobile.uj.ac.za.tertiary_explore;

import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.util.Collections;

public class RegActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private String name ="";
    private String surname = "";
    private String email ="";
    private String password = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /**
         * Input handling for taking in the required information
         *
         */
        TextView txtName = (TextView)findViewById(R.id.txtRegName);
        TextView txtSurname = (TextView)findViewById(R.id.txtRegSurname);
        TextView txtEmail = (TextView)findViewById(R.id.txtRegEmail);
        TextView txtPassword = (TextView)findViewById(R.id.txtRegPassword);

        txtName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(RegActivity.this);
                alert.setTitle("Name");
                final EditText txtIn = new EditText(RegActivity.this);
                //txtIn = new EditText(getApplicationContext());
                txtIn.setInputType(InputType.TYPE_CLASS_TEXT);
                txtIn.setHint("Name...");
                alert.setView(txtIn);
                EditText finalTxtIn = txtIn;
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(txtIn.getText().toString()!=""){
                            String input = txtIn.getText().toString();
                            name = input;
                        }
                    }
                });
                alert.show();
            }
        });


        txtSurname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(RegActivity.this);
                alert.setTitle("Surname");
                final EditText txtIn = new EditText(RegActivity.this);
                //txtIn = new EditText(getApplicationContext());
                txtIn.setInputType(InputType.TYPE_CLASS_TEXT);
                txtIn.setHint("Surname...");
                alert.setView(txtIn);
                EditText finalTxtIn = txtIn;
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(txtIn.getText().toString()!=""){
                            String input = txtIn.getText().toString();
                            surname = input;
                        }
                    }
                });
                alert.show();
            }
        });


        txtEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(RegActivity.this);
                alert.setTitle("Email");
                final EditText txtIn = new EditText(RegActivity.this);
                //txtIn = new EditText(getApplicationContext());
                txtIn.setInputType(InputType.TYPE_CLASS_TEXT);
                txtIn.setHint("Email...");
                alert.setView(txtIn);
                EditText finalTxtIn = txtIn;
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(txtIn.getText().toString()!=""){
                            String input = txtIn.getText().toString();
                            email = input;
                        }
                    }
                });
                alert.show();
            }
        });


        txtPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alert = new AlertDialog.Builder(RegActivity.this);
                alert.setTitle("Password");
                final EditText txtIn = new EditText(RegActivity.this);
                //txtIn = new EditText(getApplicationContext());
                txtIn.setInputType(InputType.TYPE_CLASS_TEXT);
                txtIn.setHint("Password...");
                alert.setView(txtIn);
                EditText finalTxtIn = txtIn;
                alert.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(txtIn.getText().toString()!=""){
                            String input = txtIn.getText().toString();
                            password = input;
                        }
                    }
                });
                alert.show();
            }
        });

        Button btnButton = (Button)findViewById(R.id.btnReg);
        btnButton.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 //calls the endpoint to register a user to the system
                 new USSD().execute("http://mmabathosmartasp-001-site1.dtempurl.com/api/Default/userReg?username=Johnny&password="+ password+"&firstname="+name+"&lastname=" +surname+"&email="+ email);
                 Toast.makeText(getApplicationContext(),"email captured" + USSD.getLine(), Toast.LENGTH_SHORT ).show();
                // USSD.getLine()
             }
         }
        );







        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.reg, menu);
        return true;
    }
    //function that is used to receive data via endpoints


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
