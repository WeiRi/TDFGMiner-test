package common.skeleton.model.expr;

import common.skeleton.model.type.ReferenceType;
import common.skeleton.visitor.HomoVisitor;
import common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.With;

@With
@Value
public class EnumConstantExpr implements Expr<EnumConstantExpr> {
    private final ReferenceType enumType;
    private final NameOrHole<?> name;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> EnumConstantExpr accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
