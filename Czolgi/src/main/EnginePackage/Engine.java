package EnginePackage;

import MapAndMoving.RectangularMap;
import TankPackage.PlayerTank;

public class Engine {
    private RectangularMap rectangularMap;
    private PlayerTank playerTank;
    private int maxTankWait;
    private int maxWallWait;
    private int tankTurn=0;
    private int wallTurn=0;
    public Engine(RectangularMap rectangularMap, PlayerTank playerTank,int maxTankWait, int maxWallWait)
    {
        this.maxTankWait=maxTankWait;
        this.maxWallWait=maxWallWait;
        this.rectangularMap=rectangularMap;
        this.playerTank=playerTank;
    }
    //will return false if player is dead
    public boolean run()
    {
        if(playerTank.getHealthPoints()<=0)
        {
            return false;
        }
        rectangularMap.moveAllMissiles();
        rectangularMap.allTanksTakeAction();
        boolean isalive=rectangularMap.removeTheDead();
        if (!isalive)
        {
            return false;
        }
        tankTurn+=1;
        wallTurn+=1;

        tankTurn=tankTurn%maxTankWait;
        wallTurn=wallTurn%maxWallWait;

        if(wallTurn==0)
        {
            rectangularMap.spawnWall();
        }
        if (tankTurn==0)
        {
            rectangularMap.spawnEnemy(playerTank);
        }
        if(rectangularMap.getTankPositions().size()<=1)
        {
            rectangularMap.spawnEnemy(playerTank);
        }
        return true;

    }
}
