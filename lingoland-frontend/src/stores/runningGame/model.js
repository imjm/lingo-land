import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";
import * as THREE from "three";
import { scene, renderer, mixer, chickModel, moveSide } from "./init";
import { countdown } from "./time";
import { checkAnswer } from "./question";

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


function handleChickMovement(keysPressed, coordinatesElement) {
    const moveSpeed = 10;
    const moveForward =
        (keysPressed["ArrowUp"] || keysPressed["w"] ? 1 : 0) -
        (keysPressed["ArrowDown"] || keysPressed["s"] ? 1 : 0);

    if (chickModel && countdown.value === 0) {
        if (moveForward !== 0) {
            const forwardDirection = new THREE.Vector3(1, 0, 0); // 앞쪽 방향
            forwardDirection.applyQuaternion(chickModel.quaternion); // 현재 회전에 따라 방향 벡터를 업데이트합니다.
            chickModel.position.addScaledVector(
                forwardDirection,
                moveForward * moveSpeed
            );
        }

        if (moveSide.value !== 0) {
            const sideDirection = new THREE.Vector3(0, 0, -1); // 옆 방향
            sideDirection.applyQuaternion(chickModel.quaternion); // 현재 회전에 따라 방향 벡터를 업데이트합니다.
            chickModel.position.addScaledVector(sideDirection, moveSide.value); // moveSide 값을 직접 사용
            moveSide.value = 0; // 한 번 이동한 후 상태 리셋
        }

        chickModel.position.x = Math.max(
            -4.00,
            Math.min(4.00, chickModel.position.x)
        );

        const { x, y, z } = chickModel.position;
        coordinatesElement.textContent = `X: ${x.toFixed(2)}, Y: ${y.toFixed(
            2
        )}, Z: ${z.toFixed(2)}`;

        if (x.toFixed(2) == -4.00) {
            checkAnswer(1);
        } else if (x.toFixed(2) == 0) {
            checkAnswer(2);
        } else if (x.toFixed(2) == 4.00) {
            checkAnswer(3);
        }
    }
}


export { loadChickModel, handleChickMovement };
