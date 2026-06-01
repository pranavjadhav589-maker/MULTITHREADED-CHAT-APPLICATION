import java.io.*;
import java.net.*;

public class Client {

    public static void main(String[] args){

        try{

            Socket socket =
                    new Socket(
                            "localhost",
                            5000);

            System.out.println(
                    "Connected to server");

            BufferedReader keyboard =
                    new BufferedReader(
                            new InputStreamReader(
                                    System.in));

            BufferedReader input =
                    new BufferedReader(
                            new InputStreamReader(
                                    socket.getInputStream()));

            PrintWriter output =
                    new PrintWriter(
                            socket.getOutputStream(),
                            true);

            //Thread to receive messages
            Thread receiveThread =
                    new Thread(() -> {

                        String msg;

                        try{

                            while((msg=
                                    input.readLine())
                                    !=null){

                                System.out.println(msg);
                            }

                        }catch(IOException e){
                            e.printStackTrace();
                        }

                    });

            receiveThread.start();

            //Send messages
            String message;

            while(true){

                message=
                        keyboard.readLine();

                output.println(message);
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }
}