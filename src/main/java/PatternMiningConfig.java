import api.PatternConfig;
import lombok.Data;

import java.util.List;
//将yaml文件转换为config设置
@Data
public class PatternMiningConfig implements PatternConfig {
    private String uri;
    private String username;
    private String password;

    private String clientCodeDir;

    //限制图的大小上限
    private Integer nodeSizeLimit;
    private List<String> dfgNodeFilters = List.of();
    private List<String> sourceCodeDirs = List.of();

    private boolean debug = false;

}
