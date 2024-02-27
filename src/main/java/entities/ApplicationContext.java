package entities;

public class ApplicationContext {
    private static ApplicationContext instance;
    private sessionManager userSession;

    private ApplicationContext() {
    }
    public static ApplicationContext getInstance(){
        if(instance==null){
            instance=new ApplicationContext();
        }
        return instance;
    }
    public sessionManager getUsersession(){
        return userSession;
    }
    public void setUserSession(sessionManager userSession){
        this.userSession=userSession;
    }
}
