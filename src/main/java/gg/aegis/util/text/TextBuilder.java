package gg.aegis.util.text;

import java.util.ArrayList;
import java.util.List;

public final class TextBuilder {

    private static final int MAX_LINES = 8;
    private static final String KEY_VALUE_SEPARATOR = ": ";

    private final List<TextPart> lines = new ArrayList<>(MAX_LINES);

    public static TextBuilder build() {
        return new TextBuilder();
    }

    public static TextBuilder of(String line) {
        return new TextBuilder(line);
    }

    public static TextBuilder of(String key, Object value) {
        return new TextBuilder(key, value);
    }

    public TextBuilder() {
    }

    public TextBuilder(String line) {
        this.add(line);
    }

    public TextBuilder(String key, Object value) {
        this.add(key, value);
    }

    public TextBuilder add(String line) {
        this.lines.add(b -> b.append(line));
        return this;
    }

    public TextBuilder add(String key, Object value) {
        this.lines.add(b ->
                b.append(key)
                .append(KEY_VALUE_SEPARATOR)
                .append(value)
        );
        return this;
    }

    public TextBuilder add(TextBuilder builder) {
        this.lines.add(b -> b.append(builder.flatten()));
        return this;
    }

    public String build(String separator) {
        if(this.lines.isEmpty()) {
            return "";
        }

        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < this.lines.size(); i++) {
            if(i > 0) {
                builder.append(separator);
            }

            this.lines.get(i).appendTo(builder);
        }

        return builder.toString();
    }

    public String flatten() {
        return build(", ");
    }

}
