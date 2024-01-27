import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class SnakeGamePanel extends JPanel implements ActionListener {

    static final int WINDOW_WIDTH = 600;
    static final int WINDOW_HEIGHT = 600;
    static final int GRID_SIZE = 25;
    static final int TOTAL_GRID_UNITS = (WINDOW_WIDTH*WINDOW_HEIGHT)/GRID_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[TOTAL_GRID_UNITS];
    final int[] y = new int[TOTAL_GRID_UNITS];
    int snakeLength = 6;
    int score;
    int fruitX;
    int fruitY;
    char direction = 'D';
    boolean running = false;
    Timer timer;
    Random random;


    SnakeGamePanel(){
        random = new Random();
        this.setPreferredSize(new Dimension(WINDOW_WIDTH, WINDOW_HEIGHT));  // Set panel dimensions
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new KeyPressHandler());
        startGame();

    }
    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        if(running) {
            g.setColor(Color.red);
            g.fillOval(fruitX, fruitY, GRID_SIZE, GRID_SIZE);

            for (int i = 0; i < snakeLength; i++) {
                if (i == 0) {
                    g.setColor(Color.white);
                    g.fillRect(x[i], y[i], GRID_SIZE, GRID_SIZE);
                } else {
                    g.setColor(new Color(192, 192, 192));
                    g.fillRect(x[i], y[i], GRID_SIZE, GRID_SIZE);
                }
            }
            g.setColor(Color.blue);
            g.setFont(new Font("Ink Free", Font.BOLD, 35));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score:" + score, (WINDOW_WIDTH - metrics.stringWidth("Score:" + score)) / 2,
                    g.getFont().getSize());
        }
        else{
            gameOver(g);
        }
    }
    public void newApple(){
        fruitX = random.nextInt((int)(WINDOW_WIDTH/GRID_SIZE)) * GRID_SIZE;
        fruitY = random.nextInt((int)(WINDOW_HEIGHT/GRID_SIZE)) * GRID_SIZE;
    }

    public void move(){
        for(int i = snakeLength; i > 0;i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction) {
            case 'W' -> y[0] = y[0] - GRID_SIZE;
            case 'S' -> y[0] = y[0] + GRID_SIZE;
            case 'A' -> x[0] = x[0] - GRID_SIZE;
            case 'D' -> x[0] = x[0] + GRID_SIZE;
        }
    }

    public void checkApple(){
        if((x[0] == fruitX) &&  (y[0] == fruitY)){
            snakeLength++;
            score++;
            newApple();
        }

    }
    public void checkCollisions(){
        for(int i = snakeLength; i > 0;i--){
            if((x[0] == x[i]) && (y[0] == y[i])){
                running = false;
            }
        }
        if(x[0] < 0){
            running = false;
        }
        if(x[0] > WINDOW_WIDTH){
            running = false;
        }
        if(y[0] < 0){
            running = false;
        }
        if(y[0] > WINDOW_HEIGHT){
            running = false;
        }
        if(!running){
            timer.stop();
        }
    }
    public void gameOver(Graphics g){
        g.setColor(Color.blue);
        g.setFont(new Font("Ink Free", Font.BOLD, 35));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score:" + score, (WINDOW_WIDTH - metrics1.stringWidth("Score:" + score)) / 2,
                g.getFont().getSize());

        g.setColor(Color.blue);
        g.setFont(new Font("Ink Free",Font.BOLD,80));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (WINDOW_WIDTH - metrics2.stringWidth("Game Over"))/2,WINDOW_HEIGHT/2);
    }



    @Override
    public void actionPerformed(ActionEvent e) {

        if(running){
            move();
            checkApple();
            checkCollisions();
        }
        repaint();

    }
    public class KeyPressHandler extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            switch (e.getKeyCode()){
                case KeyEvent.VK_A:
                    if(direction != 'D'){
                        direction = 'A';
                    }
                    break;
                case KeyEvent.VK_D:
                    if(direction != 'A'){
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_W:
                    if(direction != 'S'){
                        direction = 'W';
                    }
                    break;
                case KeyEvent.VK_S:
                    if(direction != 'W'){
                        direction = 'S';
                    }
                    break;
            }
        }
    }

}
