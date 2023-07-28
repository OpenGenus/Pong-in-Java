import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Pong extends JFrame implements KeyListener {
    private static final int WIDTH = 640;
    private static final int HEIGHT = 480;
    private static final int PADDLE_WIDTH = 10;
    private static final int PADDLE_HEIGHT = 60;
    private static final int BALL_SIZE = 10;
    private static final int PADDLE_SPEED = 5;
    private static final int BALL_SPEED = 3;

    private int paddle1Y;
    private int paddle2Y;
    private int ballX;
    private int ballY;
    private int ballXSpeed;
    private int ballYSpeed;

    public Pong() {
        setTitle("Pong");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);

        addKeyListener(this);
        setFocusable(true);

        paddle1Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
        paddle2Y = HEIGHT / 2 - PADDLE_HEIGHT / 2;
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT / 2 - BALL_SIZE / 2;
        ballXSpeed = BALL_SPEED;
        ballYSpeed = BALL_SPEED;
    }

    public void movePaddle1Up() {
        paddle1Y -= PADDLE_SPEED;
        if (paddle1Y < 0) {
            paddle1Y = 0;
        }
    }

    public void movePaddle1Down() {
        paddle1Y += PADDLE_SPEED;
        if (paddle1Y > HEIGHT - PADDLE_HEIGHT) {
            paddle1Y = HEIGHT - PADDLE_HEIGHT;
        }
    }

    public void movePaddle2Up() {
        paddle2Y -= PADDLE_SPEED;
        if (paddle2Y < 0) {
            paddle2Y = 0;
        }
    }

    public void movePaddle2Down() {
        paddle2Y += PADDLE_SPEED;
        if (paddle2Y > HEIGHT - PADDLE_HEIGHT) {
            paddle2Y = HEIGHT - PADDLE_HEIGHT;
        }
    }

    public void moveBall() {
        ballX += ballXSpeed;
        ballY += ballYSpeed;

        // Check collision with paddles
        if (ballX <= PADDLE_WIDTH && ballY + BALL_SIZE >= paddle1Y && ballY <= paddle1Y + PADDLE_HEIGHT) {
            ballXSpeed = BALL_SPEED;
        } else if (ballX >= WIDTH - PADDLE_WIDTH - BALL_SIZE && ballY + BALL_SIZE >= paddle2Y && ballY <= paddle2Y + PADDLE_HEIGHT) {
            ballXSpeed = -BALL_SPEED;
        }

        // Check collision with walls
        if (ballY <= 0 || ballY >= HEIGHT - BALL_SIZE) {
            ballYSpeed = -ballYSpeed;
        }

        // Check if ball is out of bounds
        if (ballX < 0 || ballX > WIDTH - BALL_SIZE) {
            resetGame();
        }
    }

    public void resetGame() {
        ballX = WIDTH / 2 - BALL_SIZE / 2;
        ballY = HEIGHT / 2 - BALL_SIZE / 2;
        ballXSpeed = BALL_SPEED;
        ballYSpeed = BALL_SPEED;
    }

    public void paint(Graphics g) {
        super.paint(g);

        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        g.fillRect(PADDLE_WIDTH, paddle1Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillRect(WIDTH - 2 * PADDLE_WIDTH, paddle2Y, PADDLE_WIDTH, PADDLE_HEIGHT);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

        Toolkit.getDefaultToolkit().sync();
    }

    public static void main(String[] args) {
        Pong game = new Pong();
        game.setVisible(true);

        while (true) {
            game.moveBall();
            game.repaint();
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            movePaddle1Up();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            movePaddle1Down();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            movePaddle2Up();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            movePaddle2Down();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}