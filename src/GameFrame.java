import javax.swing.*;
import java.awt.*;

public class GameFrame extends JFrame {

    public GameFrame(boolean isBorderless, int delay){        
        this.add(new GamePanel(isBorderless, delay));
        this.setTitle("Snake");        
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
