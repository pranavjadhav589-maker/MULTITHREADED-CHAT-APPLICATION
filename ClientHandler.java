import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private String userName;

    public ClientHandler(Socket socket){

        this.socket = socket;

        try{
            input = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));

            output = new PrintWriter(
                    socket.getOutputStream(),
                    true);

            output.println("Enter username:");
            userName=input.readLine();

            Server.broadcast(
                    userName+" joined the chat",
                    this);

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @Override
    public void run(){

        String message;

        try{

            while((message =
                    input.readLine())!=null){

                System.out.println(
                        userName+": "+message);

                Server.broadcast(
                        userName+": "+message,
                        this);
            }

        }catch(IOException e){

            System.out.println(
                    userName+" disconnected");

        }finally{

            Server.removeClient(this);

            Server.broadcast(
                    userName+" left chat",
                    this);

            try{
                socket.close();
            }catch(IOException e){
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(
            String message){

        output.println(message);
    }
}