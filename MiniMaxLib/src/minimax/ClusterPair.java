package minimax;

import kmeans.Cluster;
import com.sun.istack.internal.NotNull;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**

 * Created by zasam on 19.02.2017.
 */
public class ClusterPair implements Comparable<ClusterPair>{
    @NotNull
    private Cluster cluster, otherCluster;

    private ClusterPair(Cluster cluster, Cluster otherCluster) {
        this.cluster = cluster;
        this.otherCluster = otherCluster;
    }

    private double getDistance() {
        return Math.sqrt(cluster.getCore().calculateDistance(otherCluster.getCore()));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (((!(o instanceof ClusterPair)))) return false;
        ClusterPair that = (ClusterPair) o;
        return !(cluster == null || otherCluster == null)
                && (cluster.equals(that.cluster)
                && otherCluster.equals(that.otherCluster)
                || (cluster.equals(that.otherCluster)
                && otherCluster.equals(that.cluster)));
    }

    @Override
    public int hashCode() {
        int result;
        result = cluster != null ? cluster.hashCode() : 0;
        result = 31 * result + (otherCluster != null ? otherCluster.hashCode() : 0);
        return result;
    }


    public static double getAverageDistance(List<Cluster> clusterList) {
        Set<ClusterPair> set = createDistanceSet(clusterList);
        return set.isEmpty() ? 0 : getSumDistanceFromSet(set) / set.size();
    }

    private static Set<ClusterPair> createDistanceSet(List<Cluster> list) {
        Set<ClusterPair> set = new TreeSet<>();
        for (Cluster c : list) {
            for (Cluster c1 : list) {
                if (c != c1) {
                    set.add(new ClusterPair(c, c1));
                }
            }
        }
        return set;
    }

    private static double getSumDistanceFromSet(Set<ClusterPair> set) {
        double result = 0;
        for (ClusterPair clusterPair :
                set) {
            result += clusterPair.getDistance();
        }
        return result;
    }

    @Override
    public int compareTo(@org.jetbrains.annotations.NotNull ClusterPair o) {
        return (int) (1 / (o.cluster.getCore().calculateDistance(cluster.getCore())
                - o.otherCluster.getCore().calculateDistance(otherCluster.getCore())));
    }
}