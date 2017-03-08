package minimax;

import kmeans.Cluster;
import kmeans.Point;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by zasam on 19.02.2017.
 */
public class PointPair implements Comparable<PointPair>{
    protected Point point;
    protected Point core;
    protected double distance;

    public PointPair(Point point, Point core) {
        this.point = point;
        this.core = core;
        this.distance = core.calculateDistance(point);
    }

    public Point getPoint() {
        return point;
    }

    public double getDistance() {
        return distance;
    }

    public Point getCore() {
        return core;
    }


    @Override
    public String toString() {
        return "point=" + point +
                ", core=" + core +
                ", distance=" + distance +
                '}';
    }

    @Override
    public int compareTo(@NotNull PointPair o) {
        return (int) (1 / (distance - o.distance));
    }
}
