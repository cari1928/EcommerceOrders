package mx.edu.itcelaya.ecommerceproducts;

/**
 * Created by niluxer on 5/16/16.
 */
public class Products {
    private int id;
    private String imageUrl;
    private String title;
    private String price;
    private Boolean in_stock;
    private String stock_quantity;
    private String description;

    public Products(int id, String imageUrl, String title, String price, Boolean in_stock, String stock_quantity, String description) {
        this.id = id;
        this.imageUrl = imageUrl;
        this.title = title;
        this.price = price;
        this.in_stock = in_stock;
        this.stock_quantity = stock_quantity;
        this.description = description;
    }

    public Boolean getIn_stock() {
        return in_stock;
    }

    public void setIn_stock(Boolean in_stock) {
        this.in_stock = in_stock;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setStock_quantity(String stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTitle() {
        return title;
    }

    public String getPrice() {
        return price;
    }

    public String getStock_quantity() {
        return stock_quantity;
    }

    public String getDescription() {
        return description;
    }
}
