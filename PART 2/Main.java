import java.util.*;

public class Main {
    public static class RoundWinner {
        private int playerIndex;
        private String winningCard;

        public RoundWinner(int playerIndex, String winningCard) {
            this.playerIndex = playerIndex;
            this.winningCard = winningCard;
        }

        public int getPlayerIndex() {
            return playerIndex;
        }

        public String getWinningCard() {
            return winningCard;
        }
    }



    public static void main(String[] args) {
        Deck deck = new Deck();
        List<List<String>> playerHands = deck.dealToPlayers(4, 7);
        List<String> centerCards = deck.getCenterCards();

        System.out.println("Player1's cards: " + playerHands.get(0));
        System.out.println("Player2's cards: " + playerHands.get(1));
        System.out.println("Player3's cards: " + playerHands.get(2));
        System.out.println("Player4's cards: " + playerHands.get(3));
        System.out.println("Center cards: " + centerCards);
        System.out.println("Deck: " + deck);

        try (Scanner scanner = new Scanner(System.in)) {
            int currentPlayerIndex = determineFirstPlayerIndex(centerCards.get(0));
            int numPlayers = playerHands.size();
            int trickNumber = 1;
            String winningCard = "";
            int nextCenterCardPlayerIndex = -1; // Initialize with an invalid value

            while (nextCenterCardPlayerIndex != currentPlayerIndex) {
                String leadRank = winningCard.isEmpty() ? "" : winningCard.substring(0, 1);
                String inputCard = "";

                do {
                    System.out.println("\nTrick #" + trickNumber);
                    for (int i = 0; i < numPlayers; i++) {
                        System.out.println("Player" + (i + 1) + ": " + playerHands.get(i));
                    }
                    System.out.println("Center: " + centerCards);
                    System.out.println("Deck: " + deck);
                    System.out.println("Score: Player1 = 0 | Player2 = 0 | Player3 = 0 | Player4 = 0");
                    System.out.println("Turn: Player" + (currentPlayerIndex + 1));

                    System.out.print("> ");
                    inputCard = scanner.nextLine();

                    if (inputCard.equalsIgnoreCase("d")) {
                        if (!deck.isEmpty()) {
                            String drawnCard = deck.drawCard();
                            playerHands.get(currentPlayerIndex).add(drawnCard);
                            System.out.println("Player" + (currentPlayerIndex + 1) + " drew a card: " + drawnCard);
                        } else {
                            System.out.println("No more cards in the deck.");
                        }
                    }
                } while (!isValidCard(inputCard, playerHands.get(currentPlayerIndex), leadRank, centerCards.isEmpty() ? "" : centerCards.get(0)));

                if (!inputCard.equalsIgnoreCase("d")) {
                    playerHands.get(currentPlayerIndex).remove(inputCard);
                    centerCards.add(inputCard);
                }

                currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers;
                if (currentPlayerIndex == 0) {
                    trickNumber++;
                }

                if (centerCards.size() == 5) {
                    int roundWinnerIndex = determineWinner(centerCards);
                    System.out.println("Round " + (trickNumber - 1) + " winner: Player" + (roundWinnerIndex + 1));
                    currentPlayerIndex = roundWinnerIndex;
                    winningCard = centerCards.get(roundWinnerIndex);
                    centerCards.remove(centerCards.size() - 1);

                    nextCenterCardPlayerIndex = currentPlayerIndex; // Set the next center card player index to the current player index

                    if (trickNumber != 1) {
                        System.out.println("Player" + (nextCenterCardPlayerIndex + 1) + ", choose the lead suit and rank for round :" + trickNumber);
                        String nextCenterCard = scanner.nextLine();

                        while (!isValidCard(nextCenterCard, playerHands.get(nextCenterCardPlayerIndex), "", "")) {
                            System.out.println("Invalid card. Try again.");
                            System.out.println("Player" + (nextCenterCardPlayerIndex + 1) + ", choose the lead suit and rank for round :" + trickNumber);
                            nextCenterCard = scanner.nextLine();
                        }

                        centerCards.clear();
                        centerCards.add(nextCenterCard);
                    } else {
                        System.out.println("Player" + (nextCenterCardPlayerIndex + 1) + ", choose the next center card:");
                        String nextCenterCard = scanner.nextLine();

                        while (!isValidCard(nextCenterCard, playerHands.get(nextCenterCardPlayerIndex), "", "")) {
                            System.out.println("Invalid card. Try again.");
                            System.out.println("Player" + (nextCenterCardPlayerIndex + 1) + ", choose the next center card:");
                            nextCenterCard = scanner.nextLine();
                        }

                        centerCards.add(nextCenterCard);
                    }

                    System.out.print("Press any key to start the next round...");
                    scanner.nextLine();
                }
            }
        }
        System.out.println("\nGame over!");
    }

