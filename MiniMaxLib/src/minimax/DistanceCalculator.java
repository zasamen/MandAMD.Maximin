package minimax;

import kmeans.Cluster;
import kmeans.Point;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 *
 * Created by zasam on 19.02.2017.
 */
public class DistanceCalculator {

    public DistanceCalculator() {
    }

    public void shutdown(){}

    protected PointPair getTheMostDistantPoint(Cluster cluster) {
        final Point core = cluster.getCore();
        PointPair max = new PointPair(core, core);
        for (Point point : cluster.getImages()) {
            PointPair that = new PointPair(point, core);
            max = (that.compareTo(max) > 0) ? that : max;
        }
        return max;
    }

    public PointPair getTheMostDistantPoint(List<Cluster> clusters)
            throws InterruptedException, ExecutionException {
        PointPair max =new PointPair(Point.MAX_POINT,Point.MAX_POINT);
        for (Cluster cluster :clusters) {
            PointPair that = getTheMostDistantPoint(cluster);
            max = max.compareTo(that) > 0 ? max : that;
        }
        return max;
    }


}
