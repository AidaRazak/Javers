import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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

        Scanner scanner = new Scanner(System.in);
        int currentPlayerIndex = determineFirstPlayerIndex(centerCards.get(0));
        int numPlayers = playerHands.size();
        int trickNumber = 1;

        while (!playerHands.get(currentPlayerIndex).isEmpty()) {
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
            } while (!isValidCard(inputCard, playerHands.get(currentPlayerIndex), leadRank, centerCards.get(0)));

            if (!inputCard.equalsIgnoreCase("d")) {
                playerHands.get(currentPlayerIndex).remove(inputCard);
                centerCards.add(inputCard);
            }

            currentPlayerIndex = (currentPlayerIndex + 1) % numPlayers;
            if (currentPlayerIndex == 0) {
                trickNumber++;
            }

            if (centerCards.size() == 5) {
                RoundWinner roundWinner = determineRoundWinner(centerCards, currentPlayerIndex, numPlayers);
                System.out.println("Round " + (trickNumber-1) + " winner: Player" + (roundWinner.getPlayerIndex()+1 ));
                currentPlayerIndex = roundWinner.getPlayerIndex();
                centerCards.clear();
                centerCards.add(roundWinner.getWinningCard());

                // Prompt for user input before starting the next round
                System.out.print("Press any key to start the next round...");
                scanner.nextLine();
            }
        }

        System.out.println("\nGame over!");
    }

    private static int determineFirstPlayerIndex(String leadCard) {
        char rank = leadCard.charAt(0);

        if (rank == 'A' || rank == '5' || rank == '9' || rank == 'K') {
            return 0;
        } else if (rank == '2' || rank == '6' || rank == 'X') {
            return 1;
        } else if (rank == '3' || rank == '7' || rank == 'J') {
            return 2;
        } else if (rank == '4' || rank == '8' || rank == 'Q') {
            return 3;
        } else {
            throw new IllegalArgumentException("Invalid lead card: " + leadCard);
        }
    }

    private static boolean isValidCard(String card, List<String> hand, String leadRank, String centerCard) {
        if (!hand.contains(card)) {
            System.out.println("Invalid card. Try again.");
            return false;
        }

        String cardRank = card.substring(0, 1);
        String cardSuit = card.substring(1);

        String centerRank = centerCard.substring(0, 1);
        String centerSuit = centerCard.substring(1);

        if (!cardRank.equals(centerRank) && !cardSuit.equals(centerSuit)) {
            System.out.println("Invalid card. Must play a card of the lead rank: " + centerRank +
                    " or same suit: " + centerSuit);
            return false;
        }

        return true;
    }

    private static RoundWinner determineRoundWinner(List<String> centerCards, int startingPlayerIndex, int numPlayers) {
        int winningPlayerIndex = -1;
        String winningCard = null;

        for (int i = 0; i < numPlayers; i++) {
            int currentIndex = (startingPlayerIndex + i) % numPlayers;
            String currentCard = centerCards.get(currentIndex);

            if (winningCard == null || compareCards(currentCard, winningCard) > 0) 
            {
                winningPlayerIndex = currentIndex;
                winningCard = currentCard;
            }
        }

        return new RoundWinner(winningPlayerIndex, winningCard);
    }

    private static int compareCards(String card1, String card2) {
        String rank1 = card1.substring(0, 1);
        String suit1 = card1.substring(1);
        String rank2 = card2.substring(0, 1);
        String suit2 = card2.substring(1);
    
        String[] ranks = {"A", "K", "Q", "J", "X", "9", "8", "7", "6", "5", "4", "3", "2"};
        String[] suits = {"c", "d", "h", "s"};
    
        int rankIndex1 = -1;
        int rankIndex2 = -1;
        int suitIndex1 = -1;
        int suitIndex2 = -1;
    
        for (int i = 0; i < ranks.length; i++) {
            if (ranks[i].equals(rank1)) {
                rankIndex1 = i;
            }
            if (ranks[i].equals(rank2)) {
                rankIndex2 = i;
            }
        }
    
        for (int i = 0; i < suits.length; i++) {
            if (suits[i].equals(suit1)) {
                suitIndex1 = i;
            }
            if (suits[i].equals(suit2)) {
                suitIndex2 = i;
            }
        }
    
        if (rankIndex1 != rankIndex2) {
            return Integer.compare(rankIndex2, rankIndex1);
        } else {
            if (rank1.equals("A")) {
                return 1; // Card 1 is an Ace, so Card 1 wins
            } else if (rank2.equals("A")) {
                return -1; // Card 2 is an Ace, so Card 2 wins
            } else {
                return Integer.compare(suitIndex2, suitIndex1);
            }
        }
    }
}
