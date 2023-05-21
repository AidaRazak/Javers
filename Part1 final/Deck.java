import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Deck {
    private List<String> cards;
    private List<String> centerCards;

    public Deck() {
        initializeDeck();
        centerCards = new ArrayList<>();
        centerCards.add(cards.remove(cards.size() - 1));
    }

    public void updateCenterCards(String card) {
        centerCards.add(card);
    }

   
    private void initializeDeck() {
        cards = new ArrayList<>();
        String[] ranks = {"A", "5", "9", "K", "2", "6", "X", "3", "7", "J", "4", "8", "Q"};
        String[] suits = {"s", "h", "d", "c"};

        for (String suit : suits) {
            for (String rank : ranks) {
                cards.add(rank + suit);
            }
        }

        Collections.shuffle(cards);
    }

    public List<List<String>> dealToPlayers(int numPlayers, int numCards) {
        List<List<String>> playerHands = new ArrayList<>();

        for (int i = 0; i < numPlayers; i++) {
            List<String> hand = new ArrayList<>();
            for (int j = 0; j < numCards; j++) {
                hand.add(cards.remove(cards.size() - 1));
            }
            playerHands.add(hand);
        }

        return playerHands;
    }

    public List<String> getCenterCards() {
        return centerCards;
    }
    
    public boolean isEmpty() {
        return cards.isEmpty();
    }
    
    public String drawCard() {
        if (!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        } else {
            throw new IllegalStateException("The deck is empty.");
        }
    }

   
    

    @Override
    public String toString() {
        return "Deck{" +
                "cards=" + cards +
                '}';
    }
}
