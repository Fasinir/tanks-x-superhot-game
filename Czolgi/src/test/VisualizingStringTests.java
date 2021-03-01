import MapAndMoving.MapDirection;
import MapAndMoving.MoveDirection;
import MapAndMoving.RectangularMap;
import MapAndMoving.Vector2d;
import TankPackage.Missile;
import TankPackage.PlayerTank;
import org.junit.Assert;
import org.junit.Test;

public class VisualizingStringTests {
    @Test
    public void TankMovingTest()
    {
        RectangularMap rectangularMap=new RectangularMap(5,5);
        PlayerTank playerTank= new PlayerTank(rectangularMap,10, new Vector2d(3,3));
        //MainFrame mainFrame= new MainFrame(rectangularMap);
        System.out.println(rectangularMap.toString());
        //playerTank.fire();
        System.out.println(rectangularMap.toString());

        playerTank.move(MoveDirection.FORWARD);

        System.out.println(rectangularMap.toString());

        playerTank.move(MoveDirection.RIGHT);
        playerTank.move(MoveDirection.RIGHT);
        //playerTank.fire();

        playerTank.move(MoveDirection.FORWARD);

        System.out.println(rectangularMap.toString());

        playerTank.move(MoveDirection.FORWARD);

        System.out.println(rectangularMap.toString());

        playerTank.move(MoveDirection.FORWARD);

        System.out.println(rectangularMap.toString());



    }
    @Test
    public void MissileMovingTest()
    {
        RectangularMap rectangularMap=new RectangularMap(5,5);
        PlayerTank playerTank= new PlayerTank(rectangularMap,10, new Vector2d(3,3));
        //MainFrame mainFrame= new MainFrame(rectangularMap);
        System.out.println(rectangularMap.toString());
        playerTank.fire();
        System.out.println(rectangularMap.toString());
        playerTank.fire();
        System.out.println(rectangularMap.toString());

        Missile missile= new Missile(rectangularMap, MapDirection.EAST,new Vector2d(0,0));
        System.out.println(rectangularMap.toString());
        missile.move();

        System.out.println(rectangularMap.toString());
        missile.move();

        System.out.println(rectangularMap.toString());
        missile.move();

        System.out.println(rectangularMap.toString());
        missile.move();

        System.out.println(rectangularMap.toString());
        missile.move();

        System.out.println(rectangularMap.toString());
        missile.move();

    }

    @Test
    public void TakingDamageTest()
    {
        RectangularMap rectangularMap=new RectangularMap(5,5);
        PlayerTank playerTank= new PlayerTank(rectangularMap,10, new Vector2d(3,3));
        System.out.println(rectangularMap.toString());


        Missile missile= new Missile(rectangularMap, MapDirection.EAST,new Vector2d(0,3));
        System.out.println(rectangularMap.toString());

        missile.move();
        System.out.println(rectangularMap.toString());

        missile.move();
        System.out.println(rectangularMap.toString());

        missile.move();
        System.out.println(rectangularMap.toString());

        Assert.assertEquals(9,playerTank.getHealthPoints());
    }
}
