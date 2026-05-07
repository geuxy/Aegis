package gg.aegis.violation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import gg.aegis.check.BaseCheck;
import gg.aegis.util.text.TextBuilder;

@Getter @RequiredArgsConstructor
public class Violation {

    private final BaseCheck failedCheck;
    private final TextBuilder debug;
    private final long timestamp;

}
