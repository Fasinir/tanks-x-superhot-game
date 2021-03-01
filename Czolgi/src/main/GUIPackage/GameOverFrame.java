package GUIPackage;

import javax.swing.*;
import java.awt.*;

public class GameOverFrame extends JFrame {

    public GameOverFrame(int points)
    {
        this.setLayout(null);
        this.setVisible(true);
        this.setSize(400,200);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label= new JLabel();
        label.setVisible(true);
        String text="Koniec gry, udało ci się zdobyć "+points+" punktów.";
        label.setText(text);
        label.setSize(400,40);
        label.setHorizontalAlignment(JLabel.LEFT);
        this.add(label);
    }
}
