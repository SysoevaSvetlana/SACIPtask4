import java.io.*;
import java.net.*;
public class Server {
    private static final int PORT = 12345;
    private Dictionary dictionary;

    public Server() {
        this.dictionary = new Dictionary();
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new ClientHandler(clientSocket).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class ClientHandler extends Thread {
        private Socket clientSocket;

        public ClientHandler(Socket socket) {
            this.clientSocket = socket;
        }

        public void run() {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                 PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    String[] command = inputLine.split(" ");
                    String response;

                    switch (command[0]) {
                        case "set":
                            int key = Integer.parseInt(command[1]);
                            String value = command[2];
                            dictionary.set(key, value);
                            response = "OK";
                            break;
                        case "get":
                            key = Integer.parseInt(command[1]);
                            value = dictionary.get(key);
                            response = value != null ? value : "NULL";
                            break;
                        case "del":
                            key = Integer.parseInt(command[1]);
                            dictionary.delete(key);
                            response = "OK";
                            break;
                        default:
                            response = "ERROR";
                            break;
                    }

                    out.println(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Server().start();
    }
}
