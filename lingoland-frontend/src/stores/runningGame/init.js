import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";
import * as THREE from "three";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls.js";
import { ref } from "vue";
import { addLights } from "./light";
import { loadMapSection, loadNewMapSection } from "./map";
import { handleChickMovement } from "./model";

import { updateTimer } from "./time";

let renderer, scene, mixer, camera, controls, chickModel;
let moveSide = ref(0);

// 카메라 설정
const cameraSettings = {
    distance: 10, // 카메라와 캐릭터 사이의 거리
    height: 5, // 카메라의 높이
    angle: -Math.PI / 2, // 카메라의 각도 (라디안)
};

let keysPressed = setupKeyListeners();

function initDraw() {
    const canvas = document.querySelector("#c");
    // const timerElement = document.querySelector("#timer");
    const coordinatesElement = document.querySelector("#coordinates");

    scene = initScene();
    renderer = initRenderer(canvas);
    camera = initCamera();
    controls = initControls(camera, renderer);

    addLights(scene);

    for (let i = -1; i < 5; i++) {
        loadMapSection(i * 1950 - 650);
        loadNewMapSection(i * 1950 + 325);
    }

    loadChickModel();
    
    let startTime = Date.now(); // 타이머 시작 시간
    
    window.addEventListener("resize", onWindowResize, false);

    function animate() {
        requestAnimationFrame(animate);
        const delta = clock.getDelta(); // delta time 계산
        if (mixer) mixer.update(delta); // delta를 이용한 애니메이션 업데이트
        controls.update(); // 마우스 조작 업데이트

        updateCameraPosition();

        updateTimer(startTime);
        handleChickMovement(keysPressed, coordinatesElement);

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

function loadChickModel() {
    let loader = new GLTFLoader();
    loader.load("/cute_chick/scene.gltf", function (gltf) {
        gltf.scene.traverse((child) => {
            if (child.isMesh) {
                child.material.needsUpdate = true;
                if (child.material.map) {
                    child.material.map.anisotropy =
                        renderer.capabilities.getMaxAnisotropy();
                    child.material.map.needsUpdate = true;
                }
            }
        });

        chickModel = gltf.scene; // 병아리 모델을 저장
        chickModel.scale.set(1, 1, 1); // 크기 1배
        scene.add(chickModel);
        mixer = new THREE.AnimationMixer(chickModel);
        const action = mixer.clipAction(gltf.animations[0]);
        action.play();

        chickModel.rotation.y = -Math.PI / 2; // 90도 회전 (왼쪽을 보던 방향을 정면으로 조정)

        const chickBoundingBox = new THREE.Box3().setFromObject(chickModel);
        const chickHeight = chickBoundingBox.max.y - chickBoundingBox.min.y;
        chickModel.position.y = chickHeight / 2; // 맵 바닥 위로 위치 조정
    });
}

// 키보드 이벤트
function setupKeyListeners() {
    let keysPressed = {};

    window.addEventListener("keydown", (event) => {
        keysPressed[event.key] = true;

        // 옆으로 이동 처리
        if (event.key === "ArrowLeft" || event.key === "a") {
            moveSide.value = 5.25; // 왼쪽으로 이동
        }
        if (event.key === "ArrowRight" || event.key === "d") {
            moveSide.value = -5.25; // 오른쪽으로 이동
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
    if (chickModel) {
        const chickPosition = chickModel.position.clone();
        const chickRotation = chickModel.rotation.y;

        const cameraOffset = new THREE.Vector3(
            cameraSettings.distance * Math.sin(cameraSettings.angle),
            cameraSettings.height,
            cameraSettings.distance * Math.cos(cameraSettings.angle)
        );

        cameraOffset.applyMatrix4(
            new THREE.Matrix4().makeRotationY(chickRotation)
        );
        camera.position.copy(chickPosition).add(cameraOffset);
        camera.lookAt(chickPosition); // 카메라가 병아리 모델을 항상 바라보도록 설정
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
    chickModel,
    moveSide,
    cameraSettings,
    updateCameraPosition,
    initDraw,
    setupKeyListeners,
};
