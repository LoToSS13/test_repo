package net.thumbtack.school.competition.server;

public enum ResponseCode {
    ERROR(400),
    SUCCESS(200);

    ResponseCode(int code) {
        this.code = code;
    }
    final private int code;

    public int getCode() {
        return code;
    }
}
