package javaimpl.ir.statements;

import javaimpl.cfg.CFG;
import javaimpl.ir.IRExpression;
import utils.IRUtil.UsesBuilder;
import com.github.javaparser.ast.Node;
import lombok.Getter;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Set;

@Getter
public class IRMethodInvocation extends IRDefStatement {
    private final String qualifiedSignature;
    @Nullable
    private final IRExpression receiver;
    private final List<IRExpression> args;

    public IRMethodInvocation(CFG cfg, String ty, Set<Node> from, String qualifiedSignature, @Nullable IRExpression receiver, List<IRExpression> args) {
        super(cfg, ty, from);
        this.qualifiedSignature = qualifiedSignature;
        this.receiver = receiver;
        this.args = args;
        init();
    }

    @Override
    public List<IRExpression> uses() {
        return new UsesBuilder()
            .add(receiver)
            .addAll(args)
            .build();
    }
}
