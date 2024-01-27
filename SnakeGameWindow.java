import javax.swing.JFrame;
public class SnakeGameWindow  extends JFrame{

    SnakeGameWindow() {

        this.add(new SnakeGamePanel());  // Add the game panel to the window
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);

    }
}
