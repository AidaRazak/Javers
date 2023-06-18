import java.util.ArrayList;
import java.util.List;

import java.io.Serializable;

public class Player implements Serializable {
    private List<Card> hand;

    public Player() {
        hand = new ArrayList<>();
    }

    public void drawCard(Card card) {
        hand.add(card);
    }

    public List<Card> getHand() {
        return hand;
    }

    public void removeCard(Card card) {
        hand.remove(card);
    }

    public Card playCard(int index) {
        return hand.remove(index);
    }

    public boolean hasCard(Card card) {
        return hand.contains(card);
    }

    public boolean hasCardWithSuit(String suit) {
        for (Card card : hand) {
            if (card.getSuit().equals(suit)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasValidCard(Card centerCard) {
        for (Card card : hand) {
            if (card.getSuit().equals(centerCard.getSuit()) || card.getRank().equals(centerCard.getRank())) {
                return true;
            }
        }
        return false;
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : hand) {
            sb.append(card.toString()).append(" ");
        }
        return sb.toString();
    }

    public void clearHand() {
        hand.clear();
    }
}
