package gg.aegis.util.other;

public enum TriStateBoolean {

    TRUE, UNCERTAIN, FALSE;

    public boolean isPossible() {
        return this != FALSE;
    }



}
