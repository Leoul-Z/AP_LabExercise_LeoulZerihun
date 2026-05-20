import java.util.ArrayList;

public class Player {
    private String name;
    private int chips;
    private ArrayList<Card> hand;
    private boolean folded;

    public Player(String name, int chips) {
        this.name = name;
        this.chips = chips;
        this.hand = new ArrayList<>();
        this.folded = false;
    }

    public void addCard(Card card) {
        hand.add(card);
    }

    public void clearHand() {
        hand.clear();
        folded = false;
    }

    public ArrayList<Card> getHand() {
        return hand;
    }

    public String getName() {
        return name;
    }

    public int getChips() {
        return chips;
    }

    public void addChips(int amount) {
        chips += amount;
    }

    public void removeChips(int amount) {
        if (amount > chips) {
            amount = chips;
        }
        chips -= amount;
    }

    public boolean isFolded() {
        return folded;
    }

    public void fold() {
        folded = true;
    }
}