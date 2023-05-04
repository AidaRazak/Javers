public class Main {

    public static void main(String[] args) {
        // Create a new deck
        Deck deck = new Deck();

        // Print the deck before shuffling
        System.out.println("Deck before shuffling:");
        System.out.println(deck);

        // Shuffle the deck
        deck.shuffle();

        // Print the deck after shuffling
        System.out.println("\nDeck after shuffling:");
        System.out.println(deck);

        // Deal a card
        String card = deck.deal();
        System.out.println("\nDealt card: " + card);

        // Print the deck after dealing a card
        System.out.println("\nDeck after dealing a card:");
        System.out.println(deck);
    }
}
