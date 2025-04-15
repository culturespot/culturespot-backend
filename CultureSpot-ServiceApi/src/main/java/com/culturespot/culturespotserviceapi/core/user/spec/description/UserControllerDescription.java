package com.culturespot.culturespotserviceapi.core.user.spec.description;

public class UserControllerDescription {
    public static final String GET_USER_PROFILE_200_RESPONSE = """
            유저 프로필 반환값은 아래와 같습니다.
            - userId:Long = 사용자 아이디
            - username:String = 사용자 닉네임
            - platform:Enum = 사용자가 회원가입한 소셜 플랫폼
            - email:String = 사용자 이메일
            - profileCode:int = 사용자 프로필 코드(해당 값에의해 사용자 프로필 이미지 결정).
            - preferredGenres:List<String> = 사용자가 선호하는 장르(들)입니다.
            """;

    public static final String USER_PROFILE_801_RESPONSE = """
            서비스에 가입되지 않은 사용자의 요청의 경우에 대한 응답입니다.
            """;

    public static final String UPDATE_USER_PROFILE_REQUEST = """
             유저 프로필 수정에 대한 요청값은 아래와 같습니다.
             - username:String = 사용자 닉네임
             - profileCode:int = 프로필 코드
             - preferredGenres:List<String> = 사용자가 선호하는 장르
             
             요청값은 일부만 보내주셔도 동작합니다.
            """;
}
