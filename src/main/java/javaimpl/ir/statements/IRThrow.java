package javaimpl.ir.statements;

import javaimpl.ir.IRExpression;
import javaimpl.ir.IRStatement;
import com.github.javaparser.ast.Node;

import java.util.List;
import java.util.Set;

public class IRThrow extends IRStatement {
    private final IRExpression exception;

    public IRThrow(Set<Node> from, IRExpression exception) {
        super(from);
        this.exception = exception;
        init();
    }

    @Override
    public List<IRExpression> uses() {
        return List.of(exception);
    }
}
