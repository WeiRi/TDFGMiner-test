package api;

import java.util.List;

public interface PatternConfig {
    List<String> getSourceCodeDirs();
    boolean isDebug();
    List<String> getDfgNodeFilters();
    //todo:设置图的大小限制，超出此大小则不挖掘
    Integer getNodeSizeLimit();
//    default Integer getNodeSizeLimit() {
//        return null;
//    }
}
