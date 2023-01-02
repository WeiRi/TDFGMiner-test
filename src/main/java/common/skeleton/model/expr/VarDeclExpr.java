package common.skeleton.model.expr;

import common.skeleton.model.type.Type;
import common.skeleton.visitor.HomoVisitor;
import common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.With;
import org.jetbrains.annotations.Nullable;

@With
@Value
public class VarDeclExpr implements Expr<VarDeclExpr> {
    private final Type type;
    private final NameExpr<?> name;
    @Nullable
    private final Expr<?> init;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> VarDeclExpr accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
