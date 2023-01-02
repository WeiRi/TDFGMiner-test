package common.skeleton.model.expr;

import common.skeleton.model.Arg;
import common.skeleton.model.type.ReferenceType;
import common.skeleton.model.type.Type;
import common.skeleton.visitor.HomoVisitor;
import common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.With;

import java.util.List;
import java.util.stream.Collectors;

import static common.utils.ElementUtil.qualifiedName2Simple;

@With
@Value
public class ObjectCreationExpr implements Expr<ObjectCreationExpr>, Callable {
    private final ReferenceType type;
    private final List<Arg> args;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> ObjectCreationExpr accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    public String getQualifiedSignature() {
        var argStr = args.stream().map(Arg::getType).map(Type::describe).collect(Collectors.joining(", "));
        return String.format(
            "%s.%s(%s)",
            type.describe(),
            qualifiedName2Simple(type.getQualifiedName()),
            argStr
        );
    }

    @Override
    public int findArgIndex(Arg arg) {
        return args.indexOf(arg);
    }
}
