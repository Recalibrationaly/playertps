package me.buryings.playertps;

import me.buryings.playertps.TPSCount;

public class TPSCount implements Runnable {
    public static int TICK_COUNT = 0;
    public static long[] TICKS = new long[600];
    public static long LAST_TICK = 0L;

    public static double getTPS() {
        return TPSCount.getTPS(100);
    }

    public static double getTPS(int ticks) {
        if (TICK_COUNT < ticks) {
            return 20.0;
        }
        int target = (TICK_COUNT - 1 - ticks) % TICKS.length;
        long elapsed = System.currentTimeMillis() - TICKS[target];
        return (double)ticks / ((double)elapsed / 1000.0);
    }

    public static long getElapsed(int tickID) {
        TICKS.length;
        long time = TICKS[tickID % TICKS.length];
        return System.currentTimeMillis() - time;
    }

    @Override
    public void run() {
        TPSCount.TICKS[TPSCount.TICK_COUNT % TPSCount.TICKS.length] = System.currentTimeMillis();
        ++TICK_COUNT;
    }
}
