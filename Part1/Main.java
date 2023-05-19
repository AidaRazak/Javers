import java.util.List;

public class Main {

    public static void main(String[] args) {
        // Create a new deck
        Deck deck = new Deck();

        // Shuffle the deck
        deck.shuffle();

        // Deal 7 cards to 4 players
        System.out.println("\nTrick #1");
        Deal7 deal = new Deal7();
        deal.printPlayers();

        // Deal a card
        String card = deck.deal();
        // System.out.println("\nCenter card: " + card);

        // Determine the first player
        Player firstPlayer = Player.determineFirstPlayer(card);

        // Play the game starting from the first player
        String leadSuit = card.substring(0, 1);
        String leadRank = card.substring(1);
        Player currentPlayer = firstPlayer;

        List<Player> players = deal.getPlayers();

        boolean isFirstRound = true;

        int numPlayers = players.size();
        int[] scores = new int[numPlayers];

        while (!players.isEmpty()) {
            // Print the trick and the deck before each player's turn
            if (!isFirstRound) {
                System.out.println("\nTrick:");
                deal.printPlayers();
            }
            // Print the current center card
            // Print the current center cards
            System.out.println("Center cards " + deck.getCenterCards());
            System.out.println("Deck: " + deck);

            // Call the score class
            score.main(args);

            // Get the card played by the current player
            String playedCard = currentPlayer.playCard(leadSuit, leadRank);
            System.out.println(currentPlayer.getName() + " played: " + playedCard);

            // Update the center card to the played card
            deck.updateCenterCard(playedCard);
            leadSuit = playedCard.substring(0, 1);
            leadRank = playedCard.substring(1);

            // Remove the played card from the player's hand
            currentPlayer.removeCard(playedCard);

            // Handle the played card and update the game state

            currentPlayer = getNextPlayer(currentPlayer, players);
            isFirstRound = false;
        }
        System.out.println("Turn: " + firstPlayer.getName());
        // Print the deck after dealing a card
        // System.out.println("\nDeck after dealing a card:");
        // System.out.println(deck);
    }

    private static Player getNextPlayer(Player currentPlayer, List<Player> players) {
        int currentIndex = players.indexOf(currentPlayer);
        int nextIndex = (currentIndex + 1) % players.size();
        return players.get(nextIndex);
    }
}
