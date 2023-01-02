package javaimpl.ir.statements;

import javaimpl.cfg.CFG;
import javaimpl.ir.IRExpression;
import javaimpl.ir.expressions.IREnum;
import com.github.javaparser.ast.Node;

import java.util.List;
import java.util.Set;

public class IREnumAccess extends IRDefStatement {
    private final IREnum constant;

    public IREnumAccess(CFG cfg, String ty, Set<Node> from, IREnum constant) {
        super(cfg, ty, from);
        this.constant = constant;
        init();
    }

    @Override
    public List<IRExpression> uses() {
        return List.of(constant);
    }
}
