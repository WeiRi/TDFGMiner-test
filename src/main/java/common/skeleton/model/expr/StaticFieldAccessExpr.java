package common.skeleton.model.expr;

import common.skeleton.model.type.ReferenceType;
import common.skeleton.model.type.Type;
import common.skeleton.visitor.HomoVisitor;
import common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.With;

@With
@Value
public class StaticFieldAccessExpr implements Expr<StaticFieldAccessExpr> {
    private final ReferenceType receiverType;
    private final Type targetType;
    private final NameOrHole<?> name;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> StaticFieldAccessExpr accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
