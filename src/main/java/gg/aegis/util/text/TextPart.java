package gg.aegis.util.text;

@FunctionalInterface
public interface TextPart {

    void appendTo(StringBuilder builder);

}
