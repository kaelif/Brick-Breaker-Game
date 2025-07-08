import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;

public class Gameplay extends JPanel implements KeyListener, ActionListener {
    private boolean play = false;
    private int score = 0;

    private int totalBricks = 21;

    private Timer timer;
    private int delay = 8;

    private int playerX = 310;

    private int ballPosX = 120;
    private int ballPosY = 350;
    private int ballXdir = -1;
    private int ballYdir = -2;

    public Gameplay() {
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paint(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(1, 1, 692, 592);

        g.setColor(Color.yellow);
        g.fillRect(0, 0, 692, 3);
        g.fillRect(691, 0, 3, 592);

        g.setColor(Color.green);
        g.fillRect(playerX, 550, 100, 8);

        g.setColor(Color.yellow);
        g.fillRect(ballPosX, ballPosY, 20, 20);

        if (!play && ballPosY > 570) {
            // Draw 'Game Over' header
            g.setColor(Color.RED);
            java.awt.Font headerFont = new java.awt.Font("serif", java.awt.Font.BOLD, 40);
            g.setFont(headerFont);
            String header = "Game Over";
            java.awt.FontMetrics headerMetrics = g.getFontMetrics(headerFont);
            int headerX = (692 - headerMetrics.stringWidth(header)) / 2;
            int headerY = 270;
            g.drawString(header, headerX, headerY);

            // Draw subheader
            g.setColor(Color.WHITE);
            java.awt.Font subFont = new java.awt.Font("serif", java.awt.Font.PLAIN, 20);
            g.setFont(subFont);
            String subheader = "Move bar to edge of screen to restart";
            java.awt.FontMetrics subMetrics = g.getFontMetrics(subFont);
            int subX = (692 - subMetrics.stringWidth(subheader)) / 2;
            int subY = headerY + 40;
            g.drawString(subheader, subX, subY);
        }

        g.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (play) {
            ballPosX += ballXdir;
            ballPosY += ballYdir;

            if (ballPosX < 0 || ballPosX > 670) {
                ballXdir = -ballXdir;
            }

            if (ballPosY < 0) {
                ballYdir = -ballYdir;
            }

            if (new java.awt.Rectangle(ballPosX, ballPosY, 20, 20)
                    .intersects(new java.awt.Rectangle(playerX, 550, 100, 8))) {
                ballYdir = -ballYdir;
            }

            if (ballPosY > 570) {
                play = false;
                ballXdir = 0;
                ballYdir = 0;
            }
        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            if (playerX >= 600) {
                playerX = 600;
            } else {
                moveRight();
            }
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            if (playerX < 10) {
                playerX = 10;
            } else {
                moveLeft();
            }
        }

        if (!play && (e.getKeyCode() == KeyEvent.VK_LEFT || e.getKeyCode() == KeyEvent.VK_RIGHT)) {
            play = true;
            ballPosX = 120;
            ballPosY = 350;
            ballXdir = -1;
            ballYdir = -2;
            playerX = 310;
            score = 0;
            repaint();
        }
    }

    public void moveRight() {
        play = true;
        playerX += 20;
    }

    public void moveLeft() {
        play = true;
        playerX -= 20;
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
