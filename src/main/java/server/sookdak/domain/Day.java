package server.sookdak.domain;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum Day {
    MON("월"), TUE("화"), WED("수"), THU("목"), FRI("금"), SAT("토");

    @JsonValue
    private final String day;

    Day(String day) {
        this.day = day;
    }

    public static Day nameOf(String name) {
        for (Day day : Day.values()) {
            if (day.getDay().equals(name)) {
                return day;
            }
        }
        return null;
    }
}
