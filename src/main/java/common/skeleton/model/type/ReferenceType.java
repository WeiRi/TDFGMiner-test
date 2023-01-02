package common.skeleton.model.type;

import lombok.Value;

@Value
public class ReferenceType implements Type {
    private final String qualifiedName;

    @Override
    public String describe() {
        return qualifiedName;
    }
}
