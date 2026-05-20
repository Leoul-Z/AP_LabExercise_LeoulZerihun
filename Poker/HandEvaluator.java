import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class HandEvaluator {

    public static final int HIGH_CARD = 1;
    public static final int PAIR = 2;
    public static final int TWO_PAIR = 3;
    public static final int THREE_OF_A_KIND = 4;
    public static final int STRAIGHT = 5;
    public static final int FLUSH = 6;
    public static final int FULL_HOUSE = 7;
    public static final int FOUR_OF_A_KIND = 8;
    public static final int STRAIGHT_FLUSH = 9;
    public static final int ROYAL_FLUSH = 10;

    public static int evaluate(ArrayList<Card> allCards) {
        boolean flush = isFlush(allCards);
        boolean straight = isStraight(allCards);

        int[] count = new int[15]; 
        for (Card card : allCards) {
            count[card.getValue()]++;
        }

        int pairs = 0;
        boolean three = false;
        boolean four = false;

        for (int i = 2; i < count.length; i++) {
            if (count[i] == 2) pairs++;
            if (count[i] == 3) three = true;
            if (count[i] == 4) four = true;
        }

        if (flush && straight && hasAceAndKing(allCards)) return ROYAL_FLUSH;
        if (flush && straight) return STRAIGHT_FLUSH;
        if (four) return FOUR_OF_A_KIND;
        if (three && pairs >= 1) return FULL_HOUSE;
        if (flush) return FLUSH;
        if (straight) return STRAIGHT;
        if (three) return THREE_OF_A_KIND;
        if (pairs >= 2) return TWO_PAIR;
        if (pairs == 1) return PAIR;
        return HIGH_CARD;
    }

    
    public static int getHighCard(ArrayList<Card> allCards) {
        int max = 0;
        for (Card card : allCards) {
            if (card.getValue() > max) {
                max = card.getValue();
            }
        }
        return max;
    }

    private static boolean isFlush(ArrayList<Card> cards) {

        Map<String, Integer> suitCount = new HashMap<>();
        for (Card card : cards) {
            suitCount.put(card.getSuit(),
                    suitCount.getOrDefault(card.getSuit(), 0) + 1);
        }
        for (int c : suitCount.values()) {
            if (c >= 5) return true;
        }
        return false;
    }

    private static boolean isStraight(ArrayList<Card> cards) {
        ArrayList<Integer> values = new ArrayList<>();
        for (Card card : cards) {
            if (!values.contains(card.getValue())) {
                values.add(card.getValue());
            }
        }
        Collections.sort(values);

        int consecutive = 1;
        for (int i = 1; i < values.size(); i++) {
            if (values.get(i) == values.get(i - 1) + 1) {
                consecutive++;
                if (consecutive >= 5) return true;
            } else {
                consecutive = 1;
            }
        }

        if (values.contains(14) && values.contains(2) &&
                values.contains(3) && values.contains(4) &&
                values.contains(5)) {
            return true;
        }

        return false;
    }

    private static boolean hasAceAndKing(ArrayList<Card> cards) {
        boolean hasAce = false;
        boolean hasKing = false;
        for (Card card : cards) {
            if (card.getValue() == 14) hasAce = true;
            if (card.getValue() == 13) hasKing = true;
        }
        return hasAce && hasKing;
    }

    public static String getHandName(int rank) {
        switch (rank) {
            case ROYAL_FLUSH:
                return "Royal Flush";
            case STRAIGHT_FLUSH:
                return "Straight Flush";
            case FOUR_OF_A_KIND:
                return "Four of a Kind";
            case FULL_HOUSE:
                return "Full House";
            case FLUSH:
                return "Flush";
            case STRAIGHT:
                return "Straight";
            case THREE_OF_A_KIND:
                return "Three of a Kind";
            case TWO_PAIR:
                return "Two Pair";
            case PAIR:
                return "Pair";
            default:
                return "High Card";
        }
    }
}