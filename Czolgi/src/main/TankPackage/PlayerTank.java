package TankPackage;

import Interfaces.IPositionChangeObserver;
import MapAndMoving.MapDirection;
import MapAndMoving.MoveDirection;
import MapAndMoving.RectangularMap;
import MapAndMoving.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class PlayerTank extends AbstractTank{
    //private RectangularMap map;
    //private Vector2d position;
    //private MapDirection direction=MapDirection.NORTH;
    //private List<IPositionChangeObserver> observers =new ArrayList<>();
    //private int healthPoints=5;

    public PlayerTank(RectangularMap map,int initialHealth,Vector2d position)
    {
        super(map,initialHealth,position);

        /*
        this.map=map;
        this.position=position;
        addObserver(map);
        positionChanged(new Vector2d(-1,-1));

         */
    }
    /*
    private void positionChanged(Vector2d oldPosition)
    {
        for(IPositionChangeObserver observer: observers)
        {
            observer.positionChanged(oldPosition,this.getPosition(),this);
        }

    }

     */
   // @Override
    /*
    public void fire() {
        new Missile(this.map,this.direction,this.position.add(direction.toUnitVector()));
    }

     */

    //@Override
    /*
    public void move(MoveDirection dir)
    {

        switch (dir)
        {
            case LEFT:
                this.direction=direction.previous();
                break;
            case RIGHT:
                this.direction=direction.next();
                break;
            case FORWARD:
                Vector2d tmp = this.direction.toUnitVector();
                if (this.position!=null)
                {
                    tmp=tmp.add(this.position);
                }
                if (this.map.canMoveTo(tmp))
                {

                    Vector2d oldPosition=this.getPosition();
                    this.position=tmp;
                    positionChanged(oldPosition);

                }
                break;
            case BACKWARD:
                Vector2d tmp2=this.direction.toUnitVector();
                tmp2=tmp2.opposite();
                if (this.position!=null)
                {
                    tmp2=tmp2.add(this.position);
                }
                if (this.map.canMoveTo(tmp2))
                {

                    Vector2d oldPosition2=this.getPosition();
                    this.position=tmp2;
                    positionChanged(oldPosition2);

                }
                break;
            default:
                break;

        }
    }

   @Override
    public void takeDamage() {
        healthPoints-=1;
    }

    @Override
    public Vector2d getPosition() {
        return this.position;
    }
    public void addObserver(IPositionChangeObserver obs)
    {
        observers.add(obs);
    }
    public void removeObserver(IPositionChangeObserver obs)
    {
        observers.remove(obs);
    }


    public String toString()
    {
        return "T";
    }

     */
}
