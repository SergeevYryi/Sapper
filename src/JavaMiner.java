import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Locale;

import miner.Box;
import miner.Coordinate;
import miner.Game;
import miner.Ranges;


public class JavaMiner extends JFrame {

private Game game;
    private JPanel panel;
    private JLabel label;
    private final int COLS = 9;
    private final int ROWS = 9;
    private final int BOMBS = 10;
    private final int IMAGE_SIZE = 50;

    public static void main(String[] args)
    {
        new JavaMiner();
    }
    private JavaMiner()
    {
        game = new Game (COLS, ROWS, BOMBS);
        game.start();
        setImages();
        initLabel();
        initPanel();
        initFrame();
    }
    private void initLabel()
    {
        label = new JLabel("Welocme");
        add (label, BorderLayout.SOUTH);
    }
    private void initPanel()
    {
        panel = new JPanel()
        {
            @Override
            protected void paintComponent(Graphics g)
            {
                super.paintComponent(g);
                for (Coordinate coordinate : Ranges.getAllCoordinates())
                    g.drawImage((Image) game.getBox(coordinate).image,
                            coordinate.x * IMAGE_SIZE, coordinate.y * IMAGE_SIZE, this);
            }
        };

        panel.addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                int x = e.getX() / IMAGE_SIZE;
                int y = e.getY() / IMAGE_SIZE;
                Coordinate coordinate = new Coordinate(x, y);
                if (e.getButton() == MouseEvent.BUTTON1)
                    game.pressLeftButton (coordinate);
                if (e.getButton() == MouseEvent.BUTTON3)
                    game.pressRightButton (coordinate);
                if (e.getButton() == MouseEvent.BUTTON2)
                    game.start();
                label.setText(getMessage());
                panel.repaint();
            }
        });
        panel.setPreferredSize(new Dimension(
                Ranges.getSize().x * IMAGE_SIZE,
                Ranges.getSize().y * IMAGE_SIZE));
        add (panel);
    }

    private String getMessage()
    {
        switch (game.getState())
        {
            case PLAYED: return "Try again!";
            case BOMBED: return "YOU LOOOOSEER...";
            case WINNER: return "Congratulations!";
            default    : return "Welcome";
        }
    }

    private void initFrame()
    {
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Java Miner");
        setResizable(false);
        setVisible(true);
        pack();
        setLocationRelativeTo(null);
        setIconImage(getImage("icon"));
    }
    private void setImages()
    {
        for (Box box : Box.values())
            box.image = getImage(box.name().toLowerCase());
    }
    private Image getImage (String name)
    {
        String filename = "img/" + name + ".png";
        ImageIcon icon = new ImageIcon(getClass().getResource(filename));
        return icon.getImage();
    }
}

