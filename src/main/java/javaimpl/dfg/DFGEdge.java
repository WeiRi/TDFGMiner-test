package javaimpl.dfg;

import de.parsemis.parsers.LabelParser;

public enum DFGEdge {
    INSTANCE;

    public static class Parser implements LabelParser<DFGEdge> {
        @Override
        public DFGEdge parse(String s) {
            return INSTANCE;
        }

        @Override
        public String serialize(DFGEdge dfgNode) {
            return "";
        }
    }
}
