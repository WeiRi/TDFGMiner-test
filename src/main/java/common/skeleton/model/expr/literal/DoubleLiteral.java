package common.skeleton.model.expr.literal;

import common.skeleton.visitor.HomoVisitor;
import common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.With;

@With
@Value
public class DoubleLiteral implements LiteralExpr<Double> {
    private Double value;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> LiteralExpr<Double> accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
