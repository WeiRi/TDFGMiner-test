package javaimpl.ir.statements;

import javaimpl.cfg.CFG;
import javaimpl.ir.IRExpression;
import utils.IRUtil.UsesBuilder;
import com.github.javaparser.ast.Node;

import java.util.List;
import java.util.Set;

public class IRArrayInitializer extends IRDefStatement {
    private final List<IRExpression> inits;

    public IRArrayInitializer(CFG cfg, String ty, Set<Node> from, List<IRExpression> inits) {
        super(cfg, ty, from);
        this.inits = inits;
        init();
    }

    @Override
    public List<IRExpression> uses() {
        return new UsesBuilder()
            .addAll(inits)
            .build();
    }
}

