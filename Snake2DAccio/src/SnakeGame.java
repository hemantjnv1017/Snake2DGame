import javax.swing.*;


public class SnakeGame extends JFrame {
    PlayZone playZone;
    SnakeGame(){
        PlayZone playZone = new PlayZone();
        add(playZone);
        pack(); // pack the parent component to the child component
        setResizable(false);
        setVisible(true);


    }
    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGame();  // initialize thegame
         }
}