    private static int determineFirstPlayerIndex(String leadCard) {
        char rank = leadCard.charAt(0);

        Map<Character, Integer> rankToPlayerIndex = new HashMap<>();
        rankToPlayerIndex.put('A', 0);
        rankToPlayerIndex.put('5', 0);
        rankToPlayerIndex.put('9', 0);
        rankToPlayerIndex.put('K', 0);
        rankToPlayerIndex.put('2', 1);
        rankToPlayerIndex.put('6', 1);
        rankToPlayerIndex.put('X', 1);
        rankToPlayerIndex.put('3', 2);
        rankToPlayerIndex.put('7', 2);
        rankToPlayerIndex.put('J', 2);
        rankToPlayerIndex.put('4', 3);
        rankToPlayerIndex.put('8', 3);
        rankToPlayerIndex.put('Q', 3);

        if (rankToPlayerIndex.containsKey(rank)) {
            return rankToPlayerIndex.get(rank);
        } else {
            throw new IllegalArgumentException("Invalid lead card: " + leadCard);
        }
    }

    private static boolean isValidCard(String card, List<String> hand, String leadRank, String centerCard) {
        if (!hand.contains(card)) {
            System.out.println("Invalid card. Try again.");
            return false;
        }

        if (centerCard.length() < 2) {
            System.out.println("Invalid center card. Try again.");
            return false;
        }

        String cardRank = card.substring(0, 1);
        String cardSuit = card.substring(1);

        String centerRank = centerCard.substring(0, 1);
        String centerSuit = centerCard.substring(1);

        if (leadRank.isEmpty()) {
            // Trick 1
            if (!cardRank.equals(centerRank) && !cardSuit.equals(centerSuit)) {
                System.out.println("Invalid card. Must play a card of the lead rank: " + centerRank +
                        " or same suit: " + centerSuit);
                return false;
            }
        } else {
            // Trick 2
            if (!cardRank.equals(leadRank)) {
                System.out.println("Invalid card. Must play a card of the lead rank: " + leadRank);
                return false;
            }
        }

        return true;
    }

    private static int determineWinner(List<String> centerCards) {
        String leadSuit = centerCards.get(0).substring(0, 1);
        String highestCard = centerCards.get(0);
        int winningPlayerIndex = 0;

        for (int i = 1; i < centerCards.size(); i++) {
            String currentCard = centerCards.get(i);
            String currentSuit = currentCard.substring(0, 1);

            if (currentSuit.equals(leadSuit) && compareCards(currentCard, highestCard) > 0) {
                highestCard = currentCard;
                winningPlayerIndex = i;
            }
        }

        System.out.println("\n*** Player" + (winningPlayerIndex + 1) + " wins the trick! ***\n");

        return winningPlayerIndex;
    }

    private static int compareCards(String card1, String card2) {
        String rank1 = card1.substring(0, 1);
        String suit1 = card1.substring(1);
        String rank2 = card2.substring(0, 1);
        String suit2 = card2.substring(1);

        Map<String, Integer> rankToValue = new LinkedHashMap<>();
        rankToValue.put("A", 13);
        rankToValue.put("K", 12);
        rankToValue.put("Q", 11);
        rankToValue.put("J", 10);
        rankToValue.put("X", 9);
        rankToValue.put("9", 8);
        rankToValue.put("8", 7);
        rankToValue.put("7", 6);
        rankToValue.put("6", 5);
        rankToValue.put("5", 4);
        rankToValue.put("4", 3);
        rankToValue.put("3", 2);
        rankToValue.put("2", 1);

        Map<String, Integer> suitToValue = new LinkedHashMap<>();
        suitToValue.put("c", 4);
        suitToValue.put("d", 3);
        suitToValue.put("h", 2);
        suitToValue.put("s", 1);

        int rankValue1 = rankToValue.get(rank1);
        int rankValue2 = rankToValue.get(rank2);
        int suitValue1 = suitToValue.get(suit1);
        int suitValue2 = suitToValue.get(suit2);

        if (rankValue1 != rankValue2) {
            return Integer.compare(rankValue2, rankValue1);
        } else {
            if (rank1.equals("A")) {
                return 1;
            } else if (rank1.equals("K")) {
                return Integer.compare(suitValue2, suitValue1);
            } else {
                return Integer.compare(suitValue1, suitValue2);
            }
        }
    }
}
