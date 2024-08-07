import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";
import * as THREE from "three";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls.js";
import { ref } from "vue";
import { addLights } from "./light";
import { loadMapSection, loadNewMapSection } from "./map";
import { handleMovement, loadModel, moveSide, assignRandomModel } from "./model";
import { useGameStore } from "./gameStore";
import { updateTimer } from "./time";

let renderer, scene, mixer, camera, controls, model;
const gameStore = useGameStore();

// 카메라 설정
const cameraSettings = {
    distance: 10, // 카메라와 캐릭터 사이의 거리
    height: 5, // 카메라의 높이
    angle: -Math.PI / 2, // 카메라의 각도 (라디안)
};

let keysPressed = setupKeyListeners();

function initDraw() {
    const canvas = document.querySelector("#c");
    const coordinatesElement = document.querySelector("#coordinates");

    scene = initScene();
    renderer = initRenderer(canvas);
    camera = initCamera();
    controls = initControls(camera, renderer);

    addLights(scene);

    for (let i = -1; i < 10; i++) {
        loadMapSection(i * 1950 - 650);
        loadNewMapSection(i * 1950 + 325);
    }

    assignRandomModel(scene, renderer, (model1, animMixer) => {
        model = model1;
        mixer = animMixer;
    });

    let startTime = Date.now(); // 타이머 시작 시간

    window.addEventListener("resize", onWindowResize, false);

    function animate() {
        if (gameStore.isGameEnded) {
            return; // 게임 종료 시 애니메이션 중지
        }

        requestAnimationFrame(animate);
        const delta = clock.getDelta(); // delta time 계산
        if (mixer) mixer.update(delta); // delta를 이용한 애니메이션 업데이트
        controls.update(); // 마우스 조작 업데이트

        updateCameraPosition();

        updateTimer(startTime);
        handleMovement(keysPressed, coordinatesElement);

        renderer.render(scene, camera);
    }

    animate();
}

function initScene() {
    const scene = new THREE.Scene();
    scene.background = new THREE.Color(0x87ceeb); // 하늘색 배경 설정
    return scene;
}

function initRenderer(canvas) {
    const renderer = new THREE.WebGLRenderer({
        canvas: canvas,
        antialias: true,
    });
    renderer.outputEncoding = THREE.sRGBEncoding;
    renderer.localClippingEnabled = true; // 클리핑 평면 사용 활성화
    renderer.setPixelRatio(window.devicePixelRatio);
    renderer.setSize(window.innerWidth, window.innerHeight);
    return renderer;
}

function initCamera() {
    const camera = new THREE.PerspectiveCamera(
        75,
        window.innerWidth / window.innerHeight,
        0.5,
        1000
    );
    return camera;
}

function initControls(camera, renderer) {
    const controls = new OrbitControls(camera, renderer.domElement);
    controls.enableDamping = true; // 부드러운 움직임을 위해 감속 효과 추가
    controls.dampingFactor = 0.25;
    controls.screenSpacePanning = false;
    controls.maxPolarAngle = Math.PI / 2;
    controls.enableZoom = false; // 줌 기능 비활성화
    return controls;
}

function setupKeyListeners() {
    let keysPressed = {};

    window.addEventListener("keydown", (event) => {
        keysPressed[event.key] = true;

        // 옆으로 이동 처리
        if (event.key === "ArrowLeft" || event.key === "a") {
            moveSide.value = 4; // 왼쪽으로 이동
        }
        if (event.key === "ArrowRight" || event.key === "d") {
            moveSide.value = -4; // 오른쪽으로 이동
        }
    });

    window.addEventListener("keyup", (event) => {
        keysPressed[event.key] = false;

        if (event.key === "ArrowLeft" || event.key === "a") {
            moveSide.value = 0; // 옆 이동 상태 리셋
        }
        if (event.key === "ArrowRight" || event.key === "d") {
            moveSide.value = 0; // 옆 이동 상태 리셋
        }
    });

    return keysPressed;
}

function updateCameraPosition() {
    if (model) {
        const modelPosition = model.position.clone();

        const cameraOffset = new THREE.Vector3(
            0,
            cameraSettings.height,
            -cameraSettings.distance
        );

        // 카메라 위치를 캐릭터의 뒤쪽에 배치
        camera.position.set(
            0,
            modelPosition.y + cameraOffset.y+3,
            modelPosition.z + cameraOffset.z
        );

        // 카메라가 캐릭터를 바라보도록 설정
        camera.lookAt(0, modelPosition.y+3, modelPosition.z);

        gameStore.updateZCoordinate(modelPosition.z);
    }
}



function onWindowResize() {
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(window.innerWidth, window.innerHeight);
}

const clock = new THREE.Clock();

export {
    renderer,
    scene,
    mixer,
    camera,
    controls,
    model,
    moveSide,
    cameraSettings,
    updateCameraPosition,
    initDraw,
    setupKeyListeners,
};
