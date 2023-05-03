public class Main {

    public Main(String Deck){
    this.deck = Deck;


    // create a new deck
    Deck deck = new Deck();

    // print the unshuffled deck
    System.out.println(deck);

    // shuffle the deck
    deck.shuffle();

    // print the shuffled deck
    System.out.println(deck);

    // deal a card
    String card = deck.deal();
    System.out.println(card);

        // deal all the cards
        while (true) {
            card = deck.deal();
            if (card != null) {
                System.out.println(card);
            } 
            else {
                break;
            }
        }
    }




}