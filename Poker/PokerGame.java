import java.util.ArrayList;

public class PokerGame {

    private Deck deck;
    private Player player;
    private Player computer;
    private ArrayList<Card> communityCards;
    private int pot;
    private boolean roundOver;

    public PokerGame() {
        player = new Player("You", 1000);
        computer = new Player("Computer", 1000);
        startNewRound();
    }

    public void startNewRound() {
        deck = new Deck();
        deck.shuffle();

        player.clearHand();
        computer.clearHand();

        communityCards = new ArrayList<>();
        pot = 0;
        roundOver = false;

        for (int i = 0; i < 2; i++) {
            Card playerCard = deck.dealCard();
            Card computerCard = deck.dealCard();
            if (playerCard != null) player.addCard(playerCard);
            if (computerCard != null) computer.addCard(computerCard);
        }

        for (int i = 0; i < 5; i++) {
            Card card = deck.dealCard();
            if (card != null) communityCards.add(card);
        }
    }

    public boolean playerRaise(int amount) {
        if (roundOver) return false;

        if (player.getChips() >= amount) {
            player.removeChips(amount);
            pot += amount;

            if (computer.getChips() >= amount) {
                computer.removeChips(amount);
                pot += amount;
            } else if (computer.getChips() > 0) {
                int allIn = computer.getChips();
                computer.removeChips(allIn);
                pot += allIn;
            }
            return true;
        }
        return false; 
    }

    public void playerFold() {
        if (roundOver) return;

        player.fold();
        computer.addChips(pot);
        pot = 0;
        roundOver = true;
    }

    public String determineWinner() {
        if (roundOver) return "Round is already over.";

        roundOver = true;

        if (player.isFolded()) {
            return "Computer Wins!";
        }

        ArrayList<Card> playerCards = new ArrayList<>();
        ArrayList<Card> computerCards = new ArrayList<>();

        playerCards.addAll(player.getHand());
        playerCards.addAll(communityCards);

        computerCards.addAll(computer.getHand());
        computerCards.addAll(communityCards);

        int playerRank = HandEvaluator.evaluate(playerCards);
        int computerRank = HandEvaluator.evaluate(computerCards);

        if (playerRank > computerRank) {
            player.addChips(pot);
            pot = 0;
            return "You Win! (" +
                    HandEvaluator.getHandName(playerRank) + ")";
        } else if (computerRank > playerRank) {
            computer.addChips(pot);
            pot = 0;
            return "Computer Wins! (" +
                    HandEvaluator.getHandName(computerRank) + ")";
        } else {
            int playerHigh = HandEvaluator.getHighCard(playerCards);
            int computerHigh = HandEvaluator.getHighCard(computerCards);

            if (playerHigh > computerHigh) {
                player.addChips(pot);
                pot = 0;
                return "You Win! (" +
                        HandEvaluator.getHandName(playerRank) +
                        ", High Card)";
            } else if (computerHigh > playerHigh) {
                computer.addChips(pot);
                pot = 0;
                return "Computer Wins! (" +
                        HandEvaluator.getHandName(computerRank) +
                        ", High Card)";
            } else {
                int half = pot / 2;
                int remainder = pot % 2;
                player.addChips(half + remainder);
                computer.addChips(half);
                pot = 0;
                return "Draw! Pot split evenly.";
            }
        }
    }

    public boolean isRoundOver() {
        return roundOver;
    }

    public Player getPlayer() {
        return player;
    }

    public Player getComputer() {
        return computer;
    }

    public ArrayList<Card> getCommunityCards() {
        return communityCards;
    }

    public int getPot() {
        return pot;
    }
}