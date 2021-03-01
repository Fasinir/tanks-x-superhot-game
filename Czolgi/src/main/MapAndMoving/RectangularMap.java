package MapAndMoving;

import Interfaces.IWorldMap;
import MapVisualizing.MapVisualizer;
import TankPackage.AbstractTank;
import Interfaces.IPositionChangeObserver;
import TankPackage.EnemyTank;
import TankPackage.Missile;
import TankPackage.PlayerTank;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class RectangularMap implements IPositionChangeObserver, IWorldMap {
    private int width;
    private int height;


    private int points=0;
    private PlayerTank playerTank;
    private Map<Vector2d, AbstractTank> tankMap=new LinkedHashMap<>();
    private Map<Vector2d, Missile> missileMap=new LinkedHashMap<>();
    private Map<Vector2d,Wall> wallMap=new LinkedHashMap<>();

    //private Map<Vector2d,Animal> animals = new LinkedHashMap<>();

    public RectangularMap(int width,int height)
    {

        this.height=height;
        this.width=width;



    }
    public boolean canMoveTo(Vector2d position)
    {
        int x1=position.x;
        int y1=position.y;
        if(x1<width && y1<height && x1>=0 && y1>=0) {
            if(!wallMap.containsKey(position) && !tankMap.containsKey(position))
            {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean place(Object obj) {
        if (obj instanceof AbstractTank)
        {
            return placeTank((AbstractTank) obj);
        }
        else if(obj instanceof Missile)
        {
            return placeMissile((Missile) obj);
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position) {
        return true;
    }

    @Override
    public Object objectAt(Vector2d position) {
        if (tankMap.containsKey(position))
        {
            return tankMap.get(position);
        }
        else if(missileMap.containsKey(position))
        {
            return missileMap.get(position);
        }
        return null;
    }

    public boolean placeTank(AbstractTank tank)
    {
        this.tankMap.put(tank.getPosition(),tank);

        if(missileMap.containsKey(tank.getPosition()))
        {

            tank.takeDamage();
            missileMap.remove(tank.getPosition());
        }

        return true;
    }
    public boolean placeMissile(Missile missile)
    {
        Vector2d missilePosition= missile.getPosition();
        if (outOfMap(missilePosition)||missileMap.containsKey(missilePosition)
        ||tankMap.containsKey(missilePosition)||wallMap.containsKey(missilePosition))
        {
            if(tankMap.containsKey(missilePosition))
            {
                tankMap.get(missilePosition).takeDamage();
            }
            if(wallMap.containsKey(missilePosition))
            {
                wallMap.get(missilePosition).takeDamage();
            }
            missileMap.remove(missilePosition);

            return true;
        }
        else
        {
            missileMap.put(missilePosition,missile);
            return true;
        }

    }
    public void placeWall(Wall wall)
    {
        wallMap.put(wall.getPosition(),wall);
    }

  //  public Object objectAt(Vector2d position)
   // {
   //     return animals.get(position);
   // }
    /*
    public String toString()
    {
        MapVisualizer mv= new MapVisualizer(this);
        String res=mv.draw(new Vector2d(0,0),new Vector2d(this.MaxDrawWidth,this.MaxDrawHeight));
        return res;
    }*/
    // private void update(Vector2d newposition)
    //  {
    //      //AnimalVecList.set(newposition);
    //  }
    public int[] getSize()
    {
        return new int[]{this.width,this.height};
    }

    public void positionChanged(Vector2d oldPosition, Vector2d newPosition, Object obj) {
    if (obj instanceof AbstractTank)
    {
        tankMap.remove(oldPosition);
        placeTank((AbstractTank) obj);
    }
    else if (obj instanceof Missile)
    {
        missileMap.remove(oldPosition);
        placeMissile((Missile) obj);

    }
    }

    public List<Vector2d> getTankPositions()
    {
        List<Vector2d> result=new ArrayList<>();
        result.addAll(tankMap.keySet());
        return result;
    }

    public List<Vector2d> getMissilePositions()
    {
        List<Vector2d> result=new ArrayList<>();
        result.addAll(missileMap.keySet());
        return result;
    }

    public List<Vector2d> getWallPositions()
    {
        List<Vector2d> result=new ArrayList<>();
        result.addAll(wallMap.keySet());
        return result;
    }

    public String toString()
    {
        MapVisualizer mv= new MapVisualizer(this);
        String res=mv.draw(new Vector2d(0,0),new Vector2d(this.width-1,this.height-1));
        return res;
    }

    public void moveAllMissiles()
    {
        List<Missile> missiles=new ArrayList<>(this.missileMap.values());

        for(Missile missile:missiles)
        {
            this.missileMap.remove(missile.getPosition());
        }
        for(Missile missile:missiles)
        {
            missile.move();
        }

    }
    //returns false if player is dead
    public boolean removeTheDead()
    {
        List<AbstractTank> tankList=new ArrayList<>(this.tankMap.values());
        for (int i=0;i<tankList.size();i++)
        {
            AbstractTank tank=tankList.get(i);
            if(tank.getHealthPoints()<=0)
            {
                if(tank instanceof PlayerTank)
                {
                    //gameover
                    return false;
                }
                this.tankMap.remove(tank.getPosition());
                points+=1;
            }
        }
        List<Wall>wallList=new ArrayList<>(this.wallMap.values());
        for (int i=0;i<wallList.size();i++)
        {
            Wall wall=wallList.get(i);
            if(wall.getHealthPoints()<=0)
            {
                this.wallMap.remove(wall.getPosition());
            }
        }
        return true;
    }
    public void allTanksTakeAction()
    {
        List<AbstractTank> tankList=new ArrayList<>(this.tankMap.values());
        for (int i=0;i<tankList.size();i++)
        {
            AbstractTank tank=tankList.get(i);
            if (tank instanceof EnemyTank)
            {
                ((EnemyTank) tank).takeAction();
            }
        }
    }
    public void spawnEnemy(PlayerTank player)
    {
        int x= ThreadLocalRandom.current().nextInt(0,this.width);
        int y= ThreadLocalRandom.current().nextInt(0,this.height);
        Vector2d position= new Vector2d(x,y);
        if(!tankMap.containsKey(position) && !missileMap.containsKey(position) && !inCentralSquare(position)
        && !wallMap.containsKey(position))
        {
            new EnemyTank(this,position,player);
        }
    }

    private boolean inCentralSquare(Vector2d position)
    {
        if(position.x>=playerTank.getPosition().x-2 && position.x<=playerTank.getPosition().x+2
        && position.y>=playerTank.getPosition().y-2 && position.y<=playerTank.getPosition().y+2)
        {
            return true;
        }
        return false;
    }

    private boolean outOfMap(Vector2d position)
    {
        if (position.x<0 || position.x>=this.width || position.y<0 || position.y>=this.height)
        {
            return true;
        }
        return false;
    }
    public void spawnWall()
    {
        int x= ThreadLocalRandom.current().nextInt(0,this.width);
        int y= ThreadLocalRandom.current().nextInt(0,this.height);
        Vector2d position= new Vector2d(x,y);
        if(!tankMap.containsKey(position)
                && !missileMap.containsKey(position)
                && !inCentralSquare(position)
                && !wallMap.containsKey(position))
        {
            wallMap.put(position,new Wall(position));
        }
    }
    public Wall getWallAt(Vector2d position)
    {
        return this.wallMap.get(position);
    }
    public AbstractTank tankAt(Vector2d position)
    {
        return this.tankMap.get(position);
    }

    public int getPoints() {
        return points;
    }

    public void setPlayerTank(PlayerTank playerTank) {
        this.playerTank = playerTank;
    }
}
