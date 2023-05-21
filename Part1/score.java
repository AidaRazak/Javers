public class score {
    public static void main(String[] args) {
        int numPlayers = 4;
        int[] scores = new int[numPlayers];

        // Set initial scores for all players to 0
        for (int i = 0; i < numPlayers; i++) {
            scores[i] = 0;
        }

        // Print the scoreboard
        System.out.print("\nScore  : ");
        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Player" + i + " = " + scores[i-1]);

            if (i < numPlayers) {
                System.out.print(" | ");
            }
        }
    }
}

// not used in final output 
