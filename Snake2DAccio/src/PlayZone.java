import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class PlayZone extends JPanel implements ActionListener {
    int zoneHeight = 500;
    int zoneWidth = 500;

    // snake size
    int maxPart = 2500; // maxDot
    int partSize = 10; // dotSize
    int part; // dot

    int[] x = new int[maxPart];
    int[] y = new int[maxPart];

    //for food
    int foodx;
    int foody;
    // Images
    Image body, head, food;
    Timer timer;
    int delay = 100;
    boolean leftDirection = true;
    boolean rightDirection = false;
    boolean upDirection = false;
    boolean downDirection = false;
    boolean inGame = true;

    PlayZone() {
        ImplementControl implementControl = new ImplementControl();
        addKeyListener(implementControl);
        setFocusable(true);
        setPreferredSize(new Dimension(zoneWidth, zoneHeight));
        setBackground(Color.BLACK);
        Game();
        resourcesImage();
    }

    // initializing the game
    public void Game() { // initGame method
        part = 3;
        // initialize snake position
        x[0] = 250;
        y[0] = 250;
        for (int i = 1; i < part; i++) {
            x[i] = x[0] + partSize * i;
            y[i] = y[0];
        }


        foodPosition();
        timer = new Timer(delay, this);
        timer.start();
    }

    // load images from resources folder to image objects
    public void resourcesImage() {
        ImageIcon bodyIcon = new ImageIcon("src/resources/part.jpg");
        body = bodyIcon.getImage();

        ImageIcon headIcon = new ImageIcon("src/resources/head.jpg");
        head = headIcon.getImage();

        ImageIcon foodIcon = new ImageIcon("src/resources/food.jpg");
        food = foodIcon.getImage();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }

    // draw image
    public void doDrawing(Graphics g) {
        if (inGame) {
            g.drawImage(food, foodx, foody, this);

            for (int i = 0; i < part; i++) {
                if (i == 0) g.drawImage(head, x[0], y[0], this);
                else g.drawImage(body, x[i], y[i], this);
            }
        } else {
            timer.stop();
            endGame(g);
        }
    }

    //Randomize the food position
    public void foodPosition() {
        foodx = ((int) (Math.random() * (zoneWidth / partSize))) * partSize;
        foody = ((int) (Math.random() * (zoneHeight / partSize))) * partSize;
    }

    // check collision with border and body
    public void checkCollision() {

        // for body collision
        for (int i = 1; i < part; i++) {
            if (i > 4 && x[0] == x[i] && y[0] == y[i]) inGame = false;
        }
        // collosin with border
        if (x[0] < 0) inGame = false;
        if (y[0] < 0) inGame = false;
        if (x[0] >= zoneWidth) inGame = false;
        if (y[0] >= zoneHeight) inGame = false;
    }

    // display last msg and point you earn
    public void endGame(Graphics g) {
        String str = "Game Over";
        int points = (part - 3) * 10;
        String finalScore = "Points:" + Integer.toString(points);
        Font small = new Font("Helvetica", Font.ITALIC, 16);
        FontMetrics fontMetrics = getFontMetrics(small);
        g.setColor(Color.YELLOW);
        g.setFont(small);
        g.drawString(str, (zoneWidth - fontMetrics.stringWidth(str)) / 2, zoneHeight / 4);
        g.drawString(finalScore, (zoneWidth - fontMetrics.stringWidth(finalScore)) / 2, 3 * (zoneHeight / 4));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (inGame) {
            eatFood();
            checkCollision();
            move();
        }
        repaint();
    }

    //make the snake moving
    public void move() {
        for (int i = part - 1; i > 0; i--) {
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }
        if (leftDirection) x[0] -= partSize;
        if (rightDirection) x[0] += partSize;
        if (upDirection) y[0] -= partSize;
        if (downDirection) y[0] += partSize;
    }

    // make snake eat food
    public void eatFood() {
        if (foodx == x[0] && foody == y[0]) {
            part++;
            foodPosition();
        }
    }

    // implements the controls
    private class ImplementControl extends KeyAdapter {
        public void keyPressed(KeyEvent keyEvent) {
            int key = keyEvent.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !rightDirection) {
                leftDirection = true;
                rightDirection = false;
                upDirection = false;
                downDirection = false;
            }

            if (key == KeyEvent.VK_RIGHT && !leftDirection) {
                leftDirection = false;
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }

            if (key == KeyEvent.VK_UP && !downDirection) {
                leftDirection = false;
                rightDirection = false;
                upDirection = true;
                downDirection = false;
            }

            if (key == KeyEvent.VK_DOWN && !upDirection) {
                leftDirection = false;
                rightDirection = false;
                upDirection = false;
                downDirection = true;
            }
        }
    }
}
