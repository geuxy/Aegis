package gg.aegis.check;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter @RequiredArgsConstructor
public enum CheckType {

    COMBAT("Combat"),
    MOVEMENT("Movement"),
    NETWORK("Network");

    private final String name;

}
