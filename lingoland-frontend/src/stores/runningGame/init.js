import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";
import * as THREE from "three";
import { OrbitControls } from "three/examples/jsm/controls/OrbitControls.js";
import { ref } from "vue";
import { addLights } from "./light";
import { loadMapSection, loadNewMapSection } from "./map";
import { handleChickMovement, loadChickModel, moveSide } from "./model";
import { useGameStore } from "./gameStore";
import { updateTimer } from "./time";

let renderer, scene, mixer, camera, controls, chickModel, coinSound;
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

  loadChickModel(scene, renderer, (model, animMixer) => {
    chickModel = model;
    mixer = animMixer;
  });
  loadAndPlayBackgroundMusic(camera); // 음악 재생 함수 호출
  loadCoinSound(camera); // 코인 사운드 로드
  initCoinModels(); // 코인 모델 초기화

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
    handleChickMovement(keysPressed, coordinatesElement);
    checkForCoinCollisions(); // 코인 충돌 감지 및 처리

    renderer.render(scene, camera);
  }

  animate();
}

function initScene() {
  const scene = new THREE.Scene();
  scene.background = new THREE.Color(0x87ceeb); // 하늘색 배경 설정
  return scene;
}

function loadCoinModel(x, z) {
  let loader = new GLTFLoader();
  loader.load("/coin/scene.gltf", function (gltf) {
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

    let coinModel = gltf.scene;
    coinModel.scale.set(0.1, 0.1, 0.1);
    coinModel.position.set(x, 2, z); // 위치 설정
    scene.add(coinModel);

    mixer = new THREE.AnimationMixer(coinModel);
    const action = mixer.clipAction(gltf.animations[0]);
    action.setLoop(THREE.LoopRepeat);
    action.play();

    coinModel.userData = { isCoin: true }; // 충돌 체크를 위한 플래그 설정
  });
}

function initCoinModels() {
  const xPositions = [-4, 0, 4];

  for (let zBase = 0; zBase < 9000; zBase += 1000) {
    // 1000 단위로 반복
    let coinCount = 0;

    while (coinCount < 300) {
      // 각 구간마다 최소 300개의 코인 생성
      const z = zBase + Math.random() * 1000; // 현재 구간 내에서 z 좌표를 랜덤하게 설정
      const x = xPositions[Math.floor(Math.random() * xPositions.length)]; // x 좌표를 랜덤하게 선택
      loadCoinModel(x, z);
      coinCount++;

      // 추가 코인을 z축을 따라 0.7 간격으로 최소 3개 이상 배치
      for (let i = 1; i < 3 + Math.floor(Math.random() * 3); i++) {
        if (coinCount >= 300) break; // 코인 개수가 300개를 넘으면 중지
        loadCoinModel(x, z + i * 0.7);
        coinCount++;
      }
    }
  }
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
  if (chickModel) {
    const chickPosition = chickModel.position.clone();

    const cameraOffset = new THREE.Vector3(
      0,
      cameraSettings.height,
      -cameraSettings.distance
    );

    // 카메라 위치를 캐릭터의 뒤쪽에 배치
    camera.position.set(
      0,
      chickPosition.y + cameraOffset.y + 3,
      chickPosition.z + cameraOffset.z
    );

    // 카메라가 캐릭터를 바라보도록 설정
    camera.lookAt(0, chickPosition.y + 3, chickPosition.z);

    gameStore.updateZCoordinate(chickPosition.z);
  }
}

function loadAndPlayBackgroundMusic(camera) {
  // 오디오 리스너 추가 (카메라에 붙임)
  const listener = new THREE.AudioListener();
  camera.add(listener);

  // 오디오 객체 생성
  const sound = new THREE.Audio(listener);

  // 오디오 로더로 음악 파일 로드
  const audioLoader = new THREE.AudioLoader();
  audioLoader.load("/children-running.mp3", function (buffer) {
    sound.setBuffer(buffer);
    sound.setLoop(true); // 반복 재생
    sound.setVolume(0.5); // 볼륨 설정 (0.0에서 1.0까지)
    sound.play(); // 음악 재생
  });
}

function loadCoinSound(camera) {
  // 오디오 리스너 추가 (카메라에 붙임)
  const listener = new THREE.AudioListener();
  camera.add(listener);

  // 코인 소리 객체 생성
  coinSound = new THREE.Audio(listener);

  // 오디오 로더로 코인 소리 파일 로드
  const audioLoader = new THREE.AudioLoader();
  audioLoader.load("/coin-recieved.mp3", function (buffer) {
    coinSound.setBuffer(buffer);
    coinSound.setVolume(0.05); // 볼륨 설정
  });
}

function checkForCoinCollisions() {
  if (!chickModel) return;

  const chickBB = new THREE.Box3().setFromObject(chickModel); // 캐릭터의 바운딩 박스 계산

  scene.children.forEach((child) => {
    if (child.userData.isCoin) {
      // 코인인지 확인
      const coinBB = new THREE.Box3().setFromObject(child); // 코인의 바운딩 박스 계산

      if (chickBB.intersectsBox(coinBB)) {
        // 캐릭터와 코인의 충돌 감지

        scene.remove(child); // 충돌 시 코인을 씬에서 제거
        if (coinSound.isPlaying) {
          coinSound.stop(); // 이전 사운드가 재생 중이면 정지
        }
        coinSound.play(); // 코인 획득 소리 재생
      }
    }
  });
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
