package javaimpl.ir.statements;

import javaimpl.ir.IRExpression;
import javaimpl.ir.IRStatement;
import utils.IRUtil.UsesBuilder;
import com.github.javaparser.ast.Node;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

public class IRReturn extends IRStatement {
    @Nullable
    private final IRExpression value;

    public IRReturn(Set<Node> from, @Nullable IRExpression value) {
        super(from);
        this.value = value;
        init();
    }

    @Override
    public List<IRExpression> uses() {
        return new UsesBuilder()
            .add(value)
            .build();
    }
}
