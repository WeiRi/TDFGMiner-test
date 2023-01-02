package api;

import api.filter.Filter;
import cluster.APIPatternMiner;
import com.google.common.util.concurrent.UncheckedExecutionException;
import common.skeleton.visitor.Printer;
import de.parsemis.graph.Graph;
import de.parsemis.miner.environment.Settings;
import de.parsemis.miner.general.Fragment;
import de.parsemis.parsers.LabelParser;
import javaimpl.DFG2Pattern;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.christopherfrantz.dbscan.DBSCANClusteringException;
import org.jetbrains.annotations.Nullable;
import utils.GraphUtil;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javaimpl.dfg.*;

@Slf4j
@AllArgsConstructor
public class PatternMiner<Data, N, E, G extends Graph<N, E>, R> {
    private final Data source;
    private final GraphGenerator<Data, G> graphGenerator;
    //存储codeuntil等信息，用于将图转换为代码骨架
    private final PatternGenerator<N, E, G, R> patternGenerator;
    private final LabelParser<N> nodeParser;
    private final LabelParser<E> edgeParser;
    @Nullable
    private final Filter<List<Graph<N, E>>> resultFilter;
    private final String resultfilename;

    //挖掘的主程序，首先转换语料库为DFG，再使用gSpan算法进行挖掘，过滤掉一些被包含的子图之后，再返回频繁子图集合
    public Collection<R> process(MinerSetting minerSetting) {
        var graphs = graphGenerator.generate(source);
//        System.out.println(String.format("总数据流图数: %d", graphs.size()));
//        graphs.forEach(GraphUtil::print);
        //存储节点为parsemis包中ListGraph下的MyNode，边为MyEdge
        //MyNode.degree：0，边总数；1，入边；2，出边
        //MyNode.myedges:依次存储边的序号，对应MyEdge。注意边的总数与degree[0]保持一致
        var testgraph=graphs.stream().filter(g->g.getNodeCount()>0).collect(Collectors.toList());
        var dfg2Pattern=(DFG2Pattern)patternGenerator;
        var patterns=testgraph.stream().map(g-> {
            try {
                return new APIPatternMiner(dfg2Pattern.getCodeUtil(),(DFG)g).miner();
            } catch (DBSCANClusteringException e) {
                e.printStackTrace();
                return Stream.of();
            }
        }).distinct().collect(Collectors.toList());
        try {
            PrintWriter pw=new PrintWriter(resultfilename);
            patterns.forEach(p -> {
                ArrayList<Pattern> nowpatterns= (ArrayList<Pattern>) p;
                System.out.println("----------");
                System.out.println(p.toString());
                if(p!=null)
                    pw.println(p.toString());
            });
            pw.close();
        }catch (UncheckedExecutionException | FileNotFoundException e){
            System.out.println("unchecked exception");
        }
        //TODO:添加图中API节点计算，需要找到为METHOD_INVOCATION的节点，
        System.out.println("test out");
        var freqGraphs = mine(graphs, minerSetting.toParsemisSettings(nodeParser, edgeParser)).stream().map(Fragment::toGraph).collect(Collectors.toList());
        System.out.println("end mining!");
        var filtered = resultFilter == null ? freqGraphs : resultFilter.process(freqGraphs);
        System.out.println(String.format("频繁子图数: %d", filtered.size()));
        filtered.forEach(GraphUtil::print);
        //截取前5个子图
        int last_index=15;
        if(filtered.size()<15)
            last_index=filtered.size();
        var filtered_top5=filtered.subList(0,last_index);
        return filtered_top5.stream().map(r ->patternGenerator.generate(graphs, r)).collect(Collectors.toList());
    }

    //调用gspan算法进行挖掘
    @SuppressWarnings("unchecked")
    private Collection<Fragment<N, E>> mine(Collection<? extends Graph<N, E>> dfgs, Settings<N, E> settings) {
        System.out.println("start mining!");
        return de.parsemis.Miner.mine((Collection<Graph<N, E>>) dfgs, settings);
    }
}
