package common.skeleton.model.expr;

import common.skeleton.visitor.HomoVisitor;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use= Id.CLASS, property="class")
public interface NameOrHole<N extends NameOrHole<?>> extends Expr<N> {
    <A> N accept(HomoVisitor<A> visitor, A arg);
}
