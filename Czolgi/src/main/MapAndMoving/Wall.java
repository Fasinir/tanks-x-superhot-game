package MapAndMoving;

public class Wall {
    private Vector2d position;
    private int healthpoints=2;
    public Wall(Vector2d position)
    {
        this.position=position;
    }

    public Vector2d getPosition() {
        return position;
    }

    public int getHealthPoints() {
        return healthpoints;
    }
    public void takeDamage()
    {
        healthpoints-=1;
    }
}
