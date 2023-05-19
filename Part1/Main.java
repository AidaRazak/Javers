import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Create a new deck
        Deck deck = new Deck();

        // Shuffle the deck
        deck.shuffle();

        // Deal 7 cards to 4 players
        System.out.println("\nTrick #1");
        List<List<String>> playerHands = deck.dealToPlayers(4, 7);

        // Create players and assign them their respective hands
        Player player1 = new Player("Player1");
        player1.setCards(playerHands.get(0));
        Player player2 = new Player("Player2");
        player2.setCards(playerHands.get(1));
        Player player3 = new Player("Player3");
        player3.setCards(playerHands.get(2));
        Player player4 = new Player("Player4");
        player4.setCards(playerHands.get(3));

        // Print the players' hands
        player1.printCards();
        player2.printCards();
        player3.printCards();
        player4.printCards();

        // Deal a card
        String card = deck.deal();

        // Update the lead card to the correct format
        String leadCard = card.substring(0, 1) + card.substring(1).toLowerCase();

        // Determine the first player
        Player firstPlayer = Player.determineFirstPlayer(leadCard);

        // Play the game starting from the first player
        String leadSuit = leadCard.substring(0, 1);
        String leadRank = leadCard.substring(1);
        Player currentPlayer = firstPlayer;

        boolean isFirstRound = true;

        int numPlayers = 4;
        int[] scores = new int[numPlayers];

        while (true) {
            // Print the trick and the deck before each player's turn
            if (!isFirstRound) {
                System.out.println("\nTrick:");
                player1.printCards();
                player2.printCards();
                player3.printCards();
                player4.printCards();
            }

            // Print the current center cards
            System.out.println("Center cards: " + deck.getCenterCards());
            System.out.println("Deck: " + deck);

            // Get the card played by the current player
            String playedCard = currentPlayer.playCard(leadSuit, leadRank, deck.getCenterCards());
            System.out.println(currentPlayer.getName() + " played: " + playedCard);

            // Update the center cards with the played card
            deck.updateCenterCards(playedCard);
            leadSuit = playedCard.substring(0, 1);
            leadRank = playedCard.substring(1);

            // Remove the played card from the player's hand
            currentPlayer.removeCard(playedCard);

            // Handle the played card and update the game state

            currentPlayer = getNextPlayer(currentPlayer, player1, player2, player3, player4);
            isFirstRound = false;
        }
    }

    private static Player getNextPlayer(Player currentPlayer, Player player1, Player player2, Player player3, Player player4) {
        if (currentPlayer.equals(player1))
            return player2;
        else if (currentPlayer.equals(player2))
            return player3;
        else if (currentPlayer.equals(player3))
            return player4;
        else
            return player1;
    }
}
