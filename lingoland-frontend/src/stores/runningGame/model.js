import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";
import * as THREE from "three";
import { countdown } from "./time";
import { checkAnswer } from "./question";
import { useGameStore } from "./gameStore";
import { useOpenviduStore } from "@/stores/openvidu";
import { ref } from "vue";
// import { useShuffleStore } from "./shuffleStore";
const openviduStore = useOpenviduStore();
const { OV, session, mynum } = openviduStore;
// const shuffleStore = useShuffleStore();
// const { models } = shuffleStore;
const models = [
    { name: "chick", path: "/cute_chick/scene.gltf", scale: 1, rotation: -Math.PI / 2 },
    { name: "squid", path: "/squid/scene.gltf", scale: 10, rotation: 0 },
    { name: "dragonfly", path: "/dragonfly/scene.gltf", scale: 3, rotation: -Math.PI},
    { name: "unicorn", path: "/just_a_unicorn/scene.gltf", scale: 0.5, rotation: 0 },
    { name: "dog", path: "/stylized_dog_low_poly/scene.gltf", scale: 3, rotation: 0 },
    { name: "cat", path: "/little_cat/scene.gltf", scale: 7, rotation: 0 },
];
let moveSide = ref(0);
let chickModel;
let mixer;

const showRunningGameResult = () => {
    // 시그널 송신
    session.signal({
        type: "gameEnd",
        data: JSON.stringify({ type: 1, data: "running game result" })
    })
    .then(() => {
        console.log("***************************달리기 게임 결과화면 ㄱㄱ");
    })
    .catch((error) => {
        console.log("************ERROR 결과로 못가", error);
    });
}
console.log('mynum@@@@@@@@@@@@@@@@@@model',mynum)
console.log('model@@@@@@@@models',models)
function loadChickModel(scene, renderer, callback) {
    const loader = new GLTFLoader();
    loader.load(models[mynum].path, function (gltf) {
        gltf.scene.traverse((child) => {
            if (child.isMesh) {
                child.material.needsUpdate = true;
                if (child.material.map) {
                    child.material.map.anisotropy = renderer.capabilities.getMaxAnisotropy();
                    child.material.map.needsUpdate = true;
                }
            }
        });

        chickModel = gltf.scene; // 병아리 모델을 저장
        chickModel.scale.set(models[mynum].scale, models[mynum].scale, models[mynum].scale); // 크기 1배
        scene.add(chickModel);
        mixer = new THREE.AnimationMixer(chickModel);
        const action = mixer.clipAction(gltf.animations[0]);
        action.play();

        chickModel.rotation.y = models[mynum].rotation; // 정면을 보도록 설정 (0도로 설정)

        const chickBoundingBox = new THREE.Box3().setFromObject(chickModel);
        const chickHeight = chickBoundingBox.max.y - chickBoundingBox.min.y;
        chickModel.position.y = chickHeight / 2; // 맵 바닥 위로 위치 조정

        if (callback) callback(chickModel, mixer);
    });
}

function handleChickMovement(keysPressed, coordinatesElement) {
    const moveSpeed = 10;
    const autoForwardSpeed = 1; // 자동으로 앞으로 가는 속도
    const gameStore = useGameStore();

    if (chickModel && countdown.value === 0 && !gameStore.isGameEnded) {
        // 자동으로 z축을 따라 앞으로 이동
        chickModel.position.z += autoForwardSpeed;

        // 키 입력에 따른 옆으로 이동 처리
        if (moveSide.value !== 0) {
            const sideDirection = new THREE.Vector3(moveSide.value, 0, 0); // 옆 방향
            chickModel.position.add(sideDirection); // moveSide 값을 직접 사용
            moveSide.value = 0; // 한 번 이동한 후 상태 리셋
        }

        // x축 이동 범위 제한
        chickModel.position.x = Math.max(
            -4.00,
            Math.min(4.00, chickModel.position.x)
        );

        const { x, y, z } = chickModel.position;
        coordinatesElement.textContent = `X: ${x.toFixed(2)}, Y: ${y.toFixed(2)}, Z: ${z.toFixed(2)}`;

        // x축 위치에 따른 정답 체크
        if (x.toFixed(2) == -4.00) {
            checkAnswer(3);
        } else if (x.toFixed(2) == 0) {
            checkAnswer(2);
        } else if (x.toFixed(2) == 4.00) {
            checkAnswer(1);
        }

        // z 좌표가 9000이 되면 경기 종료
        if (z >= 9000) {
            gameStore.endGame(); // 경기 종료 함수 호출
            alert("경기 종료!");
            setInterval(() => {
                showRunningGameResult();
            }, 9000);
        }
    }
}

export { loadChickModel, handleChickMovement, moveSide };
