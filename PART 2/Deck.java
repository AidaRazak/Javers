import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.io.Serializable;

public class Deck implements Serializable {
    private List<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"h", "d", "c", "s"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K"};
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }
        shuffle();
    }

    public boolean isEmpty() {
        return cards.isEmpty();
    }

    public Card drawCard() {
        if (isEmpty()) {
            return null; // or throw an exception indicating an empty deck
        }
        return cards.remove(cards.size() - 1);
    }

    public void resetDeck() {
        cards.clear();
        String[] suits = {"H", "D", "C", "S"};
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K"};
        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(new Card(suit, rank));
            }
        }
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : cards) {
            sb.append(card.toString()).append(" ");
        }
        return sb.toString();
    }
}

