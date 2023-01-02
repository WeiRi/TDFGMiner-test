package common.skeleton.model.stmt;

import common.skeleton.model.expr.Expr;
import common.skeleton.visitor.HomoVisitor;
import common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.With;
import org.jetbrains.annotations.Nullable;

@With
@Value
public class IfStmt implements Stmt<IfStmt> {
    private final Expr<?> cond;
    private final BlockStmt thenBody;
    @Nullable
    private final BlockStmt elseBody;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> IfStmt accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
