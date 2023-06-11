import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
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
        if (index < 0 || index >= hand.size()) {
            throw new IllegalArgumentException("Invalid card index!");
        }
    
        return hand.remove(index);
    }

    public Card playCard() {
        Scanner scanner = new Scanner(System.in);
        Card cardToPlay = null;

        // Print the player's hand
        System.out.println("Your hand: " + getHandAsString());

        // Ask the player to choose a card to play
        System.out.print("Choose a card to play: ");
        String input = scanner.nextLine().trim();

        // Find the card in the player's hand
        for (Card card : hand) {
            if (card.toString().equals(input)) {
                cardToPlay = card;
                break;
            }
        }

        // Remove the card from the player's hand
        if (cardToPlay != null) {
            hand.remove(cardToPlay);
        } else {
            System.out.println("Invalid card! Choose a card from your hand.");
            cardToPlay = playCard(); // Recursively ask for a valid card
        }

        return cardToPlay;
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

    public String getHandAsString() {
        StringBuilder sb = new StringBuilder();
        for (Card card : hand) {
            sb.append(card.toString()).append(" ");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return getHandAsString();
    }
}
