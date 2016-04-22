/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.gruppeseks.aifov;

import dk.gruppeseks.bodtrd.common.data.Entity;
import dk.gruppeseks.bodtrd.common.data.Path;
import dk.gruppeseks.bodtrd.common.data.World;
import dk.gruppeseks.bodtrd.common.data.entityelements.AIData;
import dk.gruppeseks.bodtrd.common.data.entityelements.Body;
import dk.gruppeseks.bodtrd.common.data.entityelements.Position;
import dk.gruppeseks.bodtrd.common.data.util.Vector2;
import dk.gruppeseks.bodtrd.common.exceptions.NoPathException;
import dk.gruppeseks.bodtrd.common.services.AISPI;
import java.util.ArrayList;
import java.util.List;
import org.openide.util.lookup.ServiceProvider;

/**
 *
 * @author Morten
 */
@ServiceProvider(service = AISPI.class)
public class AIFoVProvider implements AISPI
{
    private World _world;
    private final int RAY_COUNT = 100;
    private final int VISION_DISTANCE = 350;
    private final double VISION_DEGREES = 90;
    private final int DUMB_AI_RANGE = 80;

    @Override
    public Path getPath(Entity entity, Entity target, World world) throws NoPathException
    {
        List<Position> path = new ArrayList<>();
        _world = world;
        Position targetPos = rayCast(entity, target); // Providing the point on the circle it collided would result in better AI.

        if (targetPos == null)
        {

        }
        else
        {
            Vector2 distance = new Vector2(entity.get(Position.class), target.get(Position.class));
            if (distance.getMagnitude() < DUMB_AI_RANGE)
            {
                path.add(new Position(target.get(Position.class))); // doesnt work as intended
            }
            else
            {
                path.add(targetPos);
            }
        }

        Position[] pathArray = new Position[path.size()];
        for (int i = 0; i < path.size(); i++)
        {
            pathArray[i] = path.get(i);
        }
        return new Path(pathArray);
    }

    private Position rayCast(Entity zombie, Entity target)
    {
        ArrayList<Float> shape = new ArrayList<>();
        double rotSteps = VISION_DEGREES / (double)RAY_COUNT; // 1.5
        Vector2 orientation = zombie.get(Body.class).getOrientation();
        if (orientation == null)
        {
            orientation = new Vector2(0, 1);
        }
        Vector2 startRay = new Vector2(orientation);
        startRay.rotateDegrees(-VISION_DEGREES / 2);

        Position targetPos = target.get(Position.class);
        Body targetBod = target.get(Body.class);
        Position targetCenter = new Position(targetPos.getX() + targetBod.getWidth() / 2, targetPos.getY() + targetBod.getHeight() / 2);
        Position p = zombie.get(Position.class);
        Body b = zombie.get(Body.class);

        Position zombieCenter = new Position(p.getX() + b.getWidth() / 2, p.getY() + b.getHeight() / 2);

        boolean playerCollision = false;
        for (int i = 0; i < RAY_COUNT; i++)
        {
            double rotAmount = rotSteps * i;
            Vector2 ray = new Vector2(startRay);
            ray.setMagnitude(VISION_DISTANCE);
            ray.rotateDegrees(rotAmount);
            Position collision = getFirstCollision(ray, zombie.get(Position.class));
            if (!playerCollision && lineCircleCollision(targetCenter, target.get(Body.class), zombieCenter, collision))
            {
                playerCollision = true;
            }
            shape.add((float)collision.getX());
            shape.add((float)collision.getY());
        }

        shape.add((float)zombieCenter.getX());
        shape.add((float)zombieCenter.getY());

        float[] floatShape = new float[shape.size()];
        for (int i = 0; i < shape.size(); i++)
        {
            floatShape[i] = shape.get(i);
        }
        AIData aiData = zombie.get(AIData.class);
        aiData.setFoVShape(floatShape);
        if (playerCollision)
        {
            return targetCenter;
        }
        return null;

    }

    private Position getFirstCollision(Vector2 ray, Position start)
    {
        boolean[][] grid = _world.getMap().getGrid();
        int cellSize = _world.getMap().getGridCellSize();

        for (int i = 0; i < ray.getMagnitude(); i += 2)
        {
            Vector2 v = new Vector2(ray);
            v.setMagnitude(i);
            Position p = new Position(start, v);

            int x = (int)p.getX() / cellSize;
            int y = (int)p.getY() / cellSize;
            if (x >= 0 && y >= 0 && x < grid.length && y < grid[0].length && !grid[x][y])
            {
                return p;
            }
        }
        return new Position(start, ray);    //No collision
    }

    private boolean lineCircleCollision(Position circleCen, Body circleBody, Position start, Position end)
    {
        Vector2 vec = new Vector2(circleCen, start);
        double y0 = circleCen.getY();
        double y1 = start.getY();
        double y2 = end.getY();
        double x0 = circleCen.getX();
        double x1 = start.getX();
        double x2 = end.getX();

        double distanceToLine = (Math.abs((y2 - y1) * x0 - (x2 - x1) * y0 + x2 * y1 - y2 * x1)) / (Math.sqrt(Math.pow(y2 - y1, 2) + Math.pow(x2 - x1, 2)));
        Vector2 startToTarget = new Vector2(new Position(x1, y1), new Position(x0, y0));
        Vector2 endToTarget = new Vector2(new Position(x2, y2), new Position(x0, y0));
        Vector2 endToStart = new Vector2(new Position(x2, y2), new Position(x1, y1));
        double rayLength = endToStart.getMagnitude();
        double radius = circleBody.getWidth() / 2;
        if (distanceToLine < radius && startToTarget.getMagnitude() < radius + rayLength && endToTarget.getMagnitude() < radius + rayLength)
        {
            return true;
        }
        return false;
    }

}
