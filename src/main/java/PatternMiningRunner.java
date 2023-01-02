import api.MinerSetting;
import api.PatternConfig;
import api.PatternMiner;
import com.google.common.collect.Streams;
import com.google.common.util.concurrent.UncheckedExecutionException;
import com.google.inject.Guice;
import common.skeleton.visitor.Printer;
import de.parsemis.graph.Graph;
import de.parsemis.graph.Node;
import javaimpl.DFG2Pattern;
import javaimpl.JavaDFGGenerator;
import javaimpl.dfg.DFGEdge;
import javaimpl.dfg.DFGFactory;
import javaimpl.dfg.DFGNode;
import kg.CodeUtil;
import kg.KnowledgeGraphSessionFactory;
import lombok.extern.slf4j.Slf4j;
import org.christopherfrantz.dbscan.metrics.DistanceMetricNumbers;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;
import org.christopherfrantz.dbscan.*;

@Slf4j
public class PatternMiningRunner {

    public static void test() throws DBSCANClusteringException {
        double value[][]= {
                new double[] {0.005,     0.182751,  0.1284},
                new double[] {3.65816,   0.29518,   2.123316},
                new double[] {4.1234,    0.2301,    1.8900002}
        };
        ArrayList<ArrayList<Double>> inputvalue=new ArrayList<ArrayList<Double>>();
        for(int i=0;i<3;i++)
        {
            ArrayList<Double> temp=new ArrayList<Double>();
            for(int j=0;j<3;j++)
                temp.add(value[i][j]);
            inputvalue.add(temp);
        }
        ArrayList<Double> temp=new ArrayList<Double>();
        temp.add(1.0);
        temp.add(2.0);
        temp.add(3.0);
        temp.add(3.4);
        temp.add(1.1);
        MyDistanceMetricNumbers metric=new MyDistanceMetricNumbers();
        DBSCANClusterer<Double> mycluster=new DBSCANClusterer<Double>(temp,2,0.5, metric);
        ArrayList<ArrayList<Double>>output=mycluster.performClustering();
        System.out.println("1");
    }
    private static class GraphNodeSetMapping {
        private final Graph<DFGNode, DFGEdge> graph;
        private final Set<DFGNode> nodes;

        private GraphNodeSetMapping(Graph<DFGNode, DFGEdge> graph) {
            this.graph = graph;
            this.nodes = Streams.stream(graph.nodeIterator())
                    .map(Node::getLabel)
                    .filter(l -> l.getType() != DFGNode.Type.TYPE)
                    .collect(Collectors.toSet());
        }
    }

    private static List<Graph<DFGNode, DFGEdge>> resultFilter(List<Graph<DFGNode, DFGEdge>> result) {
        var graphs = new ArrayList<Graph<DFGNode, DFGEdge>>();
        var nodeSets = new ArrayList<Set<DFGNode>>();
        result.stream()
                .map(GraphNodeSetMapping::new)
                .sorted(Comparator.<GraphNodeSetMapping>comparingInt(m -> m.nodes.size()).reversed())
                .forEach(m -> {
                    if (nodeSets.stream().noneMatch(nodeSet -> isSubset(m.nodes, nodeSet))) {
                        graphs.add(m.graph);
                        nodeSets.add(m.nodes);
                    }
                });
        return graphs;
    }

    private static boolean isSubset(Set<DFGNode> small, Set<DFGNode> big) {
        if (small.isEmpty()) return true;
        return small.stream().allMatch(nodeInSmall -> big.stream().anyMatch(nodeInBig -> Objects.equals(nodeInSmall, nodeInBig)));
    }

    public static void main(String[] args) throws IOException, DBSCANClusteringException {
        var yaml = new Yaml();
        //test();
        try (var configFile = PatternMiningRunner.class.getResourceAsStream("/application.yaml")) {
            var config = yaml.loadAs(configFile, PatternMiningConfig.class);
            var injector = Guice.createInjector(new MinerModule(config));

            try {
                var codeUtil = injector.getInstance(CodeUtil.class);
                var patternConfig = injector.getInstance(PatternConfig.class);
                var dfgFactory = injector.getInstance(DFGFactory.class);

                var clientCodeRoot = new File(config.getClientCodeDir());
                var graphGenerator = new JavaDFGGenerator(codeUtil, patternConfig, dfgFactory);

                var miner = new PatternMiner<>(
                        clientCodeRoot,
                        graphGenerator,
                        new DFG2Pattern(codeUtil),
                        new DFGNode.Parser(),
                        new DFGEdge.Parser(),
                        PatternMiningRunner::resultFilter,
                        config.getClientCodeDir()+"/mypattern.txt"//此配置项仅为输出不同模式使用
                );

                var result = miner.process(MinerSetting.builder().build());
                System.out.println(result.size());
                System.out.println("get result");
                PrintWriter pw=new PrintWriter(config.getClientCodeDir()+"/pattern.txt");
                result.forEach(r -> {
                     System.out.println("----------");
                     var printer = new Printer(Printer.PrintConfig.builder().holeString(id -> "<HOLE>").build());
//                     log.info("hi");
//                     log.info(printer.print(r.getStmts()));
                    System.out.println(printer.print(r.getStmts()));
                    pw.println(printer.print(r.getStmts()));
                });
                pw.close();
//                injector.getInstance(PatternSaver.class).saveSkeleton(result);
            } catch (UncheckedExecutionException e) {
                System.out.println("unchecked exception");
            } finally {
                var factory = injector.getInstance(KnowledgeGraphSessionFactory.class);
                factory.close();
            }
        }
    }

}
