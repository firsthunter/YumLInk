package entities;

public class Adresse {
    int idA;
    String ville;
    String gouvernorat;
    int codePostal;
    String rue;

    public Adresse() {
    }

    public Adresse(String gouvernorat, String ville, String rue, int codePostal) {
        this.ville = ville;
        this.gouvernorat = gouvernorat;
        this.codePostal = codePostal;
        this.rue = rue;
    }

    public Adresse(int idA, String gouvernorat, String ville, String rue, int codePostal) {
        this.idA = idA;
        this.ville = ville;
        this.gouvernorat = gouvernorat;
        this.codePostal = codePostal;
        this.rue = rue;
    }

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getGouvernorat() {
        return gouvernorat;
    }

    public void setGouvernorat(String gouvernorat) {
        this.gouvernorat = gouvernorat;
    }

    public int getCodePostal() {
        return codePostal;
    }

    public void setCodePostal(int codePostal) {
        this.codePostal = codePostal;
    }

    public String getRue() {
        return rue;
    }

    public void setRue(String rue) {
        this.rue = rue;
    }

    @Override
    public String toString() {
        return "Adresse{" +
                "idA=" + idA +
                ", ville='" + ville + '\'' +
                ", gouvernorat='" + gouvernorat + '\'' +
                ", codePostal=" + codePostal +
                ", rue='" + rue + '\'' +
                '}';
    }
}
