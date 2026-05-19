import java.io.*;
import java.net.Socket;

public class ClientHandler implements Runnable {

    Socket socket;
    String username;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    DatabaseHelper db;

    public ClientHandler(Socket socket, DatabaseHelper db) {
        this.socket = socket;
        this.db = db;

        try {
            dataInputStream = new DataInputStream(socket.getInputStream());

            dataOutputStream = new DataOutputStream(socket.getOutputStream());

            username = dataInputStream.readUTF();
            
            if (!db.userExists(username)) {
                db.registerUser(username, "default_password");
            }

        } catch (Exception e) {
            System.out.println("Error in creating a connection");
        }
    }


    @Override
    public void run() {
        try {
            while (true) {
                String type =
                        dataInputStream.readUTF();

                if (type.equals("TEXT")) {

                    String targetUser =
                            dataInputStream.readUTF();

                    String message =
                            dataInputStream.readUTF();
                            
                    if (!db.userExists(targetUser)) {
                        db.registerUser(targetUser, "default_password");
                    }
                    db.saveMessage(username, targetUser, message, "TEXT");

                    broadcastMessage(message, targetUser);

                } else if (type.equals("FILE")) {

                    String targetUser =
                            dataInputStream.readUTF();

                    String fileName =
                            dataInputStream.readUTF();

                    int size =
                            dataInputStream.readInt();

                    byte[] fileBytes =
                            new byte[size];

                    dataInputStream.readFully(fileBytes);

                    final File receivedFile =
                            new File("received_" + fileName);

                    try (FileOutputStream fos = new FileOutputStream(receivedFile)) {
                        fos.write(fileBytes);
                    }
                    
                    if (!db.userExists(targetUser)) {
                        db.registerUser(targetUser, "default_password");
                    }
                    db.saveMessage(username, targetUser, fileName, "FILE");

                    broadcastFile(receivedFile, targetUser);
                    
                } else if (type.equals("HISTORY")) {
                    String targetUser = dataInputStream.readUTF();
                    
                    java.util.List<String> history = db.getChatHistory(username, targetUser, 50);
                    for(String histMsg : history) {
                        dataOutputStream.writeUTF("HISTORY_MSG");
                        dataOutputStream.writeUTF(histMsg);
                        dataOutputStream.flush();
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Client " + username + " disconnected.");
        } finally {
            Server.removeClient(this);
            closeResources();
        }
    }

    public void broadcastMessage(String msg, String targetUser) {
        try {
            for (ClientHandler client : Server.clients) {
                if (client.username.equals(targetUser)) {
                    client.dataOutputStream.writeUTF("TEXT");
                    client.dataOutputStream.writeUTF(
                            username + ": " + msg
                    );
                    client.dataOutputStream.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void broadcastFile(File file, String targetUser) {

        try {
            for (ClientHandler client : Server.clients) {
                if (client.username.equals(targetUser)) {
                    try (FileInputStream fis = new FileInputStream(file)) {
                        byte[] fileBytes = new byte[(int) file.length()];
                        fis.read(fileBytes);

                        client.dataOutputStream.writeUTF("FILE");

                        client.dataOutputStream.writeUTF(
                                username + ": " + file.getName()
                        );
                        client.dataOutputStream.writeInt(fileBytes.length);

                        client.dataOutputStream.write(fileBytes);

                        client.dataOutputStream.flush();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeResources() {
        try {
            if (dataInputStream != null) dataInputStream.close();
            if (dataOutputStream != null) dataOutputStream.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
