package gg.aegis.check;

import lombok.Getter;
import lombok.SneakyThrows;

import gg.aegis.check.checks.ground.CheckGroundPositive;
import gg.aegis.check.checks.move.CheckMoveEngine;
import gg.aegis.check.checks.timer.CheckTimerBalance;
import gg.aegis.player.AegisPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public class CheckManager {

    private final List<Class<?>> checks = new ArrayList<>();

    public CheckManager() {
        this.checks.addAll(Arrays.asList(
                CheckMoveEngine.class,
                CheckGroundPositive.class,
                CheckTimerBalance.class
        ));
    }

    @SneakyThrows
    public List<BaseCheck> loadToPlayer(AegisPlayer data) {
        List<BaseCheck> checkList = new ArrayList<>();

        for(Class<?> checkClass : checks) {
            BaseCheck check = (BaseCheck) checkClass
                    .getConstructor(AegisPlayer.class)
                    .newInstance(data);

            checkList.add(check);
        }

        return checkList;
    }

}
