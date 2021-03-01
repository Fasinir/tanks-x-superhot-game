package TankPackage;

import Interfaces.IPositionChangeObserver;
import MapAndMoving.MapDirection;
import MapAndMoving.RectangularMap;
import MapAndMoving.Vector2d;

import java.util.ArrayList;
import java.util.List;

public class Missile {
    private MapDirection direction;
    private Vector2d position;
    private RectangularMap map;
    private List<IPositionChangeObserver> observers=new ArrayList<>();
    public Missile(RectangularMap map, MapDirection direction, Vector2d position)
    {
        this.map=map;
        this.direction=direction;
        this.position=position;
        addObserver(this.map);
        positionChanged(new Vector2d(-1,-1));
    }

    public Vector2d getPosition() {
        return position;
    }

    public void addObserver(IPositionChangeObserver obs)
    {
        observers.add(obs);
    }
    public void removeObserver(IPositionChangeObserver obs)
    {
        observers.remove(obs);
    }
    private void positionChanged(Vector2d oldPosition)
    {
        for(IPositionChangeObserver observer: observers)
        {
            observer.positionChanged(oldPosition,this.getPosition(),this);
        }
    }
    public void move()
    {
        Vector2d tmp=this.position;
        this.position=this.position.add(direction.toUnitVector());
        positionChanged(tmp);
    }
    public String toString()
    {
        return "o";
    }

}
