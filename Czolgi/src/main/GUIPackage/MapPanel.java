package GUIPackage;


import MapAndMoving.RectangularMap;
import MapAndMoving.Vector2d;
import TankPackage.PlayerTank;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MapPanel extends JPanel{
    RectangularMap map;
    private int initialHP;
    private PlayerTank playerTank;
    public MapPanel(RectangularMap map, int initialHP, int xcoord, int ycoord, int width, int height, PlayerTank player)
    {
        this.playerTank=player;
        this.initialHP= initialHP;
        this.map=map;
        this.setVisible(true);
        this.setBounds(xcoord,ycoord,width,height);

    }
    @Override
    public void paint(Graphics g)
    {
        Graphics2D g2D= (Graphics2D) g;

        g2D.setPaint(new Color(200,150,0));
        g2D.fillRect(0,0,this.getWidth(),this.getHeight());
        g2D.setPaint(new Color(0,150,0));
        //[0] is width
        //[1] is height

        int[] mapSize=this.map.getSize();

        //rel - relative



        //sizes relative to size of the panel
        float relwidth= (float) (this.getWidth())/(float)(mapSize[0]);
        float relheight= (float) (this.getHeight())/(float)(mapSize[1]);

        g2D.setPaint(new Color(0,100,0));
        /*
        TANK ASSET FROM:
        https://opengameart.org/content/freeart-topdown-extras-tank
        */
        //drawing tanks
        List<Vector2d> tankPositions=this.map.getTankPositions();
        for(Vector2d tankPosition:tankPositions)
        {
            float relx=(((float)tankPosition.x/(float) mapSize[0])*this.getWidth());
            //looks a bit complicated because of how Swing is using coordinates to draw
            float rely=(float)(this.getHeight())- (((float)tankPosition.y/(float) mapSize[1])*this.getHeight())- relheight;

            //version with squares
            //g2D.fillRect((int)relx,(int)rely,(int)relwidth,(int)relheight);


            //version with images
            Image tankimg;
            String imagename="src/main/Images/tank"+this.map.tankAt(tankPosition).getDirection().toString()+".png";
            try {
                tankimg = ImageIO.read(new File(imagename));
            } catch (IOException e){
                System.out.println("NOT LOADED");
                tankimg=null;
            }
            tankimg=tankimg.getScaledInstance((int)relwidth,(int)relheight,Image.SCALE_AREA_AVERAGING);
            g2D.drawImage(tankimg,(int)relx,(int)rely,this);
        }
        //drawing missiles
        List<Vector2d> missilePositions=this.map.getMissilePositions();
        for(Vector2d position: missilePositions)
        {
            g2D.setPaint(new Color(76, 76, 76));

            float relx=(((float)position.x/(float) mapSize[0])*this.getWidth())+relwidth/3;
            float rely=(float)(this.getHeight())- (((float)position.y/(float) mapSize[1])*this.getHeight())- relheight+ relheight/3;

            g2D.fillOval((int)relx,(int)rely,(int)relwidth/3,(int)relheight/3);
        }

        List<Vector2d> wallPositions=this.map.getWallPositions();
        g2D.setPaint(new Color(45, 20, 20));
        //drawing walls
        for(Vector2d wallPosition:wallPositions)
        {
            float relx=(((float)wallPosition.x/(float) mapSize[0])*this.getWidth());
            //looks a bit complicated because of how Swing is using coordinates to draw
            float rely=(float)(this.getHeight())- (((float)wallPosition.y/(float) mapSize[1])*this.getHeight())- relheight;
            g2D.fillRect((int)relx,(int)rely,(int)relwidth,(int)relheight);

            if(this.map.getWallAt(wallPosition).getHealthPoints()==1)
            {
                g2D.setPaint(new Color(255, 255, 255));
                g2D.drawLine((int)relx,(int)rely,(int)relwidth+(int)relx,(int)relheight+(int)rely);
                g2D.setPaint(new Color(45, 20, 20));
            }
        }
        //drawing HP bar
        drawHPBar(g2D);


    }

    public void refresh() {
        this.revalidate();
        this.repaint();
    }

    private void drawHPBar(Graphics2D g2D)
    {
        int[] mapSize=this.map.getSize();
        Vector2d position=playerTank.getPosition();

        float relwidth= (float) (this.getWidth())/(float)(mapSize[0]);
        float relheight= (float) (this.getHeight())/(float)(mapSize[1]);
        float actualheight=relheight*0.1f;
        float relx=(((float)position.x/(float) mapSize[0])*this.getWidth());
        float rely=(float)(this.getHeight())- (((float)position.y/(float) mapSize[1])*this.getHeight())-relheight;
        g2D.setPaint(new Color(200,0,0));
        g2D.fillRect((int)relx, (int)rely, (int) relwidth, (int)actualheight);

        float ratio=(float) playerTank.getHealthPoints()/(float) initialHP;
        g2D.setPaint(new Color(0,255,0));
        g2D.fillRect((int)relx, (int)rely, (int) (relwidth*ratio), (int)actualheight);

    }
}
