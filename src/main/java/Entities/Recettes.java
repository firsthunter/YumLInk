package Entities;

import java.util.ArrayList;
import java.util.List;

public class Recettes {
    private int id_r , calorie , protein  ;
    private String nom , description , imgSrc , videoSrc;

  //  private List<String> ing = new ArrayList<>();
    public Recettes() {

    }
    public Recettes(int id_r,  String nom, String description, String imgSrc ,int calorie, int protein) {
        this.id_r = id_r;
        this.calorie = calorie;
        this.protein = protein;
        this.nom = nom;
        this.description = description;
        this.imgSrc = imgSrc;

    }
    public Recettes(  String nom, String description, String imgSrc ,int calorie, int protein) {
        this.nom = nom;
        this.description = description;
        this.calorie = calorie;
        this.protein = protein;
        this.imgSrc = imgSrc;

    }

    @Override
    public String toString() {
        return "Recettes{" +
                "id_r=" + id_r +
                ", calorie=" + calorie +
                ", protein=" + protein +
                ", nom='" + nom + '\'' +
                ", description='" + description + '\'' +
                ", imgSrc='" + imgSrc + '\'' +
                ", videoSrc='" + videoSrc + '\'' +
                '}';
    }
    public int getId_r() {
        return id_r;
    }
    public void setId_r(int id_r) {
        this.id_r = id_r;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public int getProtein() {
        return protein;
    }

    public void setProtein(int protein) {
        this.protein = protein;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }


}
