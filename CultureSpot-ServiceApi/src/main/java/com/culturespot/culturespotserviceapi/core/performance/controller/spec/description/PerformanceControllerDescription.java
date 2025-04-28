package com.culturespot.culturespotserviceapi.core.performance.controller.spec.description;

public class PerformanceControllerDescription {
    public static final String GET_PERFORMANCE_REQUEST = """
            공연 목록 조회 Parameters 요청값의 설명은 아래와 같습니다.
            - eventType: 이벤트 대분류 (공연/전시, 행사/축제, 교육/체험)
            - genre: 이벤트 중분류 (전시, 연극, 음악, 무용, 미술, 건축, 영상, 문학, 문화정책, 축제, 기타)
            - sortType: 정렬 기준 (최신순, 인기순, 과거순)
            - page: 현재 페이지 번호 (0부터 시작, 무한 스크롤)
            - size: 가져올 아이템 개수 (무한 스크롤 & 캐러셀, 캐러셀은 size: 20으로 고정)
            - lastId: 마지막 아이템 ID (무한 스크롤)
            - keyword: 검색어 (이벤트 제목 및 장소 탐색)
            
            공연 목록 조회 Request Body 요청값의 설명은 아래와 같습니다.
            - totalElements: 전체 데이터 수
            - totalPages: 전체 페이지 수
            - title: 이벤트 제목
            - eventType: 이벤트 대분류 (전시, 공연)
            - genre: 이벤트 중분류 (전시, 연극, 음악, 무용, 미술, 건축, 영상, 문학, 문화정책, 축제, 기타)
            - place: 장소 약칭 (부산현대미술관)
            - startDate, endDate: 이벤트 시작, 종료 일자 (yyyy-MM-dd 형식)
            - imageUrl: 포스터 이미지 주소
            - liked: 사용자의 이벤트 좋아요 여부
            """;

    public static final String GET_PERFORMACE_200_RESPONSE = """
            공연 목록 조회 Response Body 응답값의 설명은 아래와 같습니다.
            - page:int = 페이지
            - size:int = 페이지 사이즈
            - lastId:int = 마지막 아이템 ID (무한 스크롤)
            - totalElements:long =
            - events 응답 값
              - id:int = 이벤트 아이디
              - title:String = 이벤트 타이틀
              - genre:String = 이벤트 장르
              - place:String = 이벤트 주최 장소
              - startDate:String = 이벤트 시작 날짜 (시작일)
              - endDate:String = 이벤트 마감 날짜 (종료일)
              - imageUrl:String(nullable) = 이벤트 관련 이미지
              - liked:boolean = 이벤트 좋아요 수
            """;
}
