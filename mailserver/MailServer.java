package mailserver;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * @author Savvas Theofilou
 */
public class MailServer {
    
    /**
     * prints Main Menu
     * @param out output stream
     */
    public static void printMenu(PrintWriter out){
        out.println(" ");
        out.println("==========");
        out.println("> LogIn");
        out.println("> Register");
        out.println("> Exit");
        out.println("==========");
    }
    
    /**
     * prints second Menu(after LogIn)
     * @param out output stream
     */
    public static void printMenuOfUser(PrintWriter out){
        out.println(" ");
        out.println("==========");
        out.println("> NewEmail");
        out.println("> ShowEmails");
        out.println("> ReadEmail");
        out.println("> DeleteEmail");
        out.println("> LogOut");
        out.println("> Exit");
        out.println("==========");
    }
    
    /**
     * Register process
     * @param listOfAccounts a list of all accounts in mailserver
     * @param in input stream
     * @param out output stream
     * @return true if register is successful, false if not
     */
    public static boolean registerSuccessful(ArrayList<Account> listOfAccounts, BufferedReader in, PrintWriter out){
        String username = null,password = null;
        boolean validUsername;
        out.println("==========");
        out.println("Register (To Exit give 0(zero) as input)");
        out.println("==========");
        do{
            out.println("Please type your username: ");
            try{
                out.println("WAITING_FOR_MESSAGE");
                username=in.readLine();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
            if (username.equals("0")){
                return false;
            }
            validUsername=true;
            for (int i=0;i<listOfAccounts.size();i++){
                if (listOfAccounts.get(i).getUsername().equals(username)){
                    out.println("Username already exists!");
                    validUsername=false;
                }
            }
        }
        while(!validUsername);
        out.println("Please type your password: ");
        try{
            out.println("WAITING_FOR_MESSAGE");
            password=in.readLine();
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        if (password.equals("0")){
            return false;
        }
        listOfAccounts.add(new Account(username,password,new ArrayList<Email>()));
        return true;
    }
    
    /**
     * 
     * @param listOfAccounts a list of all accounts in mailserver
     * @param in input stream
     * @param out output stream
     * @return -1 if failed, index of the logged-in account
     */
    public static int loginIntoAccount(ArrayList<Account> listOfAccounts, BufferedReader in, PrintWriter out){
        String username=null,password=null;
        boolean matchUsername,matchPassword;
        int indexOfAccount=0;
        
        out.println("==========");
        out.println("LogIn (To Exit give 0(zero) as input)");
        out.println("==========");
        do{
            out.println("Please type your username: ");
            try{
                out.println("WAITING_FOR_MESSAGE");
                username=in.readLine();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
            matchUsername=false;
            if (username.equals("0")){
                return -1;
            }
            for (int i=0;i<listOfAccounts.size();i++){
                if (listOfAccounts.get(i).getUsername().equals(username)){
                    matchUsername=true;
                    indexOfAccount=i;
                }
            }
            if (!matchUsername){
                out.println("Username not found!");
            }
        }
        while(!matchUsername);
        
        do{
            out.println("Please type your password: ");
            try{
                out.println("WAITING_FOR_MESSAGE");
                password=in.readLine();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
            if (password.equals("0")){
                return -1;
            }
            matchPassword=true;
            if (!password.equals(listOfAccounts.get(indexOfAccount).getPassword())){
                out.println("Password doesn't match!");
                matchPassword=false;
            }
        }
        while(!matchPassword);
        
        return indexOfAccount;
    }
    
    /**
     * 
     * @return the list of all accounts stored in "accounts.txt"
     */
    public static ArrayList<Account> loadAccounts() { 
        ArrayList<Account> listOfAccounts=new ArrayList<>();
        try (BufferedReader in=new BufferedReader(new FileReader("accounts.txt"));){
            String line;
            while ((line=in.readLine())!=null){
                StringTokenizer tempString=new StringTokenizer(line);
                String accountUsername=tempString.nextToken();
                String accountPassword=tempString.nextToken();
                ArrayList<Email> accountMailbox=new ArrayList<>();
                for (int i=1;i<=3;i++){
                    accountMailbox.add(new Email(true,"sender"+i+accountPassword,"receiver"+i+accountPassword,"subject"+i+accountPassword,"mainbody"+i+accountPassword));
                }
                listOfAccounts.add(new Account(accountUsername,accountPassword,accountMailbox));
            }
        }
        catch(IOException e){
            System.out.println("IOException");
        }
        return listOfAccounts;
    }
    
    /**
     * process if LogIn was successful
     * @param listOfAccounts a list of all accounts in mailserver
     * @param indexOfAccount index of the logged-in account
     * @param in input stream
     * @param out outputstream
     * @return 1 if user logged out, -1 if user exited the program
     */
    public static int proceedLogInSuccess(ArrayList<Account> listOfAccounts,int indexOfAccount, BufferedReader in, PrintWriter out){
        String userChoice = null;
        boolean inputIsCorrect;
        boolean returnToMenu;
        
        out.println("LogIn successful!");
        out.println();
        out.println("==========");
        out.format("Welcome back %s!\n",listOfAccounts.get(indexOfAccount).getUsername());
        
        do{
            printMenuOfUser(out);
            returnToMenu=false;
            inputIsCorrect=false;
            out.println("Please type your choice: ");
            try{
                out.println("WAITING_FOR_MESSAGE");
                userChoice=in.readLine();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
            switch (userChoice){
                case "NewEmail":    boolean matchUsername;
                                    String receiver=null;
                                    String subject=null;
                                    String mainbody=null;
                                    int tempIndex = 0;
                                    do{
                                        out.println("Please type the username of the receiver: ");
                                        try{
                                            out.println("WAITING_FOR_MESSAGE");
                                            receiver=in.readLine();
                                        }
                                        catch (IOException e) {
                                            System.out.println(e.getMessage());
                                        }
                                        matchUsername=false;
                                        if (receiver.equals("0")){
                                            return -1;
                                        }
                                        for (int i=0;i<listOfAccounts.size();i++){
                                            if (listOfAccounts.get(i).getUsername().equals(receiver)){
                                                matchUsername=true;
                                                tempIndex=i;
                                            }
                                        }
                                        if (!matchUsername){
                                            out.println("Username not found!");
                                        }
                                    }
                                    while(!matchUsername);
                                    out.println("Please type the subject of the email: ");
                                    try{
                                        out.println("WAITING_FOR_MESSAGE");
                                        subject=in.readLine();
                                    }
                                    catch (IOException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    out.println("Please type the mainbody of the email: ");
                                    try{
                                        out.println("WAITING_FOR_MESSAGE");
                                        mainbody=in.readLine();
                                    }
                                    catch (IOException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    listOfAccounts.get(tempIndex).getMailbox().add(new Email(true,listOfAccounts.get(indexOfAccount).getUsername(),listOfAccounts.get(tempIndex).getUsername(),subject,mainbody));
                                    out.println("Email sent successfully!");
                                                
                                    out.println("Press enter to continue!");
                                    String tempInput3;
                                    try{
                                        out.println("WAITING_FOR_MESSAGE");
                                        tempInput3=in.readLine();
                                    }
                                    catch (IOException e) {
                                        System.out.println(e.getMessage());
                                    }
                                    returnToMenu=true;
                                    inputIsCorrect=true;
                                    break;
                case "ShowEmails":  if (listOfAccounts.get(indexOfAccount).getMailbox().isEmpty()){
                                        out.println("Mailbox is empty!");
                                    }
                                    else{
                                        listOfAccounts.get(indexOfAccount).showEmails(out);
                                        out.println("Press enter to continue!");
                                        String tempInput;
                                        try{
                                            out.println("WAITING_FOR_MESSAGE");
                                            tempInput=in.readLine();
                                        }
                                        catch (IOException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                    inputIsCorrect=true;
                                    returnToMenu=true;
                                    break;
                case "ReadEmail":   if (listOfAccounts.get(indexOfAccount).getMailbox().isEmpty()){
                                        out.println("Mailbox is empty!");
                                    }
                                    else{
                                        int indexOfEmail=0;
                                        do{
                                            out.println("Please give the index of the email: ");
                                            try{
                                                out.println("WAITING_FOR_MESSAGE");
                                                indexOfEmail=Integer.parseInt(in.readLine());
                                            }
                                            catch (IOException e) {
                                                System.out.println(e.getMessage());
                                            }
                                            if ((indexOfEmail<1) || (indexOfEmail>(listOfAccounts.get(indexOfAccount).getMailbox().size()))){
                                                out.println("Index of email not found!");
                                            }
                                        }
                                        while((indexOfEmail<1) || (indexOfEmail>(listOfAccounts.get(indexOfAccount).getMailbox().size())));
                                        listOfAccounts.get(indexOfAccount).readEmail(indexOfEmail,out);
                                        out.println("Press enter to continue!");
                                        String tempInput2;
                                        try{
                                            out.println("WAITING_FOR_MESSAGE");
                                            tempInput2=in.readLine();
                                        }
                                        catch (IOException e) {
                                            System.out.println(e.getMessage());
                                        }
                                    }
                                    inputIsCorrect=true;
                                    returnToMenu=true;
                                    break;
                case "DeleteEmail": if (listOfAccounts.get(indexOfAccount).getMailbox().isEmpty()){
                                        out.println("Mailbox is empty!");
                                    }
                                    else{
                                        int indexOfEmail2=0;
                                        do{
                                            out.println("Please give the index of the email: ");
                                            try{
                                                out.println("WAITING_FOR_MESSAGE");
                                                indexOfEmail2=Integer.parseInt(in.readLine());
                                            }
                                            catch (IOException e) {
                                                System.out.println(e.getMessage());
                                            }
                                            if ((indexOfEmail2<1) || (indexOfEmail2>(listOfAccounts.get(indexOfAccount).getMailbox().size()))){
                                                out.println("Index of email not found!");
                                            }
                                        }
                                        while((indexOfEmail2<1) || (indexOfEmail2>(listOfAccounts.get(indexOfAccount).getMailbox().size())));
                                        listOfAccounts.get(indexOfAccount).getMailbox().remove(indexOfEmail2-1);
                                        out.println("Email deleted successfully!");
                                    }
                                    inputIsCorrect=true;
                                    returnToMenu=true;
                                    break;
                case "LogOut":      out.println("Logging out.."); 
                                    return 1;
                case "Exit":        out.println("Exiting program.."); 
                                    return -1;
                default:            out.print("Wrong input! ");
                                    break;
            }
        }
        while (!inputIsCorrect || returnToMenu);
        return 0;
    }
    
    /**
     * 
     * @param args [0] -> Server port
     * @throws Exception 
     */
    public static void main(String[] args) throws Exception{
        try{
            int serverPort = Integer.parseInt(args[0]); // the server port
            ServerSocket listenSocket = new ServerSocket(serverPort);
            while(true) {
                Socket clientSocket = listenSocket.accept();
                System.out.println("Request from client" + clientSocket.getInetAddress()+"at port "+ clientSocket.getPort());				
                Connection c = new Connection(clientSocket);
            }
        }
        catch(IOException e) {System.out.println("Listen socket:"+e.getMessage());}
    }
    
    /**
     * used for multi client support (every client is a new thread)
     */
    private static class Connection extends Thread{
        private Socket socket;
        
        public Connection(Socket socket) {
            this.socket = socket;
            System.out.println("New client connected at " + socket);
            this.start();
        }
        
        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                begin(in,out);
            }
            catch (IOException e) {
                System.out.println("Error handling client");
            }
            finally {
                try { socket.close(); } catch (IOException e) {}
                    System.out.println("Connection with client closed");
            }
        }
    }
    
    /**
     * beginning of the mailserver (when a client connects)
     * @param in input stream
     * @param out output stream
     */
    public static void begin(BufferedReader in, PrintWriter out){
        String userChoice=null;
        boolean inputIsCorrect;
        boolean returnToMenu;
        ArrayList<Account> listOfAccounts;
        listOfAccounts=loadAccounts();

        out.println("----------");
        out.println("MailServer:");
        out.println("----------");
        out.println("Hello, you connected as a guest.");
        printMenu(out);
        
        do{
            returnToMenu=false;
            inputIsCorrect=false;
            out.println("Please type your choice: ");
            try{
                out.println("WAITING_FOR_MESSAGE");
                userChoice=in.readLine();
            }
            catch (IOException e) {
                System.out.println(e.getMessage());
            }
            switch (userChoice){
                case "LogIn":       int tempIndex=loginIntoAccount(listOfAccounts, in, out);
                                    if (tempIndex!=-1){
                                        if (proceedLogInSuccess(listOfAccounts,tempIndex, in, out)==1){
                                            printMenu(out);
                                            returnToMenu=true;
                                        }
                                        else{
                                            //exit
                                        }
                                    }
                                    else{
                                        out.println("LogIn failed. Returning to main menu.");
                                        printMenu(out);
                                        returnToMenu=true;
                                    }
                                    inputIsCorrect=true;
                                    break;
                case "Register":    if (registerSuccessful(listOfAccounts, in, out)){
                                        out.println("Register successful! Please LogIn to continue!");
                                        printMenu(out);
                                        returnToMenu=true;
                                    }
                                    else{
                                        out.println("Register failed! Returning to main menu..");
                                        printMenu(out);
                                        returnToMenu=true;
                                    }
                                    inputIsCorrect=true;
                                    break;
                case "Exit":        out.println("Exiting program..");
                                    inputIsCorrect=true;
                                    break;
                default:            out.println("Wrong input! ");
                                    break;
            }
        }
        while(!inputIsCorrect || returnToMenu); 
    }
}