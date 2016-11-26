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

    ListView listOrders;
    List<Orders> items = new ArrayList<Orders>();
    Spinner sp_status;
    String jsonResult;
    String statusSelected;
    String consumer_key = "ck_1e92f3593393b4b67a9c36b4cc3fa39cec0494fa";
    String consumer_secret = "cs_9acec12116917aaa12187e38cde674e3f1b62057";
    String url = "https://192.168.1.64/store_itc/wc-api/v3/orders";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //listview
        listOrders = (ListView) findViewById(R.id.listOrders);

        //combo
        createSpinner();

        //NukeSSLCerts.nuke(); //irá aquí? o en itemSelectedListener?
    }

    public void createSpinner(){
        statusSelected = "";
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
                Toast.makeText(this, "You selected " + myText.getText() + " Posicion: " + position, Toast.LENGTH_SHORT).show();

        if(position != 0) {
            statusSelected = myText.getText() + "";

            //listStatus.setOnItemClickListener(listenerProduct); //crear un listener que responda a cada elemento del list view

            NukeSSLCerts.nuke();

            loadOrders();
        }
    }

    private void loadOrders() {
        LoadProductsTask tarea = new LoadProductsTask(this, consumer_key, consumer_secret);
        try {
            jsonResult = tarea.execute(new String[] { url }).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        //Toast.makeText(getBaseContext(), jsonResult, Toast.LENGTH_LONG).show();
        ListOrders();
    }

    public void ListOrders() {

        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("orders");

            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                Integer order_number = jsonChildNode.optInt("order_number");
                String created_at = jsonChildNode.optString("created_at");
                Integer total_line_items_quantity = jsonChildNode.optInt("total_line_items_quantity");
                String total = jsonChildNode.optString("total");

                String jsonPayment = jsonChildNode.optString("payment_details");
                JSONObject jsonResponsePayment = new JSONObject(jsonPayment);
                String payment_details = jsonResponsePayment.optString("method_title"); //obtenido!!!

                String jsonPersonal = jsonChildNode.optString("billing_address");
                JSONObject jsonResponsePersonal = new JSONObject(jsonPersonal);
                String first_name = jsonResponsePersonal.optString("first_name");
                String last_name = jsonResponsePersonal.optString("last_name");
                String email = jsonResponsePersonal.optString("email");
                String nombre = first_name + " " + last_name;

                items.add(new Orders(order_number, created_at, total_line_items_quantity, total, payment_details, nombre, email));
                //Toast.makeText(getApplicationContext(), "Nombre: " + nombre, Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(), Toast.LENGTH_LONG).show();

        }

        listOrders.setAdapter(new OrdersAdapter(this, items));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public static class NukeSSLCerts {
        protected static final String TAG = "NukeSSLCerts";

        public static void nuke() {
            try {
                TrustManager[] trustAllCerts = new TrustManager[] {
                        new X509TrustManager() {
                            public X509Certificate[] getAcceptedIssuers() {
                                X509Certificate[] myTrustedAnchors = new X509Certificate[0];
                                return myTrustedAnchors;
                            }

                            @Override
                            public void checkClientTrusted(X509Certificate[] certs, String authType) {}

                            @Override
                            public void checkServerTrusted(X509Certificate[] certs, String authType) {}
                        }
                };

                SSLContext sc = SSLContext.getInstance("SSL");
                sc.init(null, trustAllCerts, new SecureRandom());
                HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
                    @Override
                    public boolean verify(String arg0, SSLSession arg1) {
                        return true;
                    }
                });
            } catch (Exception e) {
            }
        }
    }
}
