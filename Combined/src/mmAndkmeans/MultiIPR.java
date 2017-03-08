package mmAndkmeans;

import kmeans.Cluster;
import kmeans.IterativePatternRecognition;
import kmeans.KMeans;
import kmeans.Point;
import minimax.MiniMaxAlgorithm;

import java.util.List;

public class MultiIPR implements IterativePatternRecognition {

    private KMeans kMeans;
    private MiniMaxAlgorithm miniMaxAlgorithm;
    private Point[] points;

    @Override
    public boolean isHaveNextIteration() {
        return miniMaxAlgorithm.isHaveNextIteration() || kMeans == null || kMeans.isHaveNextIteration();
    }

    @Override
    public void shutdown() throws InterruptedException {
        miniMaxAlgorithm.shutdown();
        if (kMeans != null) kMeans.shutdown();
    }

    @Override
    public int getIterationIndex() {
        return kMeans == null ? 0 : kMeans.getIterationIndex();
    }

    @Override
    public List<Cluster> call() throws Exception {
        if (miniMaxAlgorithm.isHaveNextIteration()) {
            return miniMaxAlgorithm.call();
        } else if (kMeans == null) {
            kMeans = new KMeans(points, getCoresFromAlgorithm());
            Thread.sleep(20000);
        }
        return kMeans.isHaveNextIteration() ? kMeans.call() : null;
    }

    private Point[] getCoresFromAlgorithm() {
        List<Cluster> clusters = miniMaxAlgorithm.getClusters();
        Point[] cores = new Point[clusters.size()];
        int i = 0;
        for (Cluster cluster : clusters) {
            cores[i] = cluster.getCore();
            i++;
        }
        return cores;
    }

    public MultiIPR(Point[] points, Point ...cores) {
        this.points = points;
        miniMaxAlgorithm = new MiniMaxAlgorithm(points, cores[0]);
//        kMeans = new KMeans(points,cores);
    }
}
