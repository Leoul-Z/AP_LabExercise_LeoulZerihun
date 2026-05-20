import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        AssetGenerator.ensureAssetsExist();
        
        SwingUtilities.invokeLater(() -> new PokerUI());
    }
}