package gg.aegis.util.other;

import lombok.experimental.UtilityClass;

@UtilityClass
public class StringUtil {

    public String format(String template, Object... values) {
        if (template == null || template.isEmpty() || values == null || values.length == 0) {
            return template;
        }

        int len = template.length();
        StringBuilder builder = new StringBuilder(len + values.length * 8);

        int valueIndex = 0;

        for (int i = 0; i < len; i++) {
            char character = template.charAt(i);

            if (character == '{' && i + 1 < len && template.charAt(i + 1) == '}') {
                if (valueIndex < values.length) {
                    builder.append(values[valueIndex++]);

                } else {
                    builder.append("{}");
                }
                i++;

            } else {
                builder.append(character);
            }
        }

        return builder.toString();
    }

}
