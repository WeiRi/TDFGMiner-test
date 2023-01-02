package javaimpl;

import common.skeleton.HoleFactory;
import common.skeleton.Skeleton;
import common.skeleton.model.expr.NameExpr;
import common.skeleton.model.stmt.BlockStmt;
import common.utils.CodeBuilder;
import common.collection.Pair;
import api.PatternGenerator;
import javaimpl.dfg.DFG;
import javaimpl.dfg.DFGEdge;
import javaimpl.dfg.DFGNode;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import de.parsemis.graph.Graph;
import kg.CodeUtil;
import lombok.Getter;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
//get skeleton
public class DFG2Pattern implements PatternGenerator<DFGNode, DFGEdge, DFG, Skeleton> {
    @Getter
    private final CodeUtil codeUtil;

    private final HoleFactory holeFactory = new HoleFactory();

    public DFG2Pattern(CodeUtil codeUtil) {
        this.codeUtil = codeUtil;
    }

    //将dfg图转换为skeleton
    @Override
    public Skeleton generate(Collection<DFG> originalGraph, Graph<DFGNode, DFGEdge> graph) {
        System.out.println("find skeleton!");
        var pair = originalGraph.stream()
            .map(d -> Pair.of(d, d.isSuperGraph(graph)))
            .filter(p -> p.getRight().getLeft())
                //.findAny()
                //todo 有的数据在这里没有线程执行完成
            .findFirst()
            .get();
        return generateCode(pair.getLeft(), pair.getRight().getRight());
    }

    private Skeleton generateCode(DFG dfg, Set<de.parsemis.graph.Node<DFGNode, DFGEdge>> nodes) {
        var recoverNodes = dfg.recover(nodes);
        Map<String, NameExpr<?>> ctx = new HashMap<>();
        var block = generateCode(dfg.getCfg().getDecl(), recoverNodes, ctx);
        return Skeleton.create(holeFactory, block);
    }


    //处理方法声明和构造函数语句块
    private BlockStmt generateCode(Node node, Set<Node> nodes, Map<String, NameExpr<?>> names) {
        if (node instanceof MethodDeclaration || node instanceof ConstructorDeclaration) {
            com.github.javaparser.ast.stmt.BlockStmt body;
            if (node instanceof MethodDeclaration) {
                if (((MethodDeclaration) node).getBody().isEmpty()) {
                    return CodeBuilder.block();
                }
                body = ((MethodDeclaration) node).getBody().get();
            } else {
                body = ((ConstructorDeclaration) node).getBody();
            }

            var stmts = body.accept(new DFG2PatternStatementVisitor(codeUtil, nodes, holeFactory), names);
            return CodeBuilder.block(stmts.getLeft());
        }
        return CodeBuilder.block();
    }
}
