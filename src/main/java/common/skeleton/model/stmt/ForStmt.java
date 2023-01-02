package common.skeleton.model.stmt;

import common.skeleton.model.expr.Expr;
import common.skeleton.visitor.HomoVisitor;
import common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.With;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@With
@Value
public class ForStmt implements Stmt<ForStmt> {
    private final List<Expr<?>> inits;
    @Nullable
    private final Expr<?> cond;
    private final List<Expr<?>> updates;
    private final BlockStmt body;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> ForStmt accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
