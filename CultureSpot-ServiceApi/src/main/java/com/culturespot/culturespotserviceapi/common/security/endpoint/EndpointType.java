package com.culturespot.culturespotserviceapi.common.security.endpoint;

public enum EndpointType {
    USER("/api/users/**"),
    ADMIN("/api/admins/**"),

    LOGOUT("/api/logout"),
    REFRESH("/api/re-refresh"),
    EVENT("/api/events/**"),
    MY("api/my/**");
    ;

    private final String path;

    // ✅ 정적 상수로 제공하여 컴파일 타임 상수로 사용 가능
    public static final String PUBLIC_PATH = "/api/public"; // 삭제예정
    public static final String USER_PATH = "/api/users";
    public static final String ADMIN_PATH = "/api/admins";
    public static final String API_PATH = "/api";
    public static final String EVENTS_PATH = "/api/events";
    public static final String MY_PATH = "/api/my";

    EndpointType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}