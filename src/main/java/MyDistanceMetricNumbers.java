import org.christopherfrantz.dbscan.DistanceMetric;

public class MyDistanceMetricNumbers implements DistanceMetric<Double> {
    public MyDistanceMetricNumbers() {
    }

    public double calculateDistance(Double val1, Double val2) {
        return Math.abs(val1.doubleValue() - val2.doubleValue());
    }
}