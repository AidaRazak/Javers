import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Deck deck = new Deck();
        List<List<String>> playerHands = deck.dealToPlayers(4, 6);
        List<String> centerCards = deck.getCenterCards();

        System.out.println("Player1's cards: " + playerHands.get(0));
        System.out.println("Player2's cards: " + playerHands.get(1));
        System.out.println("Player3's cards: " + playerHands.get(2));
        System.out.println("Player4's cards: " + playerHands.get(3));
        System.out.println("Center cards: " + centerCards);
        System.out.println("Deck: " + deck);

        Scanner scanner = new Scanner(System.in);
        int currentPlayerIndex = determineFirstPlayerIndex(centerCards.get(0));
        int numPlayers = playerHands.size();
        int trickNumber = 1;
        int[] trickScores = new int[numPlayers];
        int roundWinnerIndex = -1;
        int roundNumber = 1;

        while (roundWinnerIndex == -1) {
            int cardsPlayed = 0; // Number of cards played in the current trick

            while (cardsPlayed < numPlayers) {
                String leadRank = centerCards.get(0).substring(0, 1);
                String inputCard = "";

                do {
                    System.out.println("\nTrick #" + trickNumber);
                    System.out.println("Player1: " + playerHands.get(0));
                    System.out.println("Player2: " + playerHands.get(1));
                    System.out.println("Player3: " + playerHands.get(2));
                    System.out.println("Player4: " + playerHands.get(3));
                    System.out.println("Center: " + centerCards);
                    System.out.println("Deck: " + deck);
                    System.out.println("Score: Player1 = " + trickScores[0] + ", Player2 = " + trickScores[1] +
                            ", Player3 = " + trickScores[2] + ", Player4 = " + trickScores[3]);

                    if (currentPlayerIndex == 0) {
                        System.out.print("Player1, enter a card: ");
                        inputCard = scanner.nextLine();
                    } else if (currentPlayerIndex == 1) {
                        System.out.print("Player2, enter a card: ");
                        inputCard = scanner.nextLine();
                    } else if (currentPlayerIndex == 2) {
                        System.out.print("Player3, enter a card: ");
                        inputCard = scanner.nextLine();
                    } else if (currentPlayerIndex == 3) {
                        System.out.print("Player4, enter a card: ");
                        inputCard = scanner.nextLine();
                    }

                    if (!playerHands.get(currentPlayerIndex).contains(inputCard)) {
                        System.out.println("Invalid card. Please enter a card from your hand.");
                    }
                } while (!playerHands.get(currentPlayerIndex).contains(inputCard));

                playerHands.get(currentPlayerIndex).remove(inputCard);
                centerCards.add(inputCard);
                cardsPlayed++;
                currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers;

                if (cardsPlayed == numPlayers) {
                    int winnerIndex = determineWinnerIndex(centerCards, leadRank);
                    trickScores[winnerIndex]++;
                    System.out.println("\n*** Player" + (winnerIndex + 1) + " wins Trick #" + trickNumber);
                    trickNumber++;
                    centerCards.clear();
                }
            }

            if (deck.isEmpty()) {
                roundWinnerIndex = determineWinnerIndex(trickScores);
                System.out.println("\n*** Round #" + roundNumber + " winner: Player" + (roundWinnerIndex + 1) +
                        " with " + trickScores[roundWinnerIndex] + " tricks won ***");
                roundNumber++;
            }
        }
    }

    public static int determineFirstPlayerIndex(String card) {
        Map<String, Integer> rankToPlayerIndex = new HashMap<>();
        rankToPlayerIndex.put("A", 0);
        rankToPlayerIndex.put("2", 1);
        rankToPlayerIndex.put("3", 2);
        rankToPlayerIndex.put("4", 3);
        rankToPlayerIndex.put("5", 0);
        rankToPlayerIndex.put("6", 1);
        rankToPlayerIndex.put("7", 2);
        rankToPlayerIndex.put("8", 3);
        rankToPlayerIndex.put("9", 0);
        rankToPlayerIndex.put("10", 1);
        rankToPlayerIndex.put("J", 2);
        rankToPlayerIndex.put("Q", 3);
        rankToPlayerIndex.put("K", 0);

        String rank = card.substring(0, 1);
        return rankToPlayerIndex.get(rank);
    }

    public static int determineWinnerIndex(List<String> cards, String leadRank) {
        Map<String, Integer> rankToPlayerIndex = new HashMap<>();
        rankToPlayerIndex.put("A", 0);
        rankToPlayerIndex.put("2", 1);
        rankToPlayerIndex.put("3", 2);
        rankToPlayerIndex.put("4", 3);
        rankToPlayerIndex.put("5", 0);
        rankToPlayerIndex.put("6", 1);
        rankToPlayerIndex.put("7", 2);
        rankToPlayerIndex.put("8", 3);
        rankToPlayerIndex.put("9", 0);
        rankToPlayerIndex.put("10", 1);
        rankToPlayerIndex.put("J", 2);
        rankToPlayerIndex.put("Q", 3);
        rankToPlayerIndex.put("K", 0);

        int maxRankIndex = -1;
        int maxRankValue = -1;

        for (int i = 0; i < cards.size(); i++) {
            String card = cards.get(i);
            String rank = card.substring(0, 1);
            if (rank.equals(leadRank) && rankToPlayerIndex.get(rank) > maxRankValue) {
                maxRankIndex = i;
                maxRankValue = rankToPlayerIndex.get(rank);
            }
        }

        return maxRankIndex % 4;
    }

    public static int determineWinnerIndex(int[] trickScores) {
        int maxScore = -1;
        int winnerIndex = -1;

        for (int i = 0; i < trickScores.length; i++) {
            if (trickScores[i] > maxScore) {
                maxScore = trickScores[i];
                winnerIndex = i;
            }
        }

        return winnerIndex;
    }
}
