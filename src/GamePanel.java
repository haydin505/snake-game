import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {
    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT/UNIT_SIZE);
    int delay;
    int x[];
    int y[];
    int bodyParts;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    boolean isBorderless;
    Timer timer;
    Random random;

    public GamePanel(boolean isBorderless, int delay){     
        this.delay = delay;   
        this.isBorderless = isBorderless;
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        x = new int[GAME_UNITS];
        y = new int[GAME_UNITS];
        bodyParts = 6;
        direction = 'R';
        applesEaten = 0;
        running = true;
        newApple();
        timer = new Timer(delay, this);
        timer.start();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g){        
        for(int i = 0; i < SCREEN_HEIGHT/UNIT_SIZE; i++){
            g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
            g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
        }
        g.setColor(Color.red);
        g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

        for(int i = 0; i < bodyParts; i++){
            if(i == 0){
                g.setColor(Color.green);
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);                
            }else{
                g.setColor(new Color(45, 180, 0));
                g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);                
            }            
        }
        g.setColor(Color.red);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        FontMetrics metrics = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten))/2, g.getFont().getSize());

        if(!running){
            gameOver(g);
        }
    }

    public void newApple(){
        boolean isLocationValid = true;
        do{
            boolean isValid = true;
            appleX = random.nextInt((int)(SCREEN_WIDTH/UNIT_SIZE))*UNIT_SIZE;
            appleY = random.nextInt((int)(SCREEN_HEIGHT/UNIT_SIZE))*UNIT_SIZE;
            for(int i = bodyParts; i > 0; i--){
                if(x[i] == appleX && y[i] == appleY){
                    isValid = false;
                    break;
                }                
            }
            isLocationValid = isValid;
        }while(!isLocationValid);        
    }

    public void move(){
        for(int i = bodyParts; i > 0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }
        switch (direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }

    }
    public void checkApple(){
        if(x[0] == appleX && y[0] == appleY){
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkHeadCollision(){
        // Check whether head collides with body
        for(int i = bodyParts; i > 0; i--){
            if(x[0] == x[i] && y[0] == y[i]){
                running = false;
            }
        }
        if(!running){
            timer.stop();
        }
    }

    public void checkBorderCollision(){
        // Check if head collided border
        if(x[0] < 0){
            running = false;
        }
        if(x[0] > SCREEN_WIDTH){
            running = false;
        }
        if(y[0] < 0){
            running = false;
        }
        if(y[0] > SCREEN_HEIGHT){
            running = false;
        }

        if(!running){
            timer.stop();
        }

    }
    public void gameOver(Graphics g){               
        // // Score
        // g.setColor(Color.red);
        // g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
        // FontMetrics metrics1 = getFontMetrics(g.getFont());
        // g.drawString("Your Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Your Score: " + applesEaten))/2, g.getFont().getSize());
         
        if(delay == MainMenuFrame.easyDelay){
            if(applesEaten > MainMenuFrame.highestScoreEasy){
                MainMenuFrame.highestScoreEasy = applesEaten;
                drawHighestScoreNotice(g);
            }
            drawHighestScore(g, MainMenuFrame.highestScoreEasy);
        }

        if(delay == MainMenuFrame.normalDelay){
            if(applesEaten > MainMenuFrame.highestScoreNormal){
                MainMenuFrame.highestScoreNormal = applesEaten;
                drawHighestScoreNotice(g);
            }
            drawHighestScore(g, MainMenuFrame.highestScoreNormal);
        }

        if(delay == MainMenuFrame.hardDelay){
            if(applesEaten > MainMenuFrame.highestScoreHard){
                MainMenuFrame.highestScoreHard = applesEaten;
                drawHighestScoreNotice(g);
            }
            drawHighestScore(g, MainMenuFrame.highestScoreHard);
        }
        
        // Game over
        g.setColor(Color.red);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);

        // Press any key to try again.
        g.setColor(Color.white);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        FontMetrics metrics3 = getFontMetrics(g.getFont());
        g.drawString("Press any key to try again.", (SCREEN_WIDTH - metrics3.stringWidth("Press any key to try again."))/2, SCREEN_HEIGHT/2 + 75);
    }

    private void drawHighestScoreNotice(Graphics g){
        // Highest Score
        g.setColor(Color.green);
        g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 30));
        FontMetrics metrics5 = getFontMetrics(g.getFont());
        g.drawString("You have passed the highest score!", (SCREEN_WIDTH - metrics5.stringWidth("You have passed the highest score!"))/2, g.getFont().getSize()*4);
    }

    private void drawHighestScore(Graphics g, int highestScore){
       // Highest Score
       g.setColor(Color.red);
       g.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 40));
       FontMetrics metrics4 = getFontMetrics(g.getFont());
       g.drawString("Highest Score: " + highestScore, (SCREEN_WIDTH - metrics4.stringWidth("Highest Score: " + highestScore))/2, g.getFont().getSize()*5);
    }



    @Override
    public void actionPerformed(ActionEvent e) {
        if(running){
            move();
            checkApple();
            if(!isBorderless){
                checkBorderCollision();
            }else{
                if(y[0] > SCREEN_HEIGHT - UNIT_SIZE){
                    y[0] = 0;
                }
                if(y[0] < 0){
                    y[0] = SCREEN_HEIGHT;
                }
                if(x[0] > SCREEN_WIDTH - UNIT_SIZE){
                    x[0] = 0;
                }
                if(x[0] < 0){
                    x[0] = SCREEN_WIDTH;
                }
            }
            checkHeadCollision();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e){
            // if((y[0] > 0 && y[0] < SCREEN_HEIGHT) || (x[0] > 0 && x[0] < SCREEN_WIDTH)){
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_LEFT:
                        if(direction != 'R'){
                            direction = 'L';
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if(direction != 'L'){
                            direction = 'R';
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if(direction != 'U'){
                            direction = 'D';
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if(direction != 'D'){
                            direction = 'U';
                        }
                        break;
                    
                
                    default:
                        break;
                }
            // }
            if(!running){                
                startGame();
            }

        }
    }
}
