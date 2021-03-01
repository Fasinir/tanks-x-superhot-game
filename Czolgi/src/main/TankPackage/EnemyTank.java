package TankPackage;

import MapAndMoving.MoveDirection;
import MapAndMoving.RectangularMap;
import MapAndMoving.Vector2d;

import java.util.concurrent.ThreadLocalRandom;

public class EnemyTank extends AbstractTank{
    PlayerTank player;
    public EnemyTank(RectangularMap rectangularMap,Vector2d position,PlayerTank player) {
        super(rectangularMap,1,position);
        this.player=player;
    }
    public void takeAction()
    {
        Vector2d tmp=super.position.closestToUnitVector(player.getPosition());
        super.direction=tmp.fromUnitVector();
        int x= ThreadLocalRandom.current().nextInt(0,1000);
        if(x>500)
        {
            fire();
        }
        else
        {
            this.move(MoveDirection.FORWARD);
        }
    }



}
