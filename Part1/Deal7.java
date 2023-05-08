import java.util.ArrayList;
import java.util.List;

public class Deal7 {
    private Deck deck;
    private List<List<String>> players;

    public Deal7() {
        deck = new Deck();
        players = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            players.add(new ArrayList<>());
        }
        dealCards();
    }

    private void dealCards() {
        deck.shuffle();
        List<String> dealtCards = new ArrayList<>();
        for (List<String> playerCards : players) {
            dealtCards.addAll(playerCards);
        }
        deck.removeCards(dealtCards);
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 4; j++) {
                String card = deck.deal();
                players.get(j).add(card);
                dealtCards.add(card);
            }
        }
    }

    public void printPlayers() {
        for (int i = 0; i < 4; i++) {
            System.out.println("Player " + (i+1) + ": " + players.get(i));
        }
    }

}
