package common.skeleton.model;

import common.skeleton.model.expr.Expr;
import common.skeleton.model.type.Type;
import common.skeleton.visitor.HomoVisitor;
import common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.With;

@Value
@With
public class Arg implements Node<Arg> {
    private final Type type;
    private final Expr<?> value;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> Arg accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
