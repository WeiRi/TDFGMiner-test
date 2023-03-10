package api;

import java.util.Collection;

@FunctionalInterface
public interface GraphGenerator<Data, G> {
    Collection<G> generate(Data source);
}
