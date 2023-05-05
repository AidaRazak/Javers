import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<String> cards;

    public Deck() {
        this.cards = new ArrayList<String>();
        String[] suits = {"c","d","h","s"};
        String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K","A"};
        for (String suit : suits) {
            for (String rank : ranks) {
                this.cards.add(suit + rank); 
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