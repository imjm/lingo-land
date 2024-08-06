package com.ssafy.a603.lingoland.member.entity;

public enum Rank {

    CHAMBONG("종9품: 참봉", 1),
    HUNDO("정9품: 훈도", 2),
    JEONGJA("정9품: 정자", 3),
    BUBONGSA("정9품: 부봉사", 4),
    BONGSA("종8품: 봉사", 5),
    JEOJAK("정8품: 저작", 6),
    JIKJANG("종7품: 직장", 7),
    BAKSA("정7품: 박사", 8),
    GYOSU("종6품: 교수", 9),
    JUBU("종6품: 주부", 10),
    BYEOLJE("정6품: 별제", 11),
    JWARANG("정6품: 좌랑", 12),
    PANGWAN("종5품: 판관", 13),
    DOSA("종5품: 도사", 14),
    GYORI("정5품: 교리", 15),
    BYEOLJWA("정5품: 별좌", 16),
    JEONGLANG("정5품: 정랑", 17),
    CHUMJEONG("종4품: 첨정", 18),
    KYUNGRYEOK("종4품: 경력", 19),
    JANGRYEONG("정4품: 장령", 20),
    SAIN("정4품: 사인", 21),
    SAGAN("종3품: 사간", 22),
    JIBUI("종3품: 집의", 23),
    JIKJAEHAK("정3품: 직제학", 24),
    CHAMUI("정3품: 참의", 25),
    SANGSEON("종2품: 상선", 26),
    CHAMPAN("종2품: 참판", 27),
    DONGJISA("종2품: 동지사", 28),
    DAEJAEHAK("정2품: 대제학", 29),
    WOOCHAMCHAN("정2품: 우참찬", 30),
    JOICHAMCHAN("정2품: 좌참찬", 31),
    PANSEO("정2품: 판서", 32),
    JISA("정2품: 지사", 33),
    JAEJO("종1품: 제조", 34),
    PANSA("종1품: 판사", 35),
    WOCHANSEONG("종1품: 우찬성", 36),
    JWACHANSEONG("종1품: 좌찬성", 37),
    DOJAEJO("정1품: 도제조", 38),
    WOOUIJEONG("정1품: 우의정", 39),
    JWAUIJEONG("정1품: 좌의정", 40),
    YEONGUIJEONG("정1품: 영의정", 41);

    private final String grade;
    private final int order;

    Rank(String grade, int order) {
        this.grade = grade;
        this.order = order;
    }

    public String getGrade() {
        return grade;
    }

    public int getOrder() {
        return order;
    }

    public Rank nextRank() {
        int nextOrder = this.order + 1;
        for (Rank rank : Rank.values()) {
            if (rank.getOrder() == nextOrder) {
                return rank;
            }
        }
        return this;
    }
}
