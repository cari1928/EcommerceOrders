package mx.edu.itcelaya.ecommerceproducts;

import java.util.List;

/**
 * Created by Radogan on 2016-11-26.
 */

public class Orders {
    private int order_number;
    private String created_at;
    private int total_line_items_quantity;
    private String total;
    private String payment_details;
    private String full_name;
    private String email;
    private List<Integer> products_id;

    public Orders(int order_number, String created_at, int total_line_items_quantity, String total, String payment_details, String full_name, String email, List<Integer> products_id) {
        this.order_number = order_number;
        this.created_at = created_at;
        this.total_line_items_quantity = total_line_items_quantity;
        this.total = total;
        this.payment_details = payment_details;
        this.full_name = full_name;
        this.email = email;
        this.products_id = products_id;
    }

    public List<Integer> getProducts_id() {
        return products_id;
    }

    public void setProducts_id(List<Integer> products_id) {
        this.products_id = products_id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public int getOrder_number() {
        return order_number;
    }

    public String getCreated_at() {
        return created_at;
    }

    public int getTotal_line_items_quantity() {
        return total_line_items_quantity;
    }

    public String getTotal() {
        return total;
    }

    public String getPayment_details() {
        return payment_details;
    }

    public String getEmail() {
        return email;
    }

    public void setOrder_number(int order_number) {
        this.order_number = order_number;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setTotal_line_items_quantity(int total_line_items_quantity) {
        this.total_line_items_quantity = total_line_items_quantity;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public void setPayment_details(String payment_details) {
        this.payment_details = payment_details;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
