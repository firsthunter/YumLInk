package entities;

public final class sessionManager {
   private static sessionManager instance;
   private int idU;

   private String email;

   private int idA;


private sessionManager(){}

    public sessionManager(int idU, String email, int idA) {
        this.idU = idU;
        this.email = email;
        this.idA = idA;
    }

    public static sessionManager getInstace(int idU,String email,int idA){
       if (instance==null){instance=new sessionManager(idU,email,idA);}
       return instance;
    }
    public void startSession(User user){
    this.idU= user.getIdU();
    this.email=user.getEmail();
    this.idA= user.getIdA();
    }
    public void endSession(){
    idU=0;
    email="";
    idA=0;
    }


    public int getIdU() {
        return idU;
    }

    public void setIdU(int idU) {
        this.idU = idU;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getIdA() {
        return idA;
    }

    public void setIdA(int idA) {
        this.idA = idA;
    }

    @Override
    public String toString() {
        return "sessionManager{" +
                "idU=" + idU +
                ", email='" + email + '\'' +
                ", idA=" + idA +
                '}';
    }
}

