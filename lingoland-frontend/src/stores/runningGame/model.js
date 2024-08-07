import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";
import * as THREE from "three";
// import { scene, renderer,} from "./init";
import { countdown } from "./time";
import { checkAnswer } from "./question";
import { useGameStore } from "./gameStore";
import { useOpenviduStore } from "@/stores/openvidu";
import { ref } from "vue";

const openviduStore = useOpenviduStore();
const { OV,session } = openviduStore;
let moveSide = ref(0);
let model;
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

function loadModel(scene, renderer, callback) {
    const loader = new GLTFLoader();
    loader.load("/little_cat/scene.gltf", function (gltf) {
        gltf.scene.traverse((child) => {
            if (child.isMesh) {
                child.material.needsUpdate = true;
                if (child.material.map) {
                    child.material.map.anisotropy = renderer.capabilities.getMaxAnisotropy();
                    child.material.map.needsUpdate = true;
                }
            }
        });

        model = gltf.scene; // 모델을 저장
        model.scale.set(6,6,6); // 크기 몇 배
        scene.add(model);
        mixer = new THREE.AnimationMixer(model);
        const action = mixer.clipAction(gltf.animations[0]);
        action.play();

        model.rotation.y = 0 // 90도 회전 (왼쪽을 보던 방향을 정면으로 조정)
    
        const modelBoundingBox = new THREE.Box3().setFromObject(model);
        const modelHeight = modelBoundingBox.max.y - modelBoundingBox.min.y;
        model.position.y = modelHeight / 2; // 맵 바닥 위로 위치 조정

        if (callback) callback(model, mixer);
    });
}

function handleMovement(keysPressed, coordinatesElement) {
    const moveSpeed = 10;
    const autoForwardSpeed = 1; // 자동으로 앞으로 가는 속도
    const gameStore = useGameStore();

    if (model && countdown.value === 0 && !gameStore.isGameEnded) {
        // 자동으로 z축을 따라 앞으로 이동
        model.position.z += autoForwardSpeed;

        // 키 입력에 따른 옆으로 이동 처리
        if (moveSide.value !== 0) {
            const sideDirection = new THREE.Vector3(moveSide.value, 0, 0); // 옆 방향
            model.position.add(sideDirection); // moveSide 값을 직접 사용
            moveSide.value = 0; // 한 번 이동한 후 상태 리셋
        }

        // x축 이동 범위 제한
        model.position.x = Math.max(
            -4.00,
            Math.min(4.00, model.position.x)
        );

        const { x, y, z } = model.position;
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


export { loadModel, handleMovement ,moveSide};
