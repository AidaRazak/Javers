import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


public class Game implements Serializable {
    private Deck deck;
    private Player[] players;
    private Card leadCard;
    private ArrayList<Card> centerCards;
    private int currentPlayerIndex;
    private int[] scores;
    static int gameCount = 0;
    



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
        gameCount++;
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
        System.out.println("\nThe first lead card is: " + leadCard.toString());
        int firstPlayerIndex = determineFirstPlayerIndex(leadCard.getRank());

        System.out.println("\nPlayer " + (firstPlayerIndex + 1) + " goes first.");
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
            throw new IllegalArgumentException("\nInvalid lead card rank: " + leadRank);
        }
    }

    private int cardsPlayed = 0;

    public void resetGame() {
    for (Player player : players) {
        player.clearHand();
    }

    centerCards.clear();

    for (int i = 0; i < scores.length; i++) {
        scores[i] = 0;
    }

    currentPlayerIndex = 0;

    // Reset trickNumber to 1
    int trickNumber = 1;
}


    public void playGame() {
        System.out.println("\n----- Game #" + gameCount + " -----");
        int trickNumber = 1;
        int cardsPlayed = 0;
        int[] scores = new int[players.length];
        

        while (!isGameOver()) {
            Player currentPlayer = players[currentPlayerIndex];
            System.out.println("\nTrick #" + trickNumber);
            printPlayerHands();
            printCenterCards();
            printDeck();

            System.out.println("Turn: Player" + (currentPlayerIndex + 1));

            // Print the updated score board
            System.out.print("Score: ");
            for (int i = 0; i < scores.length; i++) {
                System.out.print("Player" + (i + 1) + " = " + scores[0]);
                if (i < scores.length - 1) {
                    System.out.print(" | ");
                }
            }
            System.out.println();

            Card playedCard = getValidCardFromPlayer(currentPlayer);

            if (playedCard != null) {
                System.out.println("\nPlayer" + (currentPlayerIndex + 1) + " plays: " + playedCard.toString());
                centerCards.add(playedCard);
                currentPlayer.removeCard(playedCard);
                cardsPlayed++;
            } else {
                System.out.println("Player" + (currentPlayerIndex + 1) + " passed their turn.");
                cardsPlayed++;
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % players.length;

            if (cardsPlayed == players.length) {
                int winnerIndex = determineWinner(centerCards, trickNumber);
                System.out.println("*** Player" + (winnerIndex + 1) + " wins Trick #" + trickNumber + " ***");
                trickNumber++;
                currentPlayerIndex = winnerIndex;
                centerCards.clear();
                cardsPlayed = 0;

                Player winner = players[winnerIndex];
                if (trickNumber > 1) {
                    promptWinnerForLeadCard(winner);
                    cardsPlayed++;
                }
                //scores[winnerIndex] += 1; // Increment the score for the winner

            }

            // Check if a player has finished all their cards after the trick is over
            for (int i = 0; i < players.length; i++) {
                Player player = players[i];
                if (player.getHand().isEmpty()) {
                    System.out.println("\nPlayer" + (i + 1) + " has finished all their cards!");

                    // Calculate the round score for the player
                    int roundScore = calculateRoundScore(i);
                    System.out.println("Round Score for Player" + (i + 1) + ": " + roundScore);
                    scores[i] += roundScore; // Update the player's total score

                    System.out.println();
                    int winnerIndex = determineGameWinner(scores);
                    System.out.println("\nGame over! Player" + (i + 1) + " wins the game!");
                    System.out.println();
                }
            }
        }
    
            System.out.print("Score: ");
            for (int i = 0; i < players.length; i++) {
                Player player = players[i];
                int remainingPoints = calculateRoundScore(i);
                System.out.print("Player" + (i + 1) + " = " + remainingPoints);
                if (i < scores.length - 1) {
                    System.out.print(" | ");
                } 
            }

            // Ask if players want to reset the game
            System.out.println("\nDo you want to reset the game? (Y/N)");
            Scanner scanner = new Scanner(System.in);
            String resetInput = scanner.nextLine().trim().toUpperCase();
            if (resetInput.equals("Y")) {
                resetGame(); // Reset the game
                startGame(); // Start a new game
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

    private void exitGame() {
       
        System.out.println("Exiting the game...");

        System.exit(0); // Terminate the program
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
            System.out.print(
                    "\nChoose a card to play (or 'd' to draw a card, 'p' to pass your turn, 's' to save your progress, 'e' exit): ");
            String input = scanner.nextLine().trim().toUpperCase();

            if (input.equals("D")) {
                if (!deck.isEmpty()) {
                    Card drawnCard = deck.drawCard();
                    player.drawCard(drawnCard);
                    System.out.println("\nYou drew a card: " + drawnCard.toString());
                    System.out.println("Your updated hand: " + player.toString());
                    continue;
                } else {
                    System.out.println("No more cards in the deck.");
                }
            } else if (input.equals("P")) {
                System.out.println("You passed your turn.");
                cardsPlayed++;
                break;
            }

            else if (input.equals("S")) {
                System.out.println("Enter the name of the save file: ");
                String fileName = scanner.nextLine();
                saveGame(fileName);
                System.out.println("Game saved successfully!");

            } 

            else if (input.equals("E")) {
                exitGame();
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
            if (player.getHand().isEmpty()) {
                return true;

            }
        }
        return false;
    }

    private int determineGameWinner(int[] scores) {
        int minScore = scores[0];
        int winnerIndex = 0;

        for (int i = 1; i < scores.length; i++) {
            if (scores[i] < minScore) {
                minScore = scores[i];
                winnerIndex = i;
            }
        }

        return winnerIndex;
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

    public void saveGame(String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeObject(this);
        } catch (IOException e) {
            System.out.println("Error saving the game: " + e.getMessage());
        }
    }

    public static Game loadGame(String fileName) {
    Game game = null;
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
        game = (Game) inputStream.readObject();
        game.gameCount++; // Increment gameNumber
    } catch (IOException | ClassNotFoundException e) {
        System.out.println("Error loading the game: " + e.getMessage());
    }
    return game;
}


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Game game = null;

        System.out.println("Welcome to Go Boom!");

        while (game == null) {
            System.out.println("Please select an option:");
            System.out.println("1. Start a new game");
            System.out.println("2. Load a saved game");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline character

            switch (choice) {
                case 1:
                    System.out.println("Starting a new game...");
                    game = new Game();
                    game.startGame();
                    break;
                case 2:
                    System.out.println("Enter the name of the save file: ");
                    String fileName = scanner.nextLine();
                    game = Game.loadGame(fileName);
                    if (game != null) {
                        game.playGame();
                    }
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    
}
