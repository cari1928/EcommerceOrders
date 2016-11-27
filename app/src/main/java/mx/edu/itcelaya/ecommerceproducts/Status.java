package mx.edu.itcelaya.ecommerceproducts;

/**
 * Created by Radogan on 2016-11-26.
 */

public class Status {
    private String pending;
    private String processing;
    private String on_hold;
    private String completed;
    private String cancelled;
    private String refunded;
    private String failed;
    private String[] status;

    public Status(String pending, String processing, String on_hold, String completed, String cancelled, String refunded, String failed) {
        this.pending = pending;
        this.processing = processing;
        this.on_hold = on_hold;
        this.completed = completed;
        this.cancelled = cancelled;
        this.refunded = refunded;
        this.failed = failed;

        //initStatus();
    }

    public void initStatus(){
        status = new String[7];
        status[0] = this.pending;
        status[1] = this.processing;
        status[2] = this.on_hold;
        status[3] = this.completed;
        status[4] = this.cancelled;
        status[5] = this.refunded;
        status[6] = this.failed;
    }

    public void setPending(String pending) {
        this.pending = pending;
    }

    public void setProcessing(String processing) {
        this.processing = processing;
    }

    public void setOn_hold(String on_hold) {
        this.on_hold = on_hold;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public void setCancelled(String cancelled) {
        this.cancelled = cancelled;
    }

    public void setRefunded(String refunded) {
        this.refunded = refunded;
    }

    public void setFailed(String failed) {
        this.failed = failed;
    }

    public void setStatus(String[] status) {
        this.status = status;
    }

    public String getPending() {
        return pending;
    }

    public String getProcessing() {
        return processing;
    }

    public String getOn_hold() {
        return on_hold;
    }

    public String getCompleted() {
        return completed;
    }

    public String getCancelled() {
        return cancelled;
    }

    public String getRefunded() {
        return refunded;
    }

    public String getFailed() {
        return failed;
    }

    public String[] getStatus() {
        return status;
    }
}
