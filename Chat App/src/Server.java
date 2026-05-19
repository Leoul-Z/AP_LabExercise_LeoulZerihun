import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {

    Socket socket;
    ServerSocket serverSocket;
    static CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();
    DatabaseHelper db;

    public void startServer() {
        try {
            db = new DatabaseHelper();
            db.connect();
            db.createTables();

            serverSocket = new ServerSocket(5000);

            while (true) {

                socket = serverSocket.accept();

                ClientHandler client = new ClientHandler(socket, db);
                clients.add(client);

                Thread thread = new Thread(client);
                thread.start();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeClient(ClientHandler client) {
        clients.remove(client);
    }
}
