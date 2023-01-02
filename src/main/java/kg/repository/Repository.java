package kg.repository;

import kg.entity.EnumEntity;
import kg.entity.MethodEntity;
import kg.entity.PatternEntity;
import kg.entity.TypeEntity;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface Repository {
    @Nullable
    TypeEntity getTypeEntity(String qualifiedName);

    @Nullable
    MethodEntity getMethodEntity(String qualifiedSignature);

    @Nullable
    EnumEntity getEnumEntity(String qualifiedName);

    Collection<PatternEntity> getAllPatterns();
}
