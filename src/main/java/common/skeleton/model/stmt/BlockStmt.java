package common.skeleton.model.stmt;

import common.skeleton.visitor.HomoVisitor;
import common.skeleton.visitor.Visitor;
import lombok.Value;
import lombok.With;

import java.util.List;

@With
@Value
public class BlockStmt implements Stmt<BlockStmt> {
    private final List<Stmt<?>> statements;

    @Override
    public <A, R> R accept(Visitor<A, R> visitor, A arg) {
        return visitor.visit(this, arg);
    }

    @Override
    public <A> BlockStmt accept(HomoVisitor<A> visitor, A arg) {
        return visitor.visit(this, arg);
    }
}
