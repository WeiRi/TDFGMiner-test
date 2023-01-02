package javaimpl.ir.statements;

import javaimpl.cfg.CFG;
import javaimpl.ir.IRExpression;
import com.github.javaparser.ast.Node;

import java.util.List;
import java.util.Set;

public class IRStaticFieldAccess extends IRDefStatement {
    private final String scopeTy;
    private final String field;

    public IRStaticFieldAccess(CFG cfg, String ty, Set<Node> from, String scopeTy, String field) {
        super(cfg, ty, from);
        this.scopeTy = scopeTy;
        this.field = field;
        init();
    }

    @Override
    public List<IRExpression> uses() {
        return List.of();
    }
}
