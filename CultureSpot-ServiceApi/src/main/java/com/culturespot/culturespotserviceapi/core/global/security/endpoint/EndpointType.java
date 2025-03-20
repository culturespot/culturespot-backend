package com.culturespot.culturespotserviceapi.core.global.security.endpoint;

public enum EndpointType {
    PUBLIC("/api/public/**"),
    USER("/api/users/**"),
    ADMIN("/api/admins/**");

    private final String path;

    // ✅ 정적 상수로 제공하여 컴파일 타임 상수로 사용 가능
    public static final String PUBLIC_PATH = "/api/public";
    public static final String USER_PATH = "/api/users";
    public static final String ADMIN_PATH = "/api/admins";
    public static final String GOOGLE_LOGIN_PATH = "/login/oauth2/code/google"; // Spring Security에서 제공하는 리다이렉션 URI

    EndpointType(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}