package common.skeleton.model.stmt;

import common.skeleton.model.expr.Expr;
import common.skeleton.visitor.HomoVisitor;
import common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.With;

@With
@Value
public class WhileStmt implements Stmt<WhileStmt> {
    private final Expr<?> cond;
    private final BlockStmt body;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> WhileStmt accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
