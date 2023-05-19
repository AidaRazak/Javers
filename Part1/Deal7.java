// import java.util.ArrayList;
// import java.util.List;

// public class Deal7 {
//     private Deck deck;
//     private List<List<String>> players;

//     public Deal7() {
//         deck = new Deck();
//         players = new ArrayList<>();
//         for (int i = 0; i < 4; i++) {
//             players.add(new ArrayList<>());
//         }
//         dealCards();
//     }

//     private void dealCards() {
//         deck.shuffle();
//         for (int i = 0; i < 7; i++) {
//             for (int j = 0; j < 4; j++) {
//                 String card = deck.deal();
//                 players.get(j).add(card);
//             }
//         }
//     }

//     public void printPlayers() {
//         for (int i = 0; i < 4; i++) {
//             System.out.println("Player" + (i + 1) + ": " + players.get(i));
//         }
//     }

//     public List<Player> getPlayers() {
//         List<Player> playerList = new ArrayList<>();
//         for (List<String> playerCards : players) {
//             Player player = new Player("Player" + (playerList.size() + 1));
//             for (String card : playerCards) {
//                 player.addCard(card);
//             }
//             playerList.add(player);
//         }
//         return playerList;
//     }
// }