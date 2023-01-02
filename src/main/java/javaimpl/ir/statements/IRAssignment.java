package javaimpl.ir.statements;

import javaimpl.cfg.CFG;
import javaimpl.ir.IRExpression;
import com.github.javaparser.ast.Node;

import java.util.List;
import java.util.Set;

public class IRAssignment extends IRDefStatement {
    private final IRExpression value;

    public IRAssignment(CFG cfg, String ty, Set<Node> from, IRExpression value) {
        super(cfg, ty, from);
        this.value = value;
        init();
    }

    @Override
    public List<IRExpression> uses() {
        return List.of(value);
    }
}
