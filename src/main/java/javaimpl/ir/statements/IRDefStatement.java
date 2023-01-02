package javaimpl.ir.statements;

import javaimpl.cfg.CFG;
import javaimpl.ir.IRStatement;
import javaimpl.ir.expressions.IRTemp;
import com.github.javaparser.ast.Node;
import lombok.Getter;

import java.util.Set;

public abstract class IRDefStatement extends IRStatement {
    @Getter
    private final IRTemp target;
    @Getter
    private final String ty;

    protected IRDefStatement(CFG cfg, String ty, Set<Node> from) {
        super(from);
        this.target = cfg.createTempVar(this);
        this.ty = ty;
    }
}
