package common.skeleton.model.stmt;

import common.skeleton.model.expr.Expr;
import common.skeleton.visitor.HomoVisitor;
import common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.With;
import org.jetbrains.annotations.Nullable;

@With
@Value
public class ReturnStmt implements Stmt<ReturnStmt> {
    @Nullable
    private final Expr<?> value;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> ReturnStmt accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
