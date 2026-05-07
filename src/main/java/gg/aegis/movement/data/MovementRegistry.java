package gg.aegis.movement.data;

import lombok.Getter;
import lombok.Setter;
import gg.aegis.util.other.TriStateBoolean;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
public class MovementRegistry {

    private MovementBounds bounds = new MovementBounds();
    private final List<String> tags = new ArrayList<>();
    private TriStateBoolean jumped;

    public void addTag(String tag) {
        this.tags.add(tag);
    }

}
