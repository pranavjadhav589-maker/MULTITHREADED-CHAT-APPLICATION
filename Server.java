import java.io.*;
import java.net.*;
import java.util.*;

public class Server {

    private static final int PORT = 5000;

    //Store all connected clients
    static Set<ClientHandler> clients =
            Collections.synchronizedSet(new HashSet<>());

    public static void main(String[] args) {

        try(ServerSocket serverSocket =
                    new ServerSocket(PORT)) {

            System.out.println("Server started...");
            System.out.println("Waiting for clients...");

            while(true){

                Socket socket = serverSocket.accept();

                System.out.println("New client connected");

                ClientHandler client =
                        new ClientHandler(socket);

                clients.add(client);

                Thread thread =
                        new Thread(client);

                thread.start();
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    //Broadcast message to all users
    static void broadcast(String msg,
                          ClientHandler sender){

        synchronized(clients){

            for(ClientHandler client : clients){

                if(client != sender){
                    client.sendMessage(msg);
                }
            }
        }
    }

    //Remove disconnected client
    static void removeClient(
            ClientHandler client){

        clients.remove(client);
    }
}