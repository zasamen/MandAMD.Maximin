package minimax;

import com.sun.istack.internal.NotNull;
import kmeans.*;
import org.jetbrains.annotations.Contract;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MiniMaxAlgorithm implements IterativePatternRecognition{


    private final List<Cluster> clusters;
    private final Point[] points;
    private final DistanceCalculator calculator;
    private final PointRouter router;
    private boolean haveNextIteration;
    private int totalPoints;


    public List<Cluster> getClusters() {
        return clusters;
    }

    public Point[] getPoints() {
        return points;
    }

    public MiniMaxAlgorithm(Point[] points, Point core) {
        this.points= points;
        totalPoints = points.length;
        haveNextIteration = true;

        ExecutorService service = Executors.newCachedThreadPool();
        router = new ConcurrentPointRouter(service);
//        router = new PointRouter();
        calculator = new ConcurrentDistanceCalculator(service);
//        calculator = new DistanceCalculator();
        clusters = new LinkedList<>();
        clusters.add(createCluster(core));
    }


    @Override
    public boolean isHaveNextIteration() {
        return haveNextIteration;
    }

    @Override
    public void shutdown() throws InterruptedException {
        calculator.shutdown();
    }

    @Override
    public int getIterationIndex() {
        return 0;
    }

    @Override
    public List<Cluster> call() throws Exception {
        try {
            calculateIterationResult();
        }catch (InterruptedException | ExecutionException ie) {
            ie.printStackTrace();
        }
        return clusters;
    }

    private void calculateIterationResult() throws InterruptedException,ExecutionException{
        clearClusters();
        router.movePointsToClusters(points,clusters);
        PointPair pair =calculator.getTheMostDistantPoint(clusters);
        createCoreIfShould(pair);
    }

    private void clearClusters(){
        clusters.forEach(Cluster::clear);
    }

    private void createCoreIfShould(PointPair pair) {
        boolean should = checkNewCoreExists(pair.getDistance());
        if (should) clusters.add(createCluster(pair.getPoint()));
        haveNextIteration = should;
    }

    private boolean checkNewCoreExists(double distance) {
        double averageDistance = ClusterPair.getAverageDistance(clusters) / 2;
        averageDistance*=averageDistance;
        return distance > averageDistance;
    }


    @Contract("_ -> !null")
    private Cluster createCluster(@NotNull Point point) {
        return new Cluster(point, (int) (totalPoints / ((double) clusters.size()+1) * 1.5));
    }

}
