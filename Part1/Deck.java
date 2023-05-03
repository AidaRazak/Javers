import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<String> cards;

    public Deck() {
        this.cards = new ArrayList<String>();
        String[] suits = {"hearts", "diamonds", "clubs", "spades"};
        String[] ranks = {"ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "jack", "queen", "king"};
        for (String suit : suits) {
            for (String rank : ranks) {
                this.cards.add(rank + " of " + suit);
            }
        }
    }

    public void shuffle() {
        Collections.shuffle(this.cards);
    }

    public String deal() {
        if (this.cards.size() == 0) {
            return null;
        }
        return this.cards.remove(0);
    }

    @Override
    public String toString() {
        return String.join(", ", this.cards);
    }
}