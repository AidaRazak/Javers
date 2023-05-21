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
