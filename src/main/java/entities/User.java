package entities;

public class User {
    int idU;
    String nom;
    String prenom;
    String email;
    String mdp;
    int tel;
    String role;

    String Image;
    int idA;
    public User(){}

    public User(int idU, String nom, String prenom, String email, String mdp, int tel,String role,int idA,String Image) {
        this.idU = idU;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.tel = tel;
        this.role=role;
        this.idA=idA;
        this.Image=Image;
    }
    public User(String nom, String prenom, String email, String mdp, int tel,String role,int idA,String Image) {
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.tel = tel;
        this.role=role;
        this.idA=idA;
        this.Image=Image;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMdp() {
        return mdp;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }

    public int getTel() {
        return tel;
    }

    public void setTel(int tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "User{" +
                "idU=" + idU +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", email='" + email + '\'' +
                ", mdp='" + mdp + '\'' +
                ", tel=" + tel +
                ", role='" + role + '\'' +
                ", Image='" + Image + '\'' +
                ", idA=" + idA +
                '}';
    }
}
