import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class GameMenu extends JFrame implements ActionListener {
    private JButton newGameButton;
    private JButton resumeGameButton;
    private JButton saveGameButton;
    private JButton resetGameButton;
    private JButton exitButton;

    private ImageIcon backgroundImage;
    private JLabel backgroundLabel;

    private boolean showConfetti;
    private Timer confettiTimer;

    public GameMenu() {
        setTitle("GO BOOM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null); // Set layout manager to null for custom component positioning
        initComponents();
        setPreferredSize(new Dimension(800, 600)); // Set the preferred size of the JFrame
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        loadBackgroundImage();

        newGameButton.addActionListener(this);
        resumeGameButton.addActionListener(this);
        saveGameButton.addActionListener(this);
        resetGameButton.addActionListener(this);
        exitButton.addActionListener(this);

        showConfetti = false;
        confettiTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showConfetti = !showConfetti;
                repaint();
            }
        });
        confettiTimer.start();
    }

    private void initComponents() {
        JPanel contentPane = new JPanel();
        contentPane.setLayout(null);
        contentPane.setPreferredSize(new Dimension(800, 600));
        setContentPane(contentPane);

        newGameButton = createButton("Start New Game");
        resumeGameButton = createButton("Resume Previous Game");
        saveGameButton = createButton("Save Game");
        resetGameButton = createButton("Reset Game");
        exitButton = createButton("Exit Game");

        newGameButton.setBounds(300, 250, 200, 40); // Set the position and size of the button
        resumeGameButton.setBounds(300, 300, 200, 40);
        saveGameButton.setBounds(300, 350, 200, 40);
        resetGameButton.setBounds(300, 400, 200, 40);
        exitButton.setBounds(300, 450, 200, 40);

        contentPane.add(newGameButton);
        contentPane.add(resumeGameButton);
        contentPane.add(saveGameButton);
        contentPane.add(resetGameButton);
        contentPane.add(exitButton);

        newGameButton.addActionListener(this);
        resumeGameButton.addActionListener(this);
        saveGameButton.addActionListener(this);
        resetGameButton.addActionListener(this);
        exitButton.addActionListener(this);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                scaleBackgroundImage();
            }
        });
    }

    private void loadBackgroundImage() {
        String imagePath = "C:\\Users\\User\\Desktop\\OOP game assignment\\Family_Game_Shelf_Go_Boom_card_game_cover.jpg";
        backgroundImage = new ImageIcon(imagePath);
        backgroundLabel = new JLabel(backgroundImage);
        backgroundLabel.setBounds(0, 0, getWidth(), getHeight());

        JPanel contentPane = (JPanel) getContentPane();
        contentPane.add(backgroundLabel);
    }

    private void scaleBackgroundImage() {
        Image image = backgroundImage.getImage();
        if (image != null) {
            Image scaledImage = image.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH);
            backgroundLabel.setIcon(new ImageIcon(scaledImage));
            backgroundLabel.setBounds(0, 0, getWidth(), getHeight());
        }
    }

    private JButton createButton(String label) {
        JButton button = new JButton(label);
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        return button;
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);
        if (showConfetti) {
            for (int i = 0; i < 100; i++) {
                int x = (int) (Math.random() * getWidth());
                int y = (int) (Math.random() * getHeight());
                int size = (int) (Math.random() * 10) + 5;
                Color color = getRandomColor();
                g.setColor(color);
                g.fillRect(x, y, size, size);
            }
        }
    }

    private Color getRandomColor() {
        int r = (int) (Math.random() * 256);
        int g = (int) (Math.random() * 256);
        int b = (int) (Math.random() * 256);
        return new Color(r, g, b);
    }

   @Override
public void actionPerformed(ActionEvent e) {
    if (e.getSource() == newGameButton) {
        // Start a new game
        Game game = new Game();
        game.startGame();
    } else if (e.getSource() == resumeGameButton) {
        // Resume the previous game
        // Implement your logic here to load the game state from a file or any other source
        // and resume the game.
        // For example:
        // Game game = loadGameFromFile();
        // game.playGame();
        System.out.println("Resuming the previous game...");
    } else if (e.getSource() == saveGameButton) {
        // Save the current game state
        // Implement your logic here to save the game state to a file or any other destination.
        // For example:
        // saveGameToFile(game);
        System.out.println("Saving the current game state...");
    } else if (e.getSource() == resetGameButton) {
        // Reset the game
        // Implement your logic here to reset the game state.
        // For example:
        // game.resetGame();
        System.out.println("Resetting the game...");
    } else if (e.getSource() == exitButton) {
        // Exit the game
        System.exit(0);
    }
}


    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(GameMenu::new);
    }
}
