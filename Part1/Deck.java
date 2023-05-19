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
        String[] ranks = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "X", "J", "Q", "K"};
        String[] suits = {"s", "h", "d", "c"};

        // Create the deck
        for (String suit : suits) {
            for (String rank : ranks) {
                String card = rank + suit;
                cards.add(card);
            }
        }

        // Shuffle the deck
        Collections.shuffle(cards);
    }

    public String deal() {
        if (!cards.isEmpty()) {
            return cards.remove(cards.size() - 1);
        } else {
            return null;
        }
    }
    public List<List<String>> dealToPlayers(int numPlayers, int cardsPerPlayer) {
        List<List<String>> playerHands = new ArrayList<>();
    
        for (int i = 0; i < numPlayers; i++) {
            List<String> hand = new ArrayList<>();
    
            // Deal cards to the player's hand
            for (int j = 0; j < cardsPerPlayer; j++) {
                String card = deal();
                if (card != null) {
                    hand.add(card);
                }
            }
    
            playerHands.add(hand);
        }
    
        return playerHands;
    }
    
    @Override
    public String toString() {
        return cards.toString();
    }

    public List<String> getCenterCards() {
        return centerCards;
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
}
