package common.skeleton.model.stmt;

import common.skeleton.model.Node;
import common.skeleton.visitor.HomoVisitor;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

@JsonTypeInfo(use= Id.CLASS, property="class")
public interface Stmt<S extends Stmt<?>> extends Node<S> {
    <A> S accept(HomoVisitor<A> visitor, A arg);
}
