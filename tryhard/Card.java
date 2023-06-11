import java.util.Map;
import java.util.LinkedHashMap;
import java.io.Serializable;

public class Card implements Serializable {
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

    public static int compareRanks(Card card1, Card card2) {
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

        int rankValue1 = rankToValue.get(card1.getRank());
        int rankValue2 = rankToValue.get(card2.getRank());

        // Note that the order of parameters in the compare method is switched
        // to make it so that a higher rank returns a positive value
        return Integer.compare(rankValue1, rankValue2);
    }

    @Override
    public String toString() {
        return suit + rank;
    }
}
