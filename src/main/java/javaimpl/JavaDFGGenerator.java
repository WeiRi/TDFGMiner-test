package javaimpl;

import api.GraphGenerator;
import api.PatternConfig;
import api.filter.DFGNodeFilter;
import api.filter.DFGSizeFilter;
import javaimpl.dfg.DFG;
import javaimpl.dfg.DFGFactory;
import javaimpl.dfg.DFGNode;
import javaimpl.dfg.DFGNode.Type;
import kg.CodeUtil;

import java.io.File;
import java.util.Collection;

public class JavaDFGGenerator implements GraphGenerator<File, DFG> {
    private final CodeUtil codeUtil;
    private final PatternConfig config;
    private final DFGFactory dfgFactory;

    public JavaDFGGenerator(CodeUtil codeUtil, PatternConfig config, DFGFactory dfgFactory) {
        this.codeUtil = codeUtil;
        this.config = config;
        this.dfgFactory = dfgFactory;
    }

    //使用源代码产生paser，再使用语料库转换为DFG
    @Override
    public Collection<DFG> generate(File source) {
        var projectParser = new JavaProjectParser(config.getSourceCodeDirs(), codeUtil, config, dfgFactory);
        //添加节点数量的filter，避免使用过大的图进行挖掘
        if (config.getNodeSizeLimit() != null) projectParser.register(new DFGSizeFilter(config.getNodeSizeLimit()));
        config.getDfgNodeFilters().forEach(f -> projectParser.register(new DFGNodeFilter(new DFGNode(Type.METHOD_INVOCATION, f))));
        return projectParser.process(source.toPath());
    }
}
