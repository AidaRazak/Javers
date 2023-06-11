import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GUI extends JFrame {
    private JTextArea centerCardsTextArea;
    private Game game; // Reference to the Game instance

    public GUI(Game game) {
        this.game = game; // Set the reference to the Game instance
        initializeGUI();
    }

    private void initializeGUI() {
        setTitle("Card Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        centerCardsTextArea = new JTextArea();
        centerCardsTextArea.setEditable(false);

        JScrollPane scrollPane = new JScrollPane(centerCardsTextArea);
        add(scrollPane, BorderLayout.CENTER);

        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void updateCenterCards() {
        ArrayList<Card> centerCards = game.getCenterCards(); // Retrieve center cards from the Game instance
        centerCardsTextArea.setText("");
        for (Card card : centerCards) {
            centerCardsTextArea.append(card.toString() + "\n");
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        GUI gameGUI = new GUI(game);
        game.startGame();
        gameGUI.updateCenterCards();
    }
}
