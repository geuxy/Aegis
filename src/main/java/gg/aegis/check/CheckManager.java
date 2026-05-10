package gg.aegis.check;

import lombok.SneakyThrows;

import gg.aegis.check.checks.ground.CheckGroundPositive;
import gg.aegis.check.checks.move.CheckMoveEngine;
import gg.aegis.check.checks.timer.CheckTimerBalance;
import gg.aegis.player.AegisPlayer;

import java.util.ArrayList;
import java.util.List;

public class CheckManager {

    private static final Class<? extends BaseCheck>[] CHECKS = new Class[] {
            CheckMoveEngine.class,
            CheckGroundPositive.class,
            CheckTimerBalance.class
    };

    @SneakyThrows
    public List<BaseCheck> loadToPlayer(AegisPlayer data) {
        List<BaseCheck> checkList = new ArrayList<>();

        for(Class<? extends BaseCheck> checkClass : CHECKS) {
            BaseCheck check = checkClass
                    .newInstance();

            checkList.add(check);
        }

        return checkList;
    }

}
