package mx.edu.itcelaya.ecommerceproducts;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener{

    Spinner sp_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //combo
        sp_status = (Spinner) findViewById(R.id.status);
        ArrayAdapter adapter_sp = ArrayAdapter.createFromResource(this, R.array.status, android.R.layout.simple_spinner_item);
        sp_status.setAdapter(adapter_sp);
        sp_status.setOnItemSelectedListener(this);
    }


    @Override
    public void onClick(View view) {

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
            TextView myText = (TextView) view;
                Toast.makeText(this, "You selected " + myText.getText() + " Posicion: " + posi, Toast.LENGTH_SHORT).show();


        if()
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
