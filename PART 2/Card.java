import java.util.Map;
import java.util.LinkedHashMap;

public class Card {
    private String suit;
    private String rank;

    public Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public int getRankValue() {
        Map<String, Integer> rankToValue = new LinkedHashMap<>();
        rankToValue.put("A", 14); // Make Ace the highest
        rankToValue.put("K", 13);
        rankToValue.put("Q", 12);
        rankToValue.put("J", 11);
        rankToValue.put("X", 10); // Assuming X is a ten
        rankToValue.put("9", 9);
        rankToValue.put("8", 8);
        rankToValue.put("7", 7);
        rankToValue.put("6", 6);
        rankToValue.put("5", 5);
        rankToValue.put("4", 4);
        rankToValue.put("3", 3);
        rankToValue.put("2", 2);

        return rankToValue.get(rank);
    }

    public static int compareRanks(Card card1, Card card2) {
        int rankValue1 = card1.getRankValue();
        int rankValue2 = card2.getRankValue();

        // Note that the order of parameters in the compare method is switched
        // to make it so that a higher rank returns a positive value
        return Integer.compare(rankValue1, rankValue2);
    }

    @Override
    public String toString() {
        return suit + rank;
    }
}
