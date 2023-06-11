import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Game {
    private Deck deck;
    private Player[] players;
    private Card leadCard;
    private ArrayList<Card> centerCards;
    private int currentPlayerIndex;
    private int[] scores;
    private GUI gameGUI;

    public Game() {
        deck = new Deck();
        players = new Player[4];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
        }
        centerCards = new ArrayList<>();
        scores = new int[4];
       gameGUI = new GUI(this);
    }

    public void startGame() {
        deck.resetDeck();
        deck.shuffle();
        dealCards();
        decideFirstPlayer();
        playGame();
    }

    private void dealCards() {
        for (int i = 0; i < 2; i++) {
            for (Player player : players) {
                Card card = deck.drawCard();
                player.drawCard(card);
            }
        }
    }

    private void decideFirstPlayer() {
        leadCard = deck.drawCard();
        System.out.println("The first lead card is: " + leadCard.toString());
        int firstPlayerIndex = determineFirstPlayerIndex(leadCard.getRank());

        System.out.println("Player " + (firstPlayerIndex + 1) + " goes first.");
        Player firstPlayer = players[firstPlayerIndex];
        centerCards.add(leadCard);
        firstPlayer.removeCard(leadCard);

        currentPlayerIndex = firstPlayerIndex;
    }

    private int determineFirstPlayerIndex(String leadRank) {
        Map<String, Integer> rankToPlayerIndex = new LinkedHashMap<>();
        rankToPlayerIndex.put("A", 0);
        rankToPlayerIndex.put("5", 0);
        rankToPlayerIndex.put("9", 0);
        rankToPlayerIndex.put("K", 0);
        rankToPlayerIndex.put("2", 1);
        rankToPlayerIndex.put("6", 1);
        rankToPlayerIndex.put("X", 1);
        rankToPlayerIndex.put("3", 2);
        rankToPlayerIndex.put("7", 2);
        rankToPlayerIndex.put("J", 2);
        rankToPlayerIndex.put("4", 3);
        rankToPlayerIndex.put("8", 3);
        rankToPlayerIndex.put("Q", 3);

        if (rankToPlayerIndex.containsKey(leadRank)) {
            return rankToPlayerIndex.get(leadRank);
        } else {
            throw new IllegalArgumentException("Invalid lead card rank: " + leadRank);
        }
    }

    private int cardsPlayed = 0;

    private void playGame() {
        int trickNumber = 1;
        int cardsPlayed = 0;
        int[] scores = new int[players.length]; // Initialize scores array

        while (!isGameOver()) {
            Player currentPlayer = players[currentPlayerIndex];
            System.out.println("Trick #" + trickNumber);
            printPlayerHands();
            printCenterCards();
            printDeck();

            System.out.println("Turn: Player" + (currentPlayerIndex + 1));

            Card playedCard = getValidCardFromPlayer(currentPlayer);

            if (playedCard != null) {
                System.out.println("Player" + (currentPlayerIndex + 1) + " plays: " + playedCard.toString());
                centerCards.add(playedCard);
                currentPlayer.removeCard(playedCard);
                cardsPlayed++;
            } else {
                System.out.println("Player" + (currentPlayerIndex + 1) + " passes.");
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % players.length;

            if (cardsPlayed == players.length) {
                currentPlayerIndex = determineTrickWinner();
                updateScores();
                trickNumber++;
                cardsPlayed = 0;
                centerCards.clear();
            }
        }

        printFinalScores();
    }

    private Card getValidCardFromPlayer(Player player) {
        List<Card> validCards = getValidCards(player);
        if (validCards.isEmpty()) {
            return null;
        }

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Enter the index of the card you want to play (0-" + (validCards.size() - 1) + "): ");
            int cardIndex = scanner.nextInt();

            if (cardIndex >= 0 && cardIndex < validCards.size()) {
                return validCards.get(cardIndex);
            } else {
                System.out.println("Invalid card index. Please try again.");
            }
        }
    }

    private List<Card> getValidCards(Player player) {
        List<Card> validCards = new ArrayList<>();
        for (Card card : player.getHand()) {
            if (isCardValid(card)) {
                validCards.add(card);
            }
        }
        return validCards;
    }

    private boolean isCardValid(Card card) {
        if (centerCards.isEmpty()) {
            return true;
        }

        String leadSuit = leadCard.getSuit();
        if (card.getSuit().equals(leadSuit)) {
            return true;
        }

        for (Card centerCard : centerCards) {
            if (card.getSuit().equals(centerCard.getSuit())) {
                return false;
            }
        }

        return true;
    }

    private int determineTrickWinner() {
        String leadSuit = leadCard.getSuit();
        int highestRankIndex = -1;
        int highestRankValue = -1;

        for (int i = 0; i < centerCards.size(); i++) {
            Card card = centerCards.get(i);
            if (card.getSuit().equals(leadSuit)) {
                int rankValue = card.getRankValue();
                if (rankValue > highestRankValue) {
                    highestRankIndex = i;
                    highestRankValue = rankValue;
                }
            }
        }

        return (currentPlayerIndex + highestRankIndex) % players.length;
    }

    private void updateScores() {
        int trickWinnerIndex = determineTrickWinner();
        scores[trickWinnerIndex]++;
        currentPlayerIndex = trickWinnerIndex;
    }

    private boolean isGameOver() {
        for (int score : scores) {
            if (score >= 10) {
                return true;
            }
        }
        return false;
    }

    private void printPlayerHands() {
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            System.out.println("Player" + (i + 1) + " hand: " + player.getHandAsString());
        }
    }

    private void printCenterCards() {
        System.out.println("Center cards: " + centerCards.toString());
    }

    private void printDeck() {
        System.out.println("Remaining deck: " + deck.toString());
    }

    private void printFinalScores() {
        System.out.println("Final scores:");
        for (int i = 0; i < scores.length; i++) {
            System.out.println("Player" + (i + 1) + ": " + scores[i]);
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }
}
