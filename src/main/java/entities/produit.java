package entities;


public class produit {

    private int id;




    private String nom;
private float prix;
private String diescription;
private String image;

public produit(){

}

    public produit(int id, String nom, float prix, String diescription, String image) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.diescription = diescription;
        this.image = image;
    }

    public produit(String nom, float prix, String diescription, String image) {
        this.nom = nom;
        this.prix = prix;
        this.diescription = diescription;
        this.image = image;
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public String getDiescription() {
        return diescription;
    }

    public void setDiescription(String diescription) {
        this.diescription = diescription;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "produit{" +
                "nom='" + nom + '\'' +
                ", prix=" + prix +
                ", diescription='" + diescription + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
