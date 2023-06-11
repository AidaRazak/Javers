import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameMenu extends JFrame implements ActionListener {
    // GUI components
    private JButton newGameButton;
    private JButton resumeGameButton;
    private JButton saveGameButton;
    private JButton resetGameButton;
    private JButton exitButton;

    public GameMenu() {
        // Set the window title
        setTitle("Game Menu");

        // Create the buttons
        newGameButton = new JButton("Start New Game");
        resumeGameButton = new JButton("Resume Previous Game");
        saveGameButton = new JButton("Save Game");
        resetGameButton = new JButton("Reset Game");
        exitButton = new JButton("Exit Game");

        // Set the layout manager
        setLayout(new FlowLayout());

        // Add buttons to the frame
        add(newGameButton);
        add(resumeGameButton);
        add(saveGameButton);
        add(resetGameButton);
        add(exitButton);

        // Register ActionListener for buttons
        newGameButton.addActionListener(this);
        resumeGameButton.addActionListener(this);
        saveGameButton.addActionListener(this);
        resetGameButton.addActionListener(this);
        exitButton.addActionListener(this);

        // Set frame properties
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
            // Handle new game button click
            // Call the startGame() method or any other logic to start a new game
            // For example:
            Game game = new Game();
            game.startGame();
        } else if (e.getSource() == resumeGameButton) {
            // Handle resume game button click
            // Call the logic to resume a previous game
            // For example:
            Game game = Game.loadGame("saved_game.ser");
            if (game != null) {
                game.playGame();
            } else {
                System.out.println("No saved game found.");
            }
        } else if (e.getSource() == saveGameButton) {
            // Handle save game button click
            // Call the logic to save the game
            // For example:
            Game game = getCurrentGame();
            if (game != null) {
                game.saveGame("saved_game.ser");
                System.out.println("Game saved.");
            } else {
                System.out.println("No active game to save.");
            }
        } else if (e.getSource() == resetGameButton) {
            // Handle reset game button click
            // Call the logic to reset the game
            // For example:
            Game game = getCurrentGame();
            if (game != null) {
                //game.resetGame();
                System.out.println("Game reset.");
            } else {
                System.out.println("No active game to reset.");
            }
        } else if (e.getSource() == exitButton) {
            // Handle exit button click
            // Call the logic to handle exiting the game
            // For example:
            int result = JOptionPane.showConfirmDialog(this, "Are you sure you want to exit the game?",
                    "Exit Game", JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }

    private Game getCurrentGame() {
        // Add your logic to retrieve the current game instance
        // For example, if the game is stored in a variable called 'currentGame':
        // return currentGame;
        return null; // Placeholder return statement
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameMenu::new);
    }
}
