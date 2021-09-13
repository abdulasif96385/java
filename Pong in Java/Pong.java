import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.Font;

public class Pong implements KeyListener,MouseListener,MouseMotionListener,Runnable {

  private Pong pong  = this;

  private final int WINDOW_WIDTH = 640;
  private final int WINDOW_HEIGHT = 480;

  private Font smallFont;
  private Font largeFont;
  private Button pongButton;
  private Button startButton;
  private Button howToPlayButton;
  private boolean howToPlay;
  private Button restartButton;

  private boolean running;
  private Thread thread;
  private String gameState;

  private JPanel panel;
  private Paddle paddleOne;
  private Paddle paddleTwo;
  private Ball ball;
  private int[] keys;
  private boolean paddleOneWon;
  private boolean paddleTwoWon;


  public Pong() {
    init();
  }

  public void init() {
    Thread initThread = new Thread(new Runnable() {
      @Override
      public void run() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame frame = new JFrame("Pong");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameState = "Start";

        smallFont = new Font("Helvetica", Font.BOLD,24);
        largeFont = new Font("Helvetica", Font.BOLD,32);
        pongButton = new Button("PONG",WINDOW_WIDTH/2-70, 50,Color.WHITE,new Font("Helvetica", Font.BOLD,45));
        startButton = new Button("START",WINDOW_WIDTH/2-50, WINDOW_HEIGHT/2,Color.WHITE,smallFont);
        howToPlayButton = new Button("HOW TO PLAY",WINDOW_WIDTH/2-50, WINDOW_HEIGHT/2 + 50,Color.WHITE,smallFont);
        howToPlay = false;
        restartButton = new Button("RESTART",WINDOW_WIDTH/2-200+20, WINDOW_HEIGHT/2,Color.BLACK,smallFont);

        paddleOne = new Paddle(20,WINDOW_HEIGHT/2 - 30);
        paddleTwo = new Paddle(WINDOW_WIDTH - 30,WINDOW_HEIGHT/2 - 30);
        ball = new Ball(WINDOW_WIDTH/2 - 3,WINDOW_HEIGHT/2 - 3);
        thread = new Thread(pong,"Pong");
        keys = new int[2];
        paddleOneWon = false;
        paddleTwoWon = false;

        panel = new JPanel() {
          @Override
          public void paintComponent(Graphics g) {
            super.paintComponent(g);
            if(gameState.equals("Start")) {
              drawStartScreen(g);
            }
            else if(gameState.equals("playing") && !paddleOneWon && !paddleTwoWon) {
              drawGame(g);
            }
            else if(gameState.equals("paused")) {
              drawMenu(g);
            }
            else if(gameState.equals("playing") || paddleOneWon || paddleTwoWon) {
              drawWinScreen(g);
            }
          }
        };

        panel.setPreferredSize(new Dimension(WINDOW_WIDTH,WINDOW_HEIGHT));
        frame.addKeyListener(pong);
        frame.addMouseListener(pong);
        frame.addMouseMotionListener(pong);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
      }
    });
    initThread.start();
    try {
      initThread.join();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    running = true;
  }

