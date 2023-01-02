package common.skeleton.model.expr.literal;

import common.skeleton.model.expr.Expr;

public interface LiteralExpr<T> extends Expr<LiteralExpr<T>> {
    T getValue();
}
