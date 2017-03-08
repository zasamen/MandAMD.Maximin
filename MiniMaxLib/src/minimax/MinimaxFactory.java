package minimax;

import kmeans.IPRFactory;
import kmeans.IterativePatternRecognition;
import kmeans.Point;

/**
 * Created by zasam on 20.02.2017.
 */
public class MinimaxFactory implements IPRFactory {
    @Override
    public IterativePatternRecognition createIPR(Point[]... points) {
        return new MiniMaxAlgorithm(points[0],points[1][0]);
    }
}
