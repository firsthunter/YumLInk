package entities;

import java.time.LocalDate;
import java.time.LocalTime;

public class Défis {
int id_d;
String nom_d;
String photo_d;
String dis_d;
LocalDate delai;
LocalTime heure;
User user;

public Défis(int i){}


    public Défis(int id_d, String nom_d, String photo_d, String dis_d, LocalDate delai, LocalTime heure, User user) {
        this.id_d = id_d;
        this.nom_d = nom_d;
        this.photo_d = photo_d;
        this.dis_d = dis_d;
        this.delai = delai;
        this.heure = heure;
        this.user = user;
    }
    public Défis( String nom_d, String photo_d, String dis_d, LocalDate delai, LocalTime heure, User user) {

        this.nom_d = nom_d;
        this.photo_d = photo_d;
        this.dis_d = dis_d;
        this.delai = delai;
        this.heure = heure;
        this.user = user;
    }

    public Défis(int id_d, String nom_d) {
    this.id_d=id_d;
    this.nom_d=nom_d;
    }

    public String getPhoto_d() {
        return photo_d;
    }

    public void setPhoto_d(String photo_d) {
        this.photo_d = photo_d;
    }

    public LocalDate getDelai() {
        return delai;
    }

    public void setDelai(LocalDate delai) {
        this.delai = delai;
    }

    public LocalTime getHeure() {
        return heure;
    }

    public void setHeure(LocalTime heure) {
        this.heure = heure;
    }

    public int getId_d() {
        return id_d;
    }

    public void setId_d(int id_d) {
        this.id_d = id_d;
    }

    public String getNom_d() {
        return nom_d;
    }

    public void setNom_d(String nom_d) {
        this.nom_d = nom_d;
    }

    public String getDis_d() {
        return dis_d;
    }

    public void setDis_d(String dis_d) {
        this.dis_d = dis_d;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Défis{" +
                "id_d=" + id_d +
                ", nom_d='" + nom_d + '\'' +
                ", photo_d='" + photo_d + '\'' +
                ", dis_d='" + dis_d + '\'' +
                ", delai=" + delai +
                ", heure=" + heure +
                '}';
    }
}
