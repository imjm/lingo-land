import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";
import * as THREE from "three";
import { countdown } from "./time";
import { checkAnswer } from "./question";
import { useGameStore } from "./gameStore";
import { useOpenviduStore } from "@/stores/openvidu";
import { ref } from "vue";
import { storeToRefs } from "pinia";

const gameChars = ref([]);
const openviduStore = useOpenviduStore();
const { session } = openviduStore;
const { participants } = storeToRefs(openviduStore);

let moveSide = ref(0);
let model;
let mixer;

const models = [
    { name: "chick", path: "/cute_chick/scene.gltf", scale: 1, rotation: -Math.PI / 2 },
    { name: "squid", path: "/squid/scene.gltf", scale: 10, rotation: 0 },
    { name: "dragonfly", path: "/dragonfly/scene.gltf", scale: 3, rotation: -Math.PI / 2 },
    { name: "unicorn", path: "/just_a_unicorn/scene.gltf", scale: 0.5, rotation: 0 },
    { name: "dog", path: "/stylized_dog_low_poly/scene.gltf", scale: 3, rotation: 0 },
    { name: "cat", path: "/little_cat/scene.gltf", scale: 7, rotation: 0 },
];

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

function assignRandomModel(scene, renderer, callback) {
    const assignedModelIndices = [];
    for (const participant of participants.value) {
        let randomIndex;
        do {
            randomIndex = Math.floor(Math.random() * models.length);
        } while (assignedModelIndices.includes(randomIndex));
        assignedModelIndices.push(randomIndex);
        const modelInfo = models[randomIndex];
        loadModel(scene, renderer, modelInfo, callback);

        // 참가자의 모델 정보를 저장
        gameChars.value.push({
            connectionId: participant.connectionId,
            userId: participant.userId,
            char: modelInfo
        });
    }
}

function loadModel(scene, renderer, modelInfo, callback) {
    const loader = new GLTFLoader();
    loader.load(modelInfo.path, function (gltf) {
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
        model.scale.set(modelInfo.scale, modelInfo.scale, modelInfo.scale); // 크기 몇 배
        scene.add(model);
        mixer = new THREE.AnimationMixer(model);
        const action = mixer.clipAction(gltf.animations[0]);
        action.play();

        model.rotation.y = modelInfo.rotation; // 모델 회전 설정
    
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

export { loadModel, handleMovement, moveSide, assignRandomModel, gameChars };
