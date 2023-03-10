package javaimpl.ir.expressions;

import javaimpl.ir.IRExpression;
import com.github.javaparser.ast.Node;
import lombok.Getter;

import java.util.Set;

@Getter
public class IRNull extends IRExpression {
    private final String ty;

    public IRNull(String ty, Node from) {
        super(Set.of(from));
        this.ty = ty;
    }

    @Override
    public String toString() {
        return "null";
    }
}

