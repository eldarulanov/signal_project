package com.alerts.decorator;

import com.alerts.Alert;

public class RepeatedAlertDecorator extends AlertDecorator {
    private int repeatCount;
    private long interval;

    public RepeatedAlertDecorator(Alert decoratedAlert, int repeatCount, long interval) {
        super(decoratedAlert);
        this.repeatCount = repeatCount;
        this.interval = interval;
    }

    @Override
    public void sendAlert() {
        for (int i = 0; i < repeatCount; i++) {
            super.sendAlert();
            try {
                Thread.sleep(interval);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}