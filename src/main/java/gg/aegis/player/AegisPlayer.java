package gg.aegis.player;

import com.github.retrooper.packetevents.event.ProtocolPacketEvent;

import gg.aegis.player.data.impl.*;
import lombok.Getter;
import lombok.Setter;

import gg.aegis.Anticheat;
import gg.aegis.player.data.PacketProcessor;
import me.geuxy.player.data.impl.*;
import gg.aegis.player.exempt.Exempts;
import gg.aegis.check.BaseCheck;
import gg.aegis.util.other.Score;

import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.util.*;
import java.util.stream.Stream;

@Getter @Setter
public class AegisPlayer {

    private final Player raw;

    private final Map<Class<? extends PacketProcessor>, PacketProcessor> dataMap = new HashMap<>();
    private final List<BaseCheck> checks = Anticheat.INSTANCE.getCheckManager().loadToPlayer(this);
    private final Deque<Vector> teleports = new ArrayDeque<>();

    private int livingTicks;

    protected boolean onIrregularBlock, lastOnIrregularBlock;
    private boolean inLiquid, lastInLiquid;
    private boolean climbing, lastClimbing;
    private boolean onSlime, lastOnSlime;
    private boolean onSoulSand, lastOnSoulSand;
    private boolean onIce, lastOnIce;
    private boolean inWeb, lastInWeb;
    private boolean onPiston, lastOnPiston;
    private boolean blockAbove, lastBlockAbove;
    private boolean teleporting, lastTeleporting;
    private boolean takingVelocity, lastTakingVelocity;

    private final Score violations = new Score(0, 10);

    public AegisPlayer(Player rawPlayer) {
        this.raw = rawPlayer;
        this.putData(
                new PositionData(),
                new NetworkData(),
                new GhostBlockData(),
                new CombatData(),
                new AimData(),
                new AbilityData()
        );
    }

    private void putData(PacketProcessor... data) {
        for(PacketProcessor d : data) {
            this.dataMap.put(d.getClass(), d);
        }
    }

    public void handle(ProtocolPacketEvent event) {
        this.dataMap.values().forEach(d -> d.process(this, event));
        this.checks.forEach(c -> c.handle(this, event));
    }

    public <T extends PacketProcessor> T data(Class<T> clazz) {
        return clazz.cast(this.dataMap.get(clazz));
    }

    public void onFlying() {
        this.lastOnIrregularBlock = onIrregularBlock;
        this.lastInLiquid = inLiquid;
        this.lastClimbing = climbing;
        this.lastOnSlime = onSlime;
        this.lastOnSoulSand = onSoulSand;
        this.lastOnIce = onIce;
        this.lastInWeb = inWeb;
        this.lastOnPiston = onPiston;
        this.lastBlockAbove = blockAbove;
        this.lastTeleporting = teleporting;
        this.lastTakingVelocity = takingVelocity;
        this.onIrregularBlock = false;
        this.inLiquid = false;
        this.climbing = false;
        this.onSlime = false;
        this.onSoulSand = false;
        this.onIce = false;
        this.inWeb = false;
        this.onPiston = false;
        this.blockAbove = false;
        this.teleporting = false;
        this.takingVelocity = false;
        this.livingTicks = Math.min(500, livingTicks + 1);
    }

    public boolean isExempted(Exempts... exempts) {
        return Stream.of(exempts).anyMatch(e -> e.getFunction().apply(this));
    }

}
