package GUIPackage;

import EnginePackage.Engine;
import MapAndMoving.MoveDirection;
import MapAndMoving.RectangularMap;
import TankPackage.PlayerTank;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MainFrame extends JFrame implements KeyListener, MouseListener {
    private RectangularMap map;
    private int SingleElementHeight;
    private int SingleElementWidth;
    private MapPanel mapPanel;
    private PlayerTank playerTank;
    private Engine engine;
    boolean isdead=false;
    private JLabel pointsLabel=new JLabel();
   // private RightPanel rightpanel;
    public MainFrame(RectangularMap map, PlayerTank playerTank,Engine engine, PlayerTank player)
    {
        this.engine=engine;
        this.playerTank=playerTank;
        this.setLayout(null);
        // this.pack();
        this.setVisible(true);
        this.setSize(750,750);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.map=map;

        this.addKeyListener(this);
        this.addMouseListener(this);

        pointsLabel.setVisible(true);
        String text=map.getPoints()+" punktów";
        pointsLabel.setText(text);
        pointsLabel.setBounds(this.getWidth()/2,0,200,20);
        //pointsLabel.setHorizontalAlignment(JLabel.CENTER);
        this.add(pointsLabel);


        float tmpwidth=(float) this.getWidth()/(float) this.map.getSize()[0];
        float tmpheight=(float) this.getHeight()/(float) this.map.getSize()[1];
        this.SingleElementWidth=(int)tmpwidth;
        this.SingleElementHeight=(int)tmpheight;

        //38 seems to be height of titlebar
        this.mapPanel=new MapPanel(this.map,player.getHealthPoints(),0,0,this.getWidth(),this.getHeight()-38,player);

        this.add(mapPanel);
        mapPanel.refresh();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(!isdead) {
            switch (e.getKeyChar()) {
                case 'w':

                    //System.out.println("HEY");
                    playerTank.move(MoveDirection.FORWARD);
                    if (!engine.run()) {
                        isdead = true;
                        new GameOverFrame(map.getPoints());
                    }
                    this.refresh();

                    break;
                case 'a':
                    playerTank.move(MoveDirection.LEFT);
                    this.refresh();

                    break;
                case 's':
                    playerTank.move(MoveDirection.BACKWARD);

                    if (!engine.run()) {
                        isdead = true;
                        new GameOverFrame(map.getPoints());
                    }
                    this.refresh();

                    break;
                case 'd':
                    playerTank.move(MoveDirection.RIGHT);
                    this.refresh();

                    break;
                default:
                    break;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if(!isdead) {
            //System.out.println("click");
            if (!engine.run()) {
                isdead = true;
                new GameOverFrame(map.getPoints());
            }
            playerTank.fire();
            this.refresh();
        }


    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    private void refresh()
    {
        String text=map.getPoints()+" punktów";
        pointsLabel.setText(text);
        mapPanel.refresh();
        this.repaint();
        this.revalidate();
    }

}
