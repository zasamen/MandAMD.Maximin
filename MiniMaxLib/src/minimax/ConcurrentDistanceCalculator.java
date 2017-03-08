package minimax;

import kmeans.Cluster;
import kmeans.Point;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * concurrent calculator
 * Created by zasam on 19.02.2017.
 */
public class ConcurrentDistanceCalculator extends DistanceCalculator {

    public ConcurrentDistanceCalculator() {
        this(Executors.newCachedThreadPool());
    }


    public ConcurrentDistanceCalculator(ExecutorService executorService) {
        super();
        service = executorService;
    }

    @Override
    public PointPair getTheMostDistantPoint(List<Cluster> clusters)
            throws InterruptedException, ExecutionException {
        return getDistantPoint(invokePointSearchers(createPointSearchers(clusters)));
    }

    private ExecutorService service;

    private PointPair getDistantPoint(List<Future<PointPair>> futures)
            throws ExecutionException, InterruptedException {
        final Point point = Point.MAX_POINT;
        PointPair max = new PointPair(point, point);
        for (Future<PointPair> pointPairFuture : futures) {
            max = max.compareTo(pointPairFuture.get()) > 0 ? max : pointPairFuture.get();
        }
        return max;
    }

    @Override
    public void shutdown() {
        super.shutdown();
        service.shutdown();
    }

    @NotNull
    private List<Future<PointPair>> invokePointSearchers
            (List<Callable<PointPair>> pairs) throws InterruptedException {
        return service.invokeAll(pairs, 20, TimeUnit.SECONDS);
    }

    private List<Callable<PointPair>> createPointSearchers
            (List<Cluster> clusters) {
        List<Callable<PointPair>> list = new LinkedList<>();
        for (Cluster cluster : clusters) {
            list.add(createSearcher(cluster));
        }
        return list;
    }

    @NotNull
    private Callable<PointPair> createSearcher(Cluster cluster) {
        return () -> getTheMostDistantPoint(cluster);
    }
}
