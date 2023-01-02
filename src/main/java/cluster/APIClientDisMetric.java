package cluster;

import org.christopherfrantz.dbscan.DistanceMetric;

public class APIClientDisMetric implements DistanceMetric<APIPoint> {
    public APIClientDisMetric(){

    }
    public double calculateDistance(APIPoint val1, APIPoint val2) {
        return val1.getDis(false,val2);
    }
}