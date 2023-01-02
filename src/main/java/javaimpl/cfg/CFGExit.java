package javaimpl.cfg;

import javaimpl.ir.IRStatement;

import java.util.List;

public class CFGExit extends CFGBlock{
    public CFGExit(CFG cfg) {
        super(cfg);
    }

    @Override
    public void setNext(CFGBlock next) {
        throw new UnsupportedOperationException("Cannot set next block of exit");
    }

    @Override
    public List<? extends IRStatement> statements() {
        return phis;
    }
}
