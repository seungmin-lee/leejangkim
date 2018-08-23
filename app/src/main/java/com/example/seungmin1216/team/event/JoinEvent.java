package com.example.seungmin1216.team.event;

public class JoinEvent {
    private int btn_event;

    public JoinEvent(int btn_event) {
        this.btn_event = btn_event;
    }

    public int getBtn_event() {
        return btn_event;
    }

    public void setBtn_event(int btn_event) {
        this.btn_event = btn_event;
    }
}
