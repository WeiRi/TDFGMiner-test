package api;

import de.parsemis.graph.ListGraph.Factory;
import de.parsemis.miner.environment.Settings;
import de.parsemis.miner.environment.Statistics;
import de.parsemis.miner.general.IntFrequency;
import de.parsemis.parsers.LabelParser;
import de.parsemis.strategy.ThreadedDFSStrategy;
import lombok.Builder;

//设置频繁子图挖掘算法的参数
@Builder
public class MinerSetting {
    @Builder.Default private int minFreq = 3;
    @Builder.Default private int minNodes = 7;
    @Builder.Default private boolean closeGraph = true;

    public <N, E> Settings<N, E> toParsemisSettings(LabelParser<N> nodeParser, LabelParser<E> edgeParser) {
        var s = new Settings<N, E>();
        s.minNodes = minNodes;
        s.minFreq = new IntFrequency(minFreq);
        s.algorithm = new de.parsemis.algorithms.gSpan.Algorithm<>();
        s.threadCount = 8;
        s.strategy = new ThreadedDFSStrategy<>(8, new Statistics());
        s.factory = new Factory<>(nodeParser, edgeParser);
        s.closeGraph = closeGraph;
        return s;
    }
}
