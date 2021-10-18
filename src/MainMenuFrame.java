import java.awt.Window;
import java.awt.event.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MainMenuFrame  extends JFrame implements ActionListener, ItemListener{
    public static int highestScoreEasy;
    public static int highestScoreNormal;
    public static int highestScoreHard;
    public static final int easyDelay = 110;
    public static final int normalDelay = 80;
    public static final int hardDelay = 40;
    boolean isBorderless = false;
    int delay = normalDelay;
    private JCheckBox borderCheckBox;

    public MainMenuFrame() {
        
        this.setLocationRelativeTo(null);    
        this.setTitle("Snake");
        this.setSize(300, 300);
        this.setLayout(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        // this.setResizable(false);

        
        JButton startGameButton =new JButton("Start Game");  
        startGameButton.setBounds(40,40,100,50);  
        startGameButton.addActionListener(this);
        startGameButton.setMnemonic(KeyEvent.VK_R);
        startGameButton.setActionCommand("start");
        startGameButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));
        // this.add(startGameButton);
        
        // this.pack();        
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        /*
        JButton borderlessGameButton = new JButton("Borderless Game");         
        borderlessGameButton.setBounds(100,100,200,50);  
        borderlessGameButton.addActionListener(this);
        borderlessGameButton.setMnemonic(KeyEvent.VK_E);
        borderlessGameButton.setActionCommand("Borderless");
        this.add(borderlessGameButton);

        JButton borderGameButton = new JButton("Border Game");
        borderGameButton.setBounds(100,200,200,50);  
        borderGameButton.addActionListener(this);
        borderGameButton.setMnemonic(KeyEvent.VK_B);
        borderGameButton.setActionCommand("Border");
        this.add(borderGameButton);
        */
        borderCheckBox = new JCheckBox("Borderless");
        borderCheckBox.setSelected(isBorderless);
        borderCheckBox.addItemListener(this);
        borderCheckBox.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));


        JRadioButton easyButton = new JRadioButton("Easy");
        easyButton.setMnemonic(KeyEvent.VK_B);
        easyButton.setActionCommand("easy");
        easyButton.setSelected(false);
        easyButton.addActionListener(this);
        easyButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

        
        JRadioButton normalButton = new JRadioButton("Normal");
        normalButton.setMnemonic(KeyEvent.VK_B);
        normalButton.setActionCommand("normal");
        normalButton.setSelected(true);
        normalButton.addActionListener(this);
        normalButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

        JRadioButton hardButton = new JRadioButton("Hard");
        hardButton.setMnemonic(KeyEvent.VK_B);
        hardButton.setActionCommand("hard");
        hardButton.setSelected(false);
        hardButton.addActionListener(this);
        hardButton.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 12));

        // ButtonGroup group = new ButtonGroup();
        // group.add(easyButton);
        // group.add(normalButton);
        // group.add(hardButton);

        JLabel difficultyLabel = new JLabel("Difficulty", JLabel.CENTER);
        difficultyLabel.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 15));


        

        JPanel checkPanel = new JPanel(new GridLayout(0, 3));
        checkPanel.setOpaque(true);
        checkPanel.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));        
        checkPanel.add(easyButton);
        checkPanel.add(normalButton);
        checkPanel.add(hardButton);        
        
        JPanel mainPanel = new JPanel(new GridLayout(0, 1));
        mainPanel.setOpaque(true);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
        mainPanel.add(difficultyLabel);        
        mainPanel.add(checkPanel);
        mainPanel.add(new JSeparator());
        mainPanel.add(borderCheckBox);
        mainPanel.add(new JSeparator());
        mainPanel.add(startGameButton);

        // this.add(checkPanel, BorderLayout.LINE_START);
        this.setContentPane(mainPanel);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if("start".equals(e.getActionCommand())){
            new GameFrame(isBorderless, delay);                                
        }

        if("easy".equals(e.getActionCommand())){
            delay = easyDelay;
        }

        if("normal".equals(e.getActionCommand())){
            delay = normalDelay;
        }

        if("hard".equals(e.getActionCommand())){
            delay = hardDelay;
        }
        
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        Object source = e.getItemSelectable();
        
        if(source == borderCheckBox){
            isBorderless = !isBorderless;
        }
        
    }




    
    
}
