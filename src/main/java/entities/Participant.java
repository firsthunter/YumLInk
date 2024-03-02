package entities;

public class Participant {
    int idpart;
    String photo_p;
    int vote;
    User U;
    Défis D;
    public Participant(){}

    public Participant(int idpart) {
        this.idpart = idpart;
    }

    public Participant(String photo_p, User u, Défis d) {
        this.photo_p = photo_p;
        U = u;
        D = d;
    }

    public Participant(String photo_p, int vote, User u, Défis d) {

        this.photo_p = photo_p;
        this.vote = vote;
        U = u;
        D = d;
    }

    public Participant(int idpart, String photo_p, int vote, User u, Défis d) {
        this.idpart = idpart;
        this.photo_p = photo_p;
        this.vote = vote;
        U = u;
        D = d;
    }

    public Participant(String photo_p, User user, Défis d, int vote) {
        this.photo_p = photo_p;
        U = user;
        D = d;
        this.vote=vote;
    }


    @Override
    public String toString() {
        return "Participant{" +
                "idpart=" + idpart +
                ", photo_p='" + photo_p + '\'' +
                ", vote=" + vote +
                ", U=" + U +
                ", D=" + D +
                '}';
    }

    public int getIdpart() {
        return idpart;
    }

    public void setIdpart(int idpart) {
        this.idpart = idpart;
    }

    public String getPhoto_p() {
        return photo_p;
    }

    public void setPhoto_p(String photo_p) {
        this.photo_p = photo_p;
    }

    public int getVote() {
        return vote;
    }

    public void setVote(int vote) {
        this.vote = vote;
    }

    public User getU() {
        return U;
    }

    public void setU(User u) {
        U = u;
    }

    public Défis getD() {
        return D;
    }

    public void setD(Défis d) {
        D = d;
    }
}
