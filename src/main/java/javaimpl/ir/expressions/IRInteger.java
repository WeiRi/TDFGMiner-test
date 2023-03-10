package javaimpl.ir.expressions;

import javaimpl.ir.IRExpression;
import com.github.javaparser.ast.Node;

import java.util.Set;

public class IRInteger extends IRExpression {
    private final int value;

    public IRInteger(int value, Node from) {
        super(Set.of(from));
        this.value = value;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
