package common.skeleton.model.expr.literal;

import common.skeleton.visitor.HomoVisitor;
import common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.With;

@With
@Value
public class IntLiteral implements LiteralExpr<Integer> {
    private Integer value;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> LiteralExpr<Integer> accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
