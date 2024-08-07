import { GLTFLoader } from "three/examples/jsm/loaders/GLTFLoader.js";
import * as THREE from "three";
import { scene,renderer } from "./init";

function loadMapSection(zPosition) {
    let mapLoader = new GLTFLoader();
    const clipPlanes = [
        new THREE.Plane(new THREE.Vector3(0, 0, -1), zPosition + 1050),
    ];
    mapLoader.load("/pirate/scene.gltf", function (gltf) {
        gltf.scene.traverse((child) => {
            if (child.isMesh) {
                child.material.needsUpdate = true;
                child.material.clippingPlanes = clipPlanes;
                if (child.material.map) {
                    child.material.map.anisotropy =
                        renderer.capabilities.getMaxAnisotropy();
                    child.material.map.needsUpdate = true;
                }
            }
        });

        gltf.scene.scale.set(0.015, 0.015, 0.015); // 크기 1.5배로 확장
        gltf.scene.position.set(0, 0, zPosition);
        scene.add(gltf.scene);
    });
}

function loadNewMapSection(zPosition) {
    let newMapLoader = new GLTFLoader();
    const clipPlanes = [
        new THREE.Plane(new THREE.Vector3(0, 0, -1), zPosition + 1050),
    ];
    newMapLoader.load("/pirate/scene.gltf", function (gltf) {
        gltf.scene.traverse((child) => {
            if (child.isMesh) {
                child.material.needsUpdate = true;
                child.material.clippingPlanes = clipPlanes;
                if (child.material.map) {
                    child.material.map.anisotropy =
                        renderer.capabilities.getMaxAnisotropy();
                    child.material.map.needsUpdate = true;
                }
            }
        });

        gltf.scene.scale.set(0.015, 0.015, 0.015); // 크기 1.5배로 확장
        gltf.scene.position.set(0, 0, zPosition);
        scene.add(gltf.scene);
    });
}

export { loadMapSection, loadNewMapSection };
