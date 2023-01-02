package common.skeleton.model.expr;

import common.skeleton.model.type.Type;
import common.skeleton.visitor.HomoVisitor;
import common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.With;

import java.util.List;

@With
@Value
public class ArrayCreationExpr implements Expr<ArrayCreationExpr> {
    private final Type componentType;
    private final List<Expr<?>> inits;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> ArrayCreationExpr accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
