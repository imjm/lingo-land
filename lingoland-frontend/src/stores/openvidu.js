import sampleImg from "@/assets/sampleImg.jpg";
import { OpenVidu } from "openvidu-browser";
import { defineStore } from "pinia";
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "./user";

export const useOpenviduStore = defineStore("openvidu", () => {
    const OV = new OpenVidu();
    const session = OV.initSession();

    const router = useRouter();
    const userStore = useUserStore();
    const participants = ref([]);
    const mynum = ref("");

    const reparticipants = ref([]);
    const storyList = ref([]);
    const storyOrderList = ref([]);

    // 세션에 스트림이 생성될 때 호출되는 콜백 함수
    session.on("streamCreated", function (event) {
        session.subscribe(event.stream, "subscriber");
    });

    // 세션에 새로운 유저가 참가하면 호출되는 콜백함수
    session.on("connectionCreated", (event) => {
        userStore.getProfileById(event.connection.data).then((info) => {
            // 참가자 배열에 이미 있는 사람인지 확인 필요
            // 배열에서 userId 중복되는지 확인
            const exists = participants.value.some(
                (participant) => participant.userId === event.connection.data
            );

            // 중복되지 않으면 배열에 추가
            if (!exists) {
                // 프로필 이미지가 없으면 기본 이미지를 넣어준다.
                if (!info.profileImage) {
                    info.profileImage = sampleImg;
                }

                participants.value.push({
                    connectionId: event.connection.connectionId,
                    userId: event.connection.data,
                    userProfile: info,
                    role:
                        event.connection.role == "MODERATOR"
                            ? event.connection.role
                            : "PUBLISHER",
                });
            }
        });
    });

    // 게임 시작 Signal 수신 처리
    session.on("signal:gameStart", function (event) {
        const gameType = JSON.parse(event.data);

        // 참가자 커넥션 아이디를 기준으로 정렬
        reparticipants.value = participants.value.sort((a, b) => {
            return a.connectionId.localeCompare(b.connectionId);
        });
        console.log("오픈비두 참가자목록 보기", participants);
        console.log("재정렬한참가자들!!!!!!!!!!!!!!!!!", reparticipants.value);

        // 달리기 게임으로
        if (gameType.type === 1) {
            for (let i = 0; i < reparticipants.value.length; i++) {
                if (
                    reparticipants.value[i].connectionId ==
                    event.target.connection.connectionId
                ) {
                    mynum.value = i;
                    break;
                }
            }
            router.replace({ name: "runningGame" });
        } else if (gameType.type === 2) {
            // 이야기 배열 초기화
            initalizeStoryList();

            // 정렬된 참가자 아이디를 기준으로 이야기 작성 순서 초기화
            initalizeOrderList();

            // 글쓰기 게임으로
            router.replace({ name: "writingGame" });
        }
    });

    // 세션에 유저가 나가면 호출되는 콜백함수
    session.on("connectionDestroyed", (event) => {
        const connectionId = event.connection.connectionId;

        participants.value = participants.value.filter(
            (participant) => participant.connectionId !== connectionId
        );
    });

    // 게임 종료 signal 수신 처리
    session.on("signal:gameEnd", function (event) {
        const resultType = JSON.parse(event.data);
        if (resultType.type === 1) {
            router.replace({ name: "runningGameResult" });
        } else if (resultType.taype === 2) {
            router.replace({ name: "writingGameResult" });
        }
    });

    // 방참가자리스트 초기화
    function resetParticipants() {
        participants.value = [];
    }

    // 방장 확인
    function isLeader() {
        console.log("************isLeader", participants.value);

        const isLeader = participants.value.some(
            (participant) => participant.role === "MODERATOR"
        );

        return isLeader;
    }

    // 이야기 배열 초기화
    function initalizeStoryList() {
        for (let index = 0; index < reparticipants.value.length; index++) {
            storyList.value.push({ storyId: index, story: [] });
        }
    }

    // 이야기 작성 배열 초기화
    function initalizeOrderList() {
        for (let index = 0; index < reparticipants.value.length; index++) {
            // 선형적으로 이야기 순서 만들기
            let orderList = [];
            for (
                let orderIndex = index;
                orderIndex < orderIndex + reparticipants.value.length;
                orderIndex++
            ) {
                orderList.push(orderIndex % reparticipants.value.length);
            }

            storyOrderList.value.push({
                connectionId: reparticipants.value[index].connectionId,
                storyOrderList: orderList,
            });
        }
    }

    return {
        OV,
        session,
        participants,
        reparticipants,
        mynum,
        storyList,
        storyOrderList,
        resetParticipants,
        isLeader,
    };
});
