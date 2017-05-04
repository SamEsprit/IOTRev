package eprit.tn.iot;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {


    TextView Temp,son,Light;
    Button reft,refs,refL,seuilT,seuilS,seuilL,lcdbutton;
    Switch onoffL,onoffb;
    EditText seuilTemp,seuilSon,seuilLight,LCD;
    String urlTemp="http://192.168.77.85/www/IOT/getTemp.php";
    String urlson="http://192.168.77.85/www/IOT/getSon.php";
    String urlLight="http://192.168.77.85/www/IOT/getLight.php";
    String urlLedOn="http://192.168.77.85/www/IOT/checked.php";
    String urlLedOff="http://192.168.77.85/www/IOT/NonChecked.php";
    String urlBuzzOn="http://192.168.77.85/www/IOT/checkedBuzzer.php";
    String urlBuzzOff="http://192.168.77.85/www/IOT/NonCheckedBuzzer.php";
    String urlBuzzState="http://192.168.77.85/www/IOT/getBuzzer.php";
    String urlLedState="http://192.168.77.85/www/IOT/getState.php";

    String UrlSeuilT="http://192.168.77.85/www/IOT/setseuilT.php";
    String UrlSeuilS="http://192.168.77.85/www/IOT/setSeuilS.php";
    String UrlSeuilL="http://192.168.77.85/www/IOT/setSeuilL.php";
    String UrlLCD="http://192.168.77.85/www/IOT/setLcd.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Temp=(TextView)findViewById(R.id.Temp);
        son=(TextView)findViewById(R.id.son);
        Light=(TextView)findViewById(R.id.Light);

        getValues(urlLight,Light);
        getValues(urlson,son);
        getValues(urlTemp,Temp);

        reft=(Button)findViewById(R.id.reft);
        refs=(Button)findViewById(R.id.refs);
        refL=(Button)findViewById(R.id.refL);
        seuilT=(Button)findViewById(R.id.seuilT);
        seuilS=(Button)findViewById(R.id.seuilS);
        seuilL=(Button)findViewById(R.id.seuilL);

        lcdbutton=(Button)findViewById(R.id.lcdbutton);

        reft.setText("Sync");
        refs.setText("Sync");
        refL.setText("Sync");


        reft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValues(urlTemp,Temp);
            }
        });
        refs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValues(urlson,son);
            }
        });
        refL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getValues(urlLight,Light);
            }
        });
        onoffL=(Switch)findViewById(R.id.onoffL);
        onoffb=(Switch)findViewById(R.id.onoffb);

        onoffL.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    setValues(urlLedOn);
                else setValues(urlLedOff);
            }
        });
        onoffb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    setValues(urlBuzzOn);
                else setValues(urlBuzzOff);
            }
        });

        seuilTemp=(EditText)findViewById(R.id.seuilTemp);
        seuilSon=(EditText)findViewById(R.id.seuilSon);
        seuilLight=(EditText)findViewById(R.id.seuilLight);
        LCD=(EditText) findViewById(R.id.lcd);
        seuilT.setText("valide");
        seuilT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( seuilLight.getText().equals("")){
                    Toast.makeText(getApplicationContext(),"verifier votre champs",Toast.LENGTH_SHORT).show();
               }
                else
                {
                    setSeuilT();
                    seuilTemp.setText("");

                }
            }
        });
        seuilS.setText("valide");
        seuilS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( seuilSon.getText().equals("")){
                    Toast.makeText(getApplicationContext(),"verifier votre champs",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    setSeuilS();
                    seuilSon.setText("");

                }
            }
        });
        seuilL.setText("valide");
        seuilL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( seuilLight.getText().equals("")){

                    Toast.makeText(getApplicationContext(),"verifier votre champs",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    setSeuilL();
                    seuilLight.setText("");
                }
            }
        });
        lcdbutton.setText("valide");
        lcdbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( LCD.getText().equals("")){
                    Toast.makeText(getApplicationContext(),"verifier votre champs",Toast.LENGTH_SHORT).show();
               }
                else
                {
                    setLcd();
                    LCD.setText("");

                }
            }
        });

        getBuzzerState();
        getLedState();
    }




    public void getValues(String URL,final TextView textView) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        textView.setText(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("erreur",error.getLocalizedMessage());
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
    public void setValues(String URL) {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("erreur",error.getLocalizedMessage());
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);


    }
    public void getLedState() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlLedState,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.ICE_CREAM_SANDWICH)
                    @Override
                    public void onResponse(String response) {
                        if(Integer.parseInt(response)==0)
                            onoffL.setChecked(false);
                        else
                            onoffL.setChecked(true);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("erreur",error.getLocalizedMessage());
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
    public void getBuzzerState() {
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, urlBuzzState,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        if(Integer.parseInt(response)==0)
                            onoffb.setChecked(false);
                        else
                            onoffb.setChecked(true);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("erreur",error.getLocalizedMessage());
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public void setSeuilT(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlSeuilT+"?temp="+seuilTemp.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("erreur",error.getLocalizedMessage());
            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public void setSeuilL(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlSeuilL+"?light="+seuilLight.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public void setSeuilS(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlSeuilS+"?son="+seuilSon.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
    public void setLcd(){
        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlLCD+"?lcd="+LCD.getText().toString(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }
}
