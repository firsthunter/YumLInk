package Entities;

public class Ingredient {
   private int id_ing ;
   private String nom ;
   private int quantite ;


    public Ingredient() {

    }
    public Ingredient(int id_ing, String nom, int quantite) {
        this.id_ing = id_ing;
        this.nom = nom;
        this.quantite = quantite;
    }
    public Ingredient(String nom, int quantite) {
        this.nom = nom;
        this.quantite = quantite;
    }

    @Override
    public String toString() {
        return "Ingredient{" +
                "id_ing=" + id_ing +
                ", nom='" + nom + '\'' +
                ", quantite=" + quantite +
                '}';
    }

    public int getId_ing() {
        return id_ing;
    }

    public void setId_ing(int id_ing) {
        this.id_ing = id_ing;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }


}
