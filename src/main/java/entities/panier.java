package entities;

public class panier {

    private int idp;
    private int quantite;
    private float prixtotal;
    private int id_prduit;

    public panier(int i, float v) {
        this.quantite=i;
        this.prixtotal=v;
    }

    public void setIdp(int idp) {
        this.idp = idp;
    }
    public int getQuantite() {
        return quantite;
    }
    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
    public float getPrixtotal() {
        return prixtotal;
    }
    public void setPrixtotal(float prixtotal) {
        this.prixtotal = prixtotal;
    }
    public int getId_prduit() {
        return id_prduit;
    }
    public void setId_prduit(int id_prduit) {
        this.id_prduit = id_prduit;
    }
    public int getId_client() {
        return id_client;
    }
    public void setId_client(int id_client) {
        this.id_client = id_client;
    }
    public panier(      ) {

    }

    public panier(int idp, int quantite, float prixtotal, int id_prduit, int id_client) {
        this.idp = idp;
        this.quantite = quantite;
        this.prixtotal = prixtotal;
        this.id_prduit = id_prduit;
        this.id_client = id_client;
    }
    public panier( int quantite, float prixtotal, int id_prduit, int id_client) {
        this.quantite = quantite;
        this.prixtotal = prixtotal;
        this.id_prduit = id_prduit;
        this.id_client = id_client;
    }

    private int id_client;
    @Override
    public String toString() {
        return "panier{" +
                "idp=" + idp +
                ", quantite=" + quantite +
                ", prixtotal=" + prixtotal +
                ", id_prduit=" + id_prduit +
                ", id_client=" + id_client +
                '}';
    }
}
