package mailserver;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Savvas Theofilou
 */
public class Account {
    private String username;
    private String password;
    private ArrayList<Email> mailbox;
    
    public Account(){
        this.username="NO_USERNAME";
        this.password="NO_PASSWORD";
        this.mailbox=null;
    }
    
    public Account(String username, String password, ArrayList<Email> mailbox){
        this.username=username;
        this.password=password;
        this.mailbox=mailbox;
    }
    
    public void setUsername(String username){
        this.username=username;
    }
    
    public void setPassword(String password){
        this.password=password;
    }
    
    public void setMailbox(ArrayList<Email> mailbox){
        this.mailbox=mailbox;
    }
    
    public String getUsername(){
        return this.username;
    }
    
    public String getPassword(){
        return this.password;
    }
    
    public List<Email> getMailbox(){
        return this.mailbox;
    }
    
    /**
     * shows all emails of the current mailbox
     * @param out output stream
     */
    public void showEmails(PrintWriter out){
        out.format("ID\t\t\tFrom\t\t\t\tSubject\n");
        for (int i=0;i<this.mailbox.size();i++){
            String status;
            if (this.mailbox.get(i).getIsNew()){
                status=" [New] ";
            }
            else{
                status=" ";
            }
            out.format("%d. %s\t\t%s\t\t\t\t%s\n",(i+1),status,this.mailbox.get(i).getSender(),this.mailbox.get(i).getSubject());
        }
    }
    
    /**
     * shows a specific email
     * @param indexOfEmail index of the email in the mailbox
     * @param out output stream
     */
    public void readEmail(int indexOfEmail, PrintWriter out){
        if (this.mailbox.get(indexOfEmail-1).getIsNew()){
            out.println();
            out.println("New: Yes.");
        }
        else{
            out.println();
            out.println("New: No.");
        }
        out.println("Sender: "+this.mailbox.get(indexOfEmail-1).getSender());
        out.println("Subject: "+this.mailbox.get(indexOfEmail-1).getSubject());
        out.println("Mainbody:");
        out.println(this.mailbox.get(indexOfEmail-1).getMainbody());
        this.mailbox.get(indexOfEmail-1).setNew(false);
    }
}