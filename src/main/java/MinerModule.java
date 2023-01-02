import api.PatternConfig;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import kg.KnowledgeGraphConfig;
import kg.KnowledgeGraphSessionFactory;
import kg.repository.Repository;
import kg.repository.impl.PreloadRepository;
import org.neo4j.ogm.session.Session;

public class MinerModule extends AbstractModule {
    private final PatternMiningConfig config;

    public MinerModule(PatternMiningConfig config) {
        this.config = config;
    }

    @Override
    public void configure() {
        bind(PatternConfig.class).toInstance(config);
        bind(Repository.class).to(PreloadRepository.class);
        bind(Session.class).toProvider(KnowledgeGraphSessionFactory.class).in(Singleton.class);
    }

    @Provides
    @Singleton
    public KnowledgeGraphConfig knowledgeGraphConfig() {
        return KnowledgeGraphConfig.builder()
                .uri(config.getUri())
                .username(config.getUsername())
                .password(config.getPassword())
                .build();
    }
}