  @Override
  public void run() {
    while(running) {
      if(gameState.equals("paused")) {
        Thread pauseThread = new Thread(new Runnable() {
          @Override
          public void run() {
            while(gameState.equals("paused")) {
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
              System.out.println("paused");
            }
            System.out.println("unpaused");
          }
        });
        pauseThread.start();
        try {
          pauseThread.join();
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
      try {
      Thread.sleep(33);
    }
    catch(InterruptedException e) {
      System.out.println("Error: " + e);
    }

      if(keys[0] == 38) {
        if(paddleOne.speed > -paddleOne.MAX_SPEED) {
          paddleOne.speed -= paddleOne.acc;
        }
      }
      if(keys[1] == 40) {
        if(paddleOne.speed < paddleOne.MAX_SPEED) {
          paddleOne.speed += paddleOne.acc;
        }
      }
      if(keys[0] != 38 && keys[1] != 40) {
        if(paddleOne.speed > 0) {
          paddleOne.speed -= paddleOne.acc;
        }
        else if(paddleOne.speed < 0) {
          paddleOne.speed += paddleOne.acc;
        }
      }

      if(ball.x > WINDOW_WIDTH/2 && ball.speedX > 0) {
        if (ball.y - ball.HEIGHT / 2 < paddleTwo.y) {
          paddleTwo.speed -= paddleTwo.acc;
        } else if (ball.y - ball.HEIGHT / 2 > paddleTwo.y - paddleTwo.HEIGHT) {
          paddleTwo.speed += paddleTwo.acc;
        }
      }
      else {
        if(paddleTwo.speed > 0) {
          paddleTwo.speed -= paddleTwo.acc;
        }
        else if(paddleTwo.speed < 0) {
          paddleTwo.speed += paddleTwo.acc;
        }
      }

      paddleOne.y += paddleOne.speed;
      paddleTwo.y += paddleTwo.speed;
      ball.x += ball.speedX;
      ball.y += ball.speedY;

      paddleOne.contain(WINDOW_HEIGHT);
      paddleTwo.contain(WINDOW_HEIGHT);
      ball.contain(WINDOW_WIDTH,WINDOW_HEIGHT,paddleOne,paddleTwo);

      if(Collision.detectCollision(paddleOne,ball)) {
        ball.speedX *= -1;
        if(paddleOne.speed != 0) {
          ball.speedY += paddleOne.speed / 2;
        }
        else {
          ball.speedX += 1;
        }
        ball.x = paddleOne.x + paddleOne.WIDTH;
      }
      else if(Collision.detectCollision(paddleTwo,ball)) {
        ball.speedX *= -1;
        if(paddleOne.speed != 0) {
          ball.speedY += paddleTwo.speed / 2;
        }
        else {
          ball.speedX -= 1;
        }
        ball.x = paddleTwo.x - ball.WIDTH;
      }

      checkWin();
      panel.repaint();
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if(e.getKeyCode() == 38) {
      keys[0] = 38;
    }
    if(e.getKeyCode() == 40) {
      keys[1] = 40;
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if(e.getKeyCode() == 38) {
      keys[0] = 0;
    }
    if(e.getKeyCode() == 40) {
      keys[1] = 0;
    }
    if(e.getKeyCode() == 80) {
      if(gameState != "paused") {
        gameState = "paused";
      }
      else {
        gameState = "playing";
      }
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {}
  @Override
  public void mouseClicked(MouseEvent e) {}

  @Override
  public void mousePressed(MouseEvent e) {
    if(gameState.equals("Start")) {
      if(e.getY() > startButton.y && e.getY() < startButton.y + 24) {
        gameState = "playing";
        thread.start();
      }
    }
    if(gameState.equals("paused") || paddleOneWon || paddleTwoWon) {
      if(e.getY() > restartButton.y && e.getY() < restartButton.y + 24) {
        restart();
        gameState = "playing";
      }
    }
  }

  @Override
  public void mouseReleased(MouseEvent e) {}
  @Override
  public void mouseEntered(MouseEvent e) {}
  @Override
  public void mouseExited(MouseEvent e) {}
  @Override
  public void mouseDragged(MouseEvent e) {}

  @Override
  public void mouseMoved(MouseEvent e) {
    if(gameState.equals("Start") || gameState.equals("paused") || paddleOneWon || paddleTwoWon) {
      if(e.getY() > startButton.y && e.getY() < startButton.y + 32) {
        startButton.font = largeFont;
      }
      else {
        startButton.font = smallFont;
      }
      if(e.getY() > howToPlayButton.y && e.getY() < howToPlayButton.y + 32) {
        howToPlayButton.font = largeFont;
        howToPlay = true;
      }
      else {
        howToPlayButton.font = smallFont;
        howToPlay = false;
      }
      if(e.getY() > restartButton.y && e.getY() < restartButton.y + 32) {
        restartButton.font = largeFont;
      }
      else {
        restartButton.font = smallFont;
      }
      panel.repaint();
    }
  }

  public void checkWin() {
    if(paddleOne.score == 10) {
      ball.speedX = 0;
      ball.speedY = 0;
      paddleOne.score = 0;
      paddleTwo.score = 0;
      paddleOneWon = true;
    }
    else if(paddleTwo.score == 10) {
      ball.speedX = 0;
      ball.speedY = 0;
      paddleOne.score = 0;
      paddleTwo.score = 0;
      paddleTwoWon = true;
    }
  }

  public void restart() {
    ball.x = WINDOW_WIDTH/2 - 5;
    ball.y = WINDOW_HEIGHT/2 - 5;
    ball.speedX = 0;
    ball.speedY = 0;
    paddleOne.score = 0;
    paddleTwo.score = 0;
    ball.setSpeed();
    paddleOneWon = false;
    paddleTwoWon = false;
    paddleOne.x = 20;
    paddleOne.y = WINDOW_HEIGHT/2;
    paddleTwo.x = WINDOW_WIDTH - 30;
    paddleTwo.y = WINDOW_HEIGHT/2 - 30;
  }

  public void drawStartScreen(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
    pongButton.draw(g);
    startButton.draw(g);
    howToPlayButton.draw(g);
    if(howToPlay) {
      g.setFont(new Font("Helvetica",Font.BOLD,14));
      g.drawString("USE UP AND DOWN ARROW KEYS TO MOVE AND P FOR PAUSING THE GAME",50,WINDOW_HEIGHT/2+200);
    }
  }

  public void drawGame(Graphics g) {
    g.setColor(Color.BLACK);
    g.fillRect(0,0,WINDOW_WIDTH,WINDOW_HEIGHT);
    paddleOne.draw(g);
    paddleTwo.draw(g);
    ball.draw(g);
    paddleOne.displayScore(g,100,50);
    paddleTwo.displayScore(g,WINDOW_WIDTH-150,50);
  }

  public void drawMenu(Graphics g) {
    drawGame(g);
    g.setColor(Color.WHITE);
    g.fillRect(WINDOW_WIDTH/2-200,WINDOW_HEIGHT/2-150,400,300);
    howToPlayButton.color = Color.BLACK;
    howToPlayButton.x = WINDOW_WIDTH/2-200+20;
    howToPlayButton.y = WINDOW_HEIGHT/2-100;
    howToPlayButton.draw(g);
    restartButton.draw(g);
    if(howToPlay) {
      g.setColor(Color.WHITE);
      g.setFont(new Font("Helvetica",Font.BOLD,14));
      g.drawString("USE UP AND DOWN ARROW KEYS TO MOVE AND P FOR PAUSING THE GAME",50,WINDOW_HEIGHT/2+200);
    }

  }

  public void drawWinScreen(Graphics g) {
    drawGame(g);
    g.setColor(Color.WHITE);
    g.fillRect(WINDOW_WIDTH/2-200,WINDOW_HEIGHT/2-150,400,300);
    g.setColor(Color.BLACK);
    g.setFont(smallFont);
    if(paddleOneWon) {
      g.drawString("You won!",WINDOW_WIDTH/2-100,WINDOW_HEIGHT/2+100);
    }
    else {
      g.drawString("You lost:(",WINDOW_WIDTH/2-100,WINDOW_HEIGHT/2+100);
    }
    restartButton.draw(g);
  }

  public static void main(String[] args) {
    Pong pong = new Pong();
  }
}
