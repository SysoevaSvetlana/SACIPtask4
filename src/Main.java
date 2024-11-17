public class Main {
    public static void main(String[] args) {

        Thread serverThread = new Thread(() -> {
            Server server = new Server();
            server.start();
        });
        serverThread.start();


        Thread clientThread = new Thread(() -> {
            Client.main(new String[]{});
        });
        clientThread.start();
    }
}