import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    private String name;
    private List<String> cards;
    private int score;

    public Player(String name) {
        this.name = name;
        cards = new ArrayList<>();
        score = 0;
    }

    public String getName() {
        return name;
    }

    public List<String> getCards() {
        return cards;
    }

    public void setCards(List<String> cards) {
        this.cards = cards;
    }

    public void addCard(String card) {
        cards.add(card);
    }

    public void removeCard(String card) {
        cards.remove(card);
    }

    public void incrementScore() {
        score++;
    }

    public void printCards() {
        System.out.println(name + "'s cards: " + cards);
    }

    public String playCard(String leadSuit, String leadRank, List<String> centerCards) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("\n" + name + " > ");

            String card = scanner.nextLine();

            if (isValidCard(card, leadSuit, leadRank, centerCards)) {
                cards.remove(card); // Remove the played card from the player's hand
                return card;
            } else {
                System.out.println("Invalid card. Try again.");
            }
        }
    }

    private boolean isValidCard(String card, String leadSuit, String leadRank, List<String> centerCards) {
        String suit = card.substring(0, 1);
        String rank = card.substring(1);

        if (suit.equals(leadSuit) || rank.equals(leadRank)) {
            return true;
        }

        for (String c : cards) {
            String s = c.substring(0, 1);
            String r = c.substring(1);

            if (s.equals(leadSuit) || r.equals(leadRank)) {
                return false;
            }
        }

        return true;
    }

    public static Player determineFirstPlayer(String leadCard) {
        char rank = leadCard.charAt(0);

        if (rank == 'A' || rank == '5' || rank == '9' || rank == 'K') {
            return new Player("Player1");
        } else if (rank == '2' || rank == '6' || rank == 'x') {
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
