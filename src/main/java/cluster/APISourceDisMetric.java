package cluster;

import org.christopherfrantz.dbscan.DistanceMetric;

public class APISourceDisMetric implements DistanceMetric<APIPoint> {
    public APISourceDisMetric(){

    }
    public double calculateDistance(APIPoint val1, APIPoint val2) {
        return val1.getDis(true,val2);
    }
}
