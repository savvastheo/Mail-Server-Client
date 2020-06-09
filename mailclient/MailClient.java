package mailclient;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author Savvas Theofilou
 */
public class MailClient {
    
    /**
     * 
     * @param args [0] ->IP Address, [1] -> Server port
     */
    public static void main (String args[]) {
	Socket socket  = null;
        Scanner scanner = new Scanner(System.in);
	try{
            int serverPort = Integer.parseInt(args[1]);
	    socket = new Socket(args[0], serverPort);    
	    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            while(true){
                String temp=in.readLine();
                if (temp!=null){
                    if (temp.equals("WAITING_FOR_MESSAGE")){
                        String message = scanner.nextLine();
                        out.println(message);
                    }
                    else{
                        System.out.println(temp);
                    }
                }
                else{
                    break;
                }
            }
            out.close();
            in.close();
            socket.close();
	}catch (UnknownHostException e){System.out.println("Socket:"+e.getMessage());
	}catch (EOFException e){System.out.println("EOF:"+e.getMessage());
	}catch (IOException e){System.out.println("readline:"+e.getMessage());
	}finally {if(socket!=null) try {socket.close();}catch (IOException e){System.out.println("close:"+e.getMessage());}}
    } 
}