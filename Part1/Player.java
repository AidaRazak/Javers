import java.util.ArrayList;
import java.util.List;

public class Player {
    private String name;
    private List<String> cards;

    public Player(String name) {
        this.name = name;
        cards = new ArrayList<>();
    }

    public void addCard(String card) {
        cards.add(card);
    }

    public void printCards() {
        System.out.println(name + "'s cards: " + cards);
    }

    public static Player determineFirstPlayer(String leadCard) {
        char rank = leadCard.charAt(0);
        //char suit = leadCard.charAt(1);

        if (rank == 'A' || rank == '5' || rank == '9' || rank == 'K') {
            return new Player("Player1");
        } else if (rank == '2' || rank == '6' || rank == 'X') {
            return new Player("Player2");
        } else if (rank == '3' || rank == '7' || rank == 'J') {
            return new Player("Player3");
        } else if (rank == '4' || rank == '8' || rank == 'Q') {
            return new Player("Player4");
        } else {
            throw new IllegalArgumentException("Invalid lead card: " + leadCard);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
