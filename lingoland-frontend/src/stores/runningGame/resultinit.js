import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";
import * as THREE from "three";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls.js";
import { ref } from "vue";
import { addLights } from "./light";
import { loadMapSection, loadNewMapSection } from "./resultmap";

let renderer, scene, mixer, camera, controls;
let moveSide = ref(0);

// 카메라 설정
const cameraSettings = {
    distance: 28, // 카메라와 캐릭터 사이의 거리
    height: 14, // 카메라의 높이
    angle: Math.PI/6+Math.PI/6+Math.PI/6 , // 카메라의 각도 (라디안) // 초기 각도
};

function initDraw() {
    const canvas = document.querySelector("#c");

    scene = initScene();
    renderer = initRenderer(canvas);
    camera = initCamera();
    controls = initControls(camera, renderer);

    addLights(scene);

    loadMapSection(-100);

    window.addEventListener("resize", onWindowResize, false);

    // 카메라 애니메이션 시작
    startCameraRotationAnimation();

    function animate() {
        requestAnimationFrame(animate);
        const delta = clock.getDelta(); // delta time 계산
        if (mixer) mixer.update(delta); // delta를 이용한 애니메이션 업데이트
        controls.update(); // 마우스 조작 업데이트

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
    
    // Set initial camera position
    camera.position.set(
        cameraSettings.distance * Math.sin(cameraSettings.angle),
        cameraSettings.height,
        cameraSettings.distance * Math.cos(cameraSettings.angle)
    );
    
    // Set the camera to look slightly above the target point
    camera.lookAt(0, cameraSettings.height + 20, 0);

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

function updateCameraPosition() {
    // 카메라의 최종 위치를 목표로 설정
    const cameraOffset = new THREE.Vector3(
        cameraSettings.distance * Math.sin(cameraSettings.angle),
        cameraSettings.height,
        cameraSettings.distance * Math.cos(cameraSettings.angle)
    );

    camera.lookAt(0, cameraSettings.height + 20, 0); // Look at a point at the character's height directly ahead
}

function onWindowResize() {
    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();
    renderer.setSize(window.innerWidth, window.innerHeight);
}

function startCameraRotationAnimation() {
    const rotationDuration = 4000; // 3초 동안 애니메이션
    const rotationAngle = Math.PI / 4; // 60도 (Math.PI / 3)
    const startAngle = cameraSettings.angle;
    const endAngle = startAngle - rotationAngle; // 반대 방향으로 회전
    const startTime = performance.now(); // 애니메이션 시작 시간

    function animateRotation() {
        const currentTime = performance.now();
        const elapsedTime = currentTime - startTime;
        const progress = Math.min(elapsedTime / rotationDuration, 1); // 애니메이션 진행 상태 (0에서 1로)

        // Calculate the current angle for rotation (opposite direction)
        const currentAngle = startAngle - progress * rotationAngle;

        // Update camera position
        camera.position.set(
            cameraSettings.distance * Math.sin(currentAngle),
            cameraSettings.height,
            cameraSettings.distance * Math.cos(currentAngle)
        );

        // Set the camera to look slightly above the target point
        camera.lookAt(0, cameraSettings.height + 20, 0);

        if (progress < 1) {
            requestAnimationFrame(animateRotation); // Continue animation until complete
        } else {
            // Set final angle after animation completes
            cameraSettings.angle = currentAngle;
        }
    }

    animateRotation(); // Start animation
}


const clock = new THREE.Clock();

export {
    renderer,
    scene,
    mixer,
    camera,
    controls,
    moveSide,
    cameraSettings,
    updateCameraPosition,
    initDraw,
}
