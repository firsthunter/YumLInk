package entities;

public class User {
    private int idU;
    private String nom;
    private String prenom;
    private String email;
    private String mdp;
    private int tel;
    private String role;
    private String adresse;

    public User(int idU, String nom, String prenom, String email, String mdp, int tel, String role, String adresse) {
        this.idU = idU;
        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.tel = tel;
        this.role = role;
        this.adresse = adresse;
    }
    public User( String nom, String prenom, String email, String mdp, int tel, String role, String adresse) {

        this.nom = nom;
        this.prenom = prenom;
        this.email = email;
        this.mdp = mdp;
        this.tel = tel;
        this.role = role;
        this.adresse = adresse;
    }

    public User(int idU, String nom, String prenom) {
        this.idU = idU;
        this.nom = nom;
        this.prenom = prenom;
    }

    public User(){}

    public User(int idU) {
        this.idU = idU;
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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
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
                ", adresse='" + adresse + '\'' +
                '}';
    }
}
