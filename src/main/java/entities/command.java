package entities;

import java.util.Date;

public class command {


    private int id_comm;
    private Date date;
    private int id_client;


    public command() {

    }
    public command(int id_comm, Date date, int id_client) {
        this.id_comm = id_comm;
        this.date = date;
        this.id_client = id_client;

    }

    public command(Date date, int id_client) {
        this.date = date;
        this.id_client = id_client;

    }

    public int getId_comm() {
        return id_comm;
    }

    public void setId_comm(int id_comm) {
        this.id_comm = id_comm;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId_client() {
        return id_client;
    }

    public void setId_client(int id_client) {
        this.id_client = id_client;
    }





    @Override
    public String toString() {
        return "command{" +
                "id_comm=" + id_comm +
                ", date=" + date +
                ", id_client=" + id_client +
                '}';
    }
}
