import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

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
    private Game game;

    public GameMenu() {
        setTitle("GO BOOM");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null); 
        initComponents();
        setPreferredSize(new Dimension(800, 600)); 
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        loadBackgroundImage();
        game = new Game();

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

        newGameButton.setBounds(300, 210, 200, 40); 
        resumeGameButton.setBounds(300, 260, 200, 40);
        saveGameButton.setBounds(300, 310, 200, 40);
        resetGameButton.setBounds(300, 360, 200, 40);
        exitButton.setBounds(300, 410, 200, 40);

   
        newGameButton.setBackground(Color.PINK);
        resumeGameButton.setBackground(Color.PINK);
        saveGameButton.setBackground(Color.PINK);
        resetGameButton.setBackground(Color.PINK);
        exitButton.setBackground(Color.PINK);

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
        String imagePath = "C:\\Users\\User\\Desktop\\OOP game assignment\\welcome.jpg";
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
            backgroundLabel.setBounds(-8, 0, getWidth(), getHeight());
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
          
            System.out.println("Starting new game...");
            game.startGame();
        } else if (e.getSource() == resumeGameButton) {
      
            String fileName = JOptionPane.showInputDialog(null, "Enter the file name to load:");
            if (fileName != null && !fileName.isEmpty()) {
   
                try {
                    FileInputStream fileInputStream = new FileInputStream(fileName);
                    ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                    game = (Game) objectInputStream.readObject();
                    objectInputStream.close();
                    fileInputStream.close();
                    game.gameCount++; 
                    System.out.println("Game loaded from file: " + fileName);
                    game.playGame(); 
                } catch (IOException | ClassNotFoundException ex) {
                    System.out.println("Error loading the file: " + ex.getMessage());
                }
            } else {
         
                System.out.println("Starting new game...");
            }
        }

        else if (e.getSource() == saveGameButton)

        {
     
            if (game != null) {
                String fileName = JOptionPane.showInputDialog(null, "Enter the file name to save:");
                if (fileName != null && !fileName.isEmpty()) {
                 
                    try {
                        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                        objectOutputStream.writeObject(game);
                        objectOutputStream.close();
                        fileOutputStream.close();
                        System.out.println("Game saved to file: " + fileName);
                    } catch (IOException ex) {
                        System.out.println("Error saving the game: " + ex.getMessage());
                    }
                }
            } else {
                System.out.println("No game to save.");
            }
        } else if (e.getSource() == resetGameButton) {
       
            System.out.println("Resetting game...");
            game.playGame();
        } else if (e.getSource() == exitButton) {
       
            System.out.println("Exiting game...");
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
