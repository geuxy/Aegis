package gg.aegis.check;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Accessors;

@Getter @Accessors(fluent = true) @RequiredArgsConstructor
public final class CheckMetadata {

    private final String name, unique;
    private final CheckType type;

    public static CheckMetadata of(Class<? extends BaseCheck> clazz) {
        CheckData data = clazz.getAnnotation(CheckData.class);

        if(data == null) {
            throw new IllegalArgumentException("Missing check metadata");
        }

        return new CheckMetadata(data.name(), data.unique(), data.type());
    }

}
