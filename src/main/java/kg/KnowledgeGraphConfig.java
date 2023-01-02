package kg;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class KnowledgeGraphConfig {
    private String uri;
    private String username;
    private String password;
}
