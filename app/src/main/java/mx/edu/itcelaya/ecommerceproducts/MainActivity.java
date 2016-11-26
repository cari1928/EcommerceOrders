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
    List<Products> p_items = new ArrayList<Products>();
    Spinner sp_status;
    String jsonResult;
    String statusSelected;
    String consumer_key = "ck_1e92f3593393b4b67a9c36b4cc3fa39cec0494fa";
    String consumer_secret = "cs_9acec12116917aaa12187e38cde674e3f1b62057";
    String url = "https://192.168.1.64/store_itc/wc-api/v3/orders";
    AlertDialog dialogFoto;
    Button btnRegresa;

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
        if(view == btnRegresa) {
            dialogFoto.dismiss();
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        TextView myText = (TextView) view;
        Toast.makeText(this, "You selected " + myText.getText().toString().toLowerCase(), Toast.LENGTH_SHORT).show();

        if(position != 0) {
            statusSelected = myText.getText().toString().toLowerCase();
            initListOrders();
            NukeSSLCerts.nuke();
            loadOrders();
        }
    }

    public void initListOrders(){
        listOrders.setAdapter(null);
        items = new ArrayList<Orders>();
        listOrders.setOnItemClickListener(listenerOrder); //crear un listener que responda a cada elemento del list view
    }

    AdapterView.OnItemClickListener listenerOrder = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            List<Integer> products_id;
            p_items = new ArrayList<Products>();
            Orders selected_order = items.get(position); //posición
            //Toast.makeText(getBaseContext(), "Orden: " + selected_order.getOrder_number(), Toast.LENGTH_LONG).show();

            products_id = selected_order.getProducts_id();
            for(int i = 0; i < products_id.size(); i++) {
                Integer product_id = products_id.get(i); //esto debe modificarse
                //Toast.makeText(getBaseContext(), "ID Producto: " + products_id.get(i), Toast.LENGTH_LONG).show();
                loadProducts(product_id);
            }

            setAdapter();
        }
    };

    private void setAdapter(){
        //para mostrar los productos
        AlertDialog.Builder builder = new AlertDialog.Builder(this); //recibe el contexto de la app
        LinearLayout layout1 = new LinearLayout(this); //para colocar en él los elementos
        layout1.setOrientation(LinearLayout.VERTICAL);

        //nuevo listview en conjunto con un arrayadapter
        ListView vProducts = new ListView(this);
        vProducts.setAdapter(new ProductsAdapter(this, p_items));

        //boton
        btnRegresa = new Button(this);
        btnRegresa.setText("Cerrar");
        btnRegresa.setOnClickListener(this);

        //se pasan los elementos al layout
        layout1.addView(vProducts);
        layout1.addView(btnRegresa);

        builder.setView(layout1); //se le pasa el layout a builder
        dialogFoto = builder.create(); //se termina de crear el dialogo
        dialogFoto.show(); //se muestra el dialogo
    }

    private void loadProducts(int product_id) {
        url = "https://192.168.1.64/store_itc/wc-api/v3/products/" + product_id;
        LoadProductsTask tarea = new LoadProductsTask(this, consumer_key, consumer_secret);

        try {
            jsonResult = tarea.execute(new String[] { url }).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Toast.makeText(getBaseContext(), jsonResult, Toast.LENGTH_LONG).show();
        ListProducts(); //descomentar!!
    }

    public void ListProducts(){
        try {
            //se obtiene el apartado product
            JSONObject jsonResponse = new JSONObject(jsonResult);
            String jsonProduct = jsonResponse.optString("product");

            //ya se pueden obtener sus elementos
            JSONObject jsonResponseProduct = new JSONObject(jsonProduct);
            Integer id = jsonResponseProduct.optInt("id");
            String ImageURL = jsonResponseProduct.optString("featured_src");
            String title = jsonResponseProduct.optString("title");
            String price = jsonResponseProduct.optString("price");
            String description = jsonResponseProduct.optString("description");
            Boolean in_stock = jsonResponseProduct.optBoolean("in_stock");
            String stock_quantity = "0";

            if(in_stock) {
                if(stock_quantity != null) {
                    stock_quantity = jsonResponseProduct.optString("stock_quantity");
                } else {
                    stock_quantity = "0";
                }
            }

            p_items.add(new Products(id, ImageURL, title, price, in_stock, stock_quantity, description));

        } catch (JSONException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "Error" + e.toString(), Toast.LENGTH_LONG).show();

        }
    }

    private void loadOrders() {
        url = "https://192.168.1.64/store_itc/wc-api/v3/orders";
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
                List<Integer> products_id = new ArrayList<Integer>();
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);

                String jsonStatus = jsonChildNode.optString("status");

                //Toast.makeText(getApplicationContext(), "JSONStatus: " + jsonStatus, Toast.LENGTH_LONG).show();
                if(jsonStatus.equals(statusSelected)) {
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

                    String jsonProduct = jsonChildNode.optString("line_items");
                    JSONArray jsonMainNode2 = jsonChildNode.optJSONArray("line_items");
                    for(int j = 0; j < jsonMainNode2.length(); j++) {
                        JSONObject p = jsonMainNode2.getJSONObject(j);
                        Integer product_id = p.optInt("product_id");
                        //Toast.makeText(getApplicationContext(), "Productos: " + p.optInt("product_id"), Toast.LENGTH_LONG).show();
                        products_id.add(product_id);
                    }

                    items.add(new Orders(order_number, created_at, total_line_items_quantity, total, payment_details, nombre, email, products_id));
                }
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
