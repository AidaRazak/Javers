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

    public Game() {
        deck = new Deck();
        players = new Player[4];
        for (int i = 0; i < players.length; i++) {
            players[i] = new Player();
        }
        centerCards = new ArrayList<>();
        scores = new int[4];
    }

    public void startGame() {
        deck.resetDeck();
        deck.shuffle();
        dealCards();
        decideFirstPlayer();
        playGame();
    }

    private void dealCards() {
        for (int i = 0; i < 7; i++) {
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
    int[] cardsLeft = new int[players.length]; // Initialize cardsLeft array

    // Initialize cardsLeft with the initial number of cards in each player's hand
    for (int i = 0; i < players.length; i++) {
        cardsLeft[i] = players[i].getHand().size();
    }

    while (!isGameOver()) {
        Player currentPlayer = players[currentPlayerIndex];
        System.out.println("Trick #" + trickNumber);
        printPlayerHands();
        printCenterCards();
        printDeck();

        System.out.println("Turn: Player" + (currentPlayerIndex + 1));

        // Print the score board after each turn
        printScoreBoard();

        Card playedCard = getValidCardFromPlayer(currentPlayer);

        if (playedCard != null) {
            System.out.println("Player" + (currentPlayerIndex + 1) + " plays: " + playedCard.toString());
            centerCards.add(playedCard);
            currentPlayer.removeCard(playedCard);
            cardsPlayed++;
            cardsLeft[currentPlayerIndex]--; // Update cardsLeft for the current player

            if (cardsLeft[currentPlayerIndex] == 0) {
                // Calculate scores at the end of the round when a player has no cards left
                for (int i = 0; i < players.length; i++) {
                    scores[i] = calculateRoundScore(i);
                }

                // Print the scores after the round
                System.out.println("Scores after Round #" + trickNumber);
                printScoreBoard();

                // Reset cardsLeft for the next round
                for (int i = 0; i < players.length; i++) {
                    cardsLeft[i] = players[i].getHand().size();
                }

                // Increment the trick number
                trickNumber++;
            }
        } else {
            System.out.println("Player" + (currentPlayerIndex + 1) + " passed their turn.");
        }

        currentPlayerIndex = (currentPlayerIndex + 1) % players.length;

        if (cardsPlayed == players.length) {
            int winnerIndex = determineWinner(centerCards, trickNumber);
            System.out.println("*** Player" + (winnerIndex + 1) + " wins Trick #" + trickNumber + " ***");
            currentPlayerIndex = winnerIndex;
            centerCards.clear();
            cardsPlayed = 0;

            Player winner = players[winnerIndex];
            if (trickNumber > 1) {
                promptWinnerForLeadCard(winner);
                cardsPlayed++;
            }
        }
    }
    // Print the final score board at the end of the game
    printScoreBoard();
}

private int calculateRoundScore(int playerIndex) {
    int score = 0;
    Map<String, Integer> rankToValue = new LinkedHashMap<>();
    rankToValue.put("A", 1); // Make Ace the highest
    rankToValue.put("K", 10);
    rankToValue.put("Q", 10);
    rankToValue.put("J", 10);
    rankToValue.put("X", 10); // Assuming X is a ten
    rankToValue.put("9", 9);
    rankToValue.put("8", 8);
    rankToValue.put("7", 7);
    rankToValue.put("6", 6);
    rankToValue.put("5", 5);
    rankToValue.put("4", 4);
    rankToValue.put("3", 3);
    rankToValue.put("2", 2);

    for (Card card : players[playerIndex].getHand()) {
        score += rankToValue.get(card.getRank());
    }

    return score;
}

private void printScoreBoard() {
    System.out.print("Score: ");
    for (int i = 0; i < scores.length; i++) {
        System.out.print("Player" + (i + 1) + " = " + scores[i]);
        if (i < scores.length - 1) {
            System.out.print(" | ");
        }
    }
    System.out.println();
}



    private int determineWinner(List<Card> centerCards, int trickNumber) {
        int winningCardIndex = -1;
        Card winningCard = null;

        // Find the index of the first card with matching suit and rank
        for (int i = 0; i < centerCards.size(); i++) {
            Card card = centerCards.get(i);
            if (card.getSuit().equals(centerCards.get(0).getSuit())
                    && card.getRank().equals(centerCards.get(0).getRank())) {
                winningCardIndex = i;
                winningCard = card;
                break;
            }
        }

        // Compare the remaining cards for their ranks
        for (int i = 0; i < centerCards.size(); i++) {
            Card card = centerCards.get(i);

            if (card != winningCard && (winningCard == null || Card.compareRanks(card, winningCard) > 0)) {
                winningCard = card;
                winningCardIndex = i;
            }
        }

        if (trickNumber != 1) {
            return (currentPlayerIndex + winningCardIndex + 4) % players.length;
        } else {
            return (currentPlayerIndex + winningCardIndex + 3) % players.length;
        }
    }

    private void promptWinnerForLeadCard(Player winner) {
        Scanner scanner = new Scanner(System.in);
        System.out.print(
                "Player" + (currentPlayerIndex + 1) + ", choose a card to be the lead card for the next round: ");
        String input = scanner.nextLine().trim().toUpperCase();

        if (input.isEmpty()) {
            currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
            return;
        }

        for (Card card : winner.getHand()) {
            if (card.toString().equals(input)) {
                if (centerCards.contains(card)) {
                    System.out.println("The selected card is already in the center. Player" + (currentPlayerIndex + 1)
                            + " does not need to play again.");
                    currentPlayerIndex = (currentPlayerIndex + 1) % players.length;
                    return;
                }

                leadCard = card;
                winner.removeCard(card);
                centerCards.add(card);
                currentPlayerIndex = (currentPlayerIndex + 1) % players.length;

                return;
            }
        }

        System.out.println("Invalid card! Choose a card from your hand.");
        promptWinnerForLeadCard(winner); // Recursively ask for a valid card
    }

    private Card getValidCardFromPlayer(Player player) {
        Scanner scanner = new Scanner(System.in);
        Card playedCard = null;
        boolean isValidCard = false;

        while (!isValidCard) {
            System.out.print("Choose a card to play (or 'd' to draw a card, 'p' to pass your turn): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("D")) {
                if (!deck.isEmpty()) {
                    Card drawnCard = deck.drawCard();
                    player.drawCard(drawnCard);
                    System.out.println("You drew a card: " + drawnCard.toString());
                    System.out.println("Your updated hand: " + player.toString());
                    continue;
                } else {
                    System.out.println("No more cards in the deck.");
                }
            } else if (input.equals("P")) {
                System.out.println("You passed your turn.");
                break;
            }

            for (Card card : player.getHand()) {
                if (card.getSuit().equals(centerCards.get(0).getSuit())
                        || card.getRank().equals(centerCards.get(0).getRank())) {
                    if (card.toString().equals(input)) {
                        playedCard = card;
                        isValidCard = true;
                        break;
                    }
                }
            }

            if (!isValidCard) {
                System.out.println(
                        "Invalid card! Choose a card with the same suit or rank as the center card, 'd' to draw a card, or 'p' to pass your turn.");
            }
        }

        return playedCard;
    }

    private void printDeck() {
        System.out.println("Deck: " + deck.toString());
    }

    private void printPlayerHands() {
        for (int i = 0; i < players.length; i++) {
            Player player = players[i];
            System.out.println("Player" + (i + 1) + ": " + player.toString());
        }
    }

    private void printCenterCards() {
        System.out.println("Center: " + centerCards.toString());
    }

    private boolean isGameOver() {
        for (Player player : players) {
            if (!player.getHand().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.startGame();
    }
}
