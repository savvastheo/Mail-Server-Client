package mailserver;

/**
 * @author Savvas Theofilou
 */
public class Email {
    private boolean isNew;
    private String sender;
    private String receiver;
    private String subject;
    private String mainbody;
    
    public Email(){
        this.isNew=true;
        this.sender="NO_NAME";
        this.receiver="NO_NAME";
        this.subject="NO_SUBJECT";
        this.mainbody="NO_MAINBODY";
    }
    
    public Email(boolean isNew, String sender, String receiver, String subject, String mainbody){
        this.isNew=isNew;
        this.sender=sender;
        this.receiver=receiver;
        this.subject=subject;
        this.mainbody=mainbody;
    }
    
    public void setNew(boolean isNew){
        this.isNew=isNew;
    }
    
    public void setSender(String sender){
        this.sender=sender;
    }
    
    public void setReceiver(String receiver){
        this.receiver=receiver;
    }
    
    public void setSubject(String subject){
        this.subject=subject;
    }
    
    public void setMainbody(String mainbody){
        this.mainbody=mainbody;
    }
    
    public boolean getIsNew(){
        return this.isNew;
    }
    
    public String getSender(){
        return this.sender;
    }
    
    public String getReceiver(){
        return this.receiver;
    }
    
    public String getSubject(){
        return this.subject;
    }
    
    public String getMainbody(){
        return this.mainbody;
    }
}