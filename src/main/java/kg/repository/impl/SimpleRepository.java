package kg.repository.impl;

import kg.entity.EnumEntity;
import kg.entity.MethodEntity;
import kg.entity.PatternEntity;
import kg.entity.TypeEntity;
import kg.repository.Repository;
import com.google.inject.Inject;
import org.neo4j.ogm.session.Session;

import java.util.Collection;

public class SimpleRepository implements Repository {
    private final Session session;

    @Inject
    public SimpleRepository(Session session) {
        this.session = session;
    }

    @Override
    public TypeEntity getTypeEntity(String qualifiedName) {
        var enumEntity = session.load(EnumEntity.class, qualifiedName);
        if (enumEntity != null) return enumEntity;
        return session.load(TypeEntity.class, qualifiedName);
    }

    @Override
    public MethodEntity getMethodEntity(String qualifiedSignature) {
        return session.load(MethodEntity.class, qualifiedSignature);
    }

    @Override
    public EnumEntity getEnumEntity(String qualifiedName) {
        return session.load(EnumEntity.class, qualifiedName);
    }

    @Override
    public Collection<PatternEntity> getAllPatterns() {
        return session.loadAll(PatternEntity.class);
    }
}
