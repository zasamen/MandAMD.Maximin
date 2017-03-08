package mmAndkmeans;

import kmeans.IPRFactory;
import kmeans.IterativePatternRecognition;
import kmeans.Point;

public class MultiPrFactory implements IPRFactory {
    @Override
    public IterativePatternRecognition createIPR(Point[]... points) {
//        return new MultiIPR(points[0],points[1][0]);
        return new MultiIPR(points[0],points[1]);
    }
}
