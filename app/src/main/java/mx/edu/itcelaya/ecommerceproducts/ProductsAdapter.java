package mx.edu.itcelaya.ecommerceproducts;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by niluxer on 5/16/16.
 */
public class ProductsAdapter extends BaseAdapter {
    private Context context;
    private List<Products> productos;
    ImageView img1;

    public ProductsAdapter(Context context, List<Products> productos) {
        super();
        this.context = context;
        this.productos = productos;
    }

    @Override
    public int getCount() {
        return this.productos.size();
    }

    @Override
    public Object getItem(int position) {
        return this.productos.get(position);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        View rowView = view;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.list_products, null);
        }

        TextView tvTitle   = (TextView) rowView.findViewById(R.id.tvTitle);
        TextView tvPrice  = (TextView) rowView.findViewById(R.id.tvPrice);
        TextView tvStockQuantity  = (TextView) rowView.findViewById(R.id.tvStockQuantity);
        TextView tvDescription  = (TextView) rowView.findViewById(R.id.tvDescription);
        img1 = (ImageView) rowView.findViewById(R.id.imgIdProduct);

        final Products item = this.productos.get(position);
        tvTitle.setText(item.getTitle());
        tvPrice.setText(item.getPrice());
        tvStockQuantity.setText(item.getStock_quantity());
        tvDescription.setText(item.getDescription());
        rowView.setTag(item.getId());
        String sUrl = item.getImageUrl();
        //String sUrl = "http://gravatar.com/avatar/1c57c8eea18ec3bbf43b81432e61132f";

        try {
            final Bitmap bitmap = new BackgroundTask().execute(sUrl).get();
            img1.setImageBitmap(bitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return rowView;
    }

    private InputStream OpenHttpConnection(String urlString)
            throws IOException
    {
        InputStream in = null;
        int response = -1;
        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();
        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");
        try{
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setRequestMethod("GET");

            httpConn.connect();
            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        }
        catch (Exception ex) {
            throw new IOException("Error connecting" + response + ex.getMessage());
        }
        return in;
    }

    private Bitmap DownloadImage(String URL)     {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = OpenHttpConnection(URL);
            bitmap = BitmapFactory.decodeStream(in);
            in.close();
        } catch (IOException e1) {
            //Toast.makeText(context, e1.getMessage(), Toast.LENGTH_LONG).show();
            e1.printStackTrace();
        }
        return bitmap;
    }

    private class BackgroundTask extends AsyncTask<String, Void, Bitmap> {
        protected Bitmap doInBackground(String... url) {
            //---download an image---
            Bitmap bitmap = DownloadImage(url[0]);
            return bitmap;
        }
    }

}
