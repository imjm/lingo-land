import sampleImg from "@/assets/sampleImg.jpg";
import { OpenVidu } from "openvidu-browser";
import { defineStore } from "pinia";
import { ref } from "vue";
import { useRouter } from "vue-router";
import { useUserStore } from "./user";

export const useOpenviduStore = defineStore("openvidu", () => {
    const OV = new OpenVidu();
    const session = OV.initSession();
    const userStore = useUserStore();
    const participants = ref([]);
    const router = useRouter();

    // 세션에 스트림이 생성될 때 호출되는 콜백 함수
    session.on("streamCreated", function (event) {
        session.subscribe(event.stream, "subscriber");
    });

    // 세션에 새로운 유저가 참가하면 호출되는 콜백함수
    session.on("connectionCreated", (event) => {
        console.log("***************event", event);

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

            console.log("**********참가자", participants.value);
        });
    });

    // 세션에 유저가 나가면 호출되는 콜백함수
    session.on("connectionDestroyed", (event) => {
        const connectionId = event.connection.connectionId;

        console.log("***********connectionDestroyed", event);
        console.log("**********participants", participants.value);

        participants.value = participants.value.filter(
            (participant) => participant.connectionId !== connectionId
        );
    });

    // 게임 시작 signal 수신 처리
    session.on("signal:gameStart", function (event) {
        const gameType = JSON.parse(event.data);

        if (gameType.type === 1) {
            // 달리기 게임으로
            router.replace({ name: "runningGame" });
        } else if (gameType.type === 2) {
            // 글쓰기 게임으로
            router.replace({ name: "writingGame" });
        }
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
    return { OV, session, participants };
});
