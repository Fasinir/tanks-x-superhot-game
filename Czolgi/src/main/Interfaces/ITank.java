package Interfaces;

import MapAndMoving.MoveDirection;
import MapAndMoving.Vector2d;

public interface ITank {
    void fire();
    void move(MoveDirection moveDirection);
    void takeDamage();
    Vector2d getPosition();
}
