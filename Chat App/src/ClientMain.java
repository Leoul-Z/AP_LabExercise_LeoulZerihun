import java.io.IOException;

public class ClientMain {

    public static void main(String[] args) throws IOException {
        String username =
                javax.swing.JOptionPane.showInputDialog(
                        "Enter username"
                );
        Main main= new Main();
        Client client = new Client(main, username);

        main.setClient(client);

        new Thread(() -> {
            client.startClient();
        }).start();




    }
}
