package javaimpl.ir.expressions;

import javaimpl.ir.IRExpression;
import com.github.javaparser.ast.Node;

import java.util.Set;

public class IRLong extends IRExpression {
    private final long value;

    public IRLong(long value, Node from) {
        super(Set.of(from));
        this.value = value;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
