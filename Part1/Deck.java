import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<String> cards;

    public Deck() {
        initializeDeck();
    }

    private void initializeDeck() {
        cards = new ArrayList<>();
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] suits = {"s", "h", "d", "c"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(rank + suit);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }

    public String deal() {
        if (!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        } else {
            return null;
        }
    }

    @Override
    public String toString() {
        return cards.toString();
    }

    public void removeCards(List<String> dealtCards) {
    }
}
