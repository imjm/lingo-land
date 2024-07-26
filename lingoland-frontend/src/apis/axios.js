import router from "@/router";
import { Mutex } from "async-mutex";
import axios from "axios";
import { httpStatus } from "./http-status.js";

const { VITE_SERVER_URL, VITE_LOCAL_URL } = import.meta.env;

export const instance = axios.create({
    baseURL: VITE_LOCAL_URL,
});

// Request 발생 시 적용할 기본 속성 설정.
instance.defaults.headers.post["Content-Type"] = "application/json";
instance.defaults.headers.put["Content-Type"] = "application/json";

instance.interceptors.request.use(
    (config) => {
        return config;
    },
    (error) => {
        return Promise.reject(error);
    }
);

let tokenRefreshingMutex = new Mutex();
instance.interceptors.response.use(
    (response) => {
        return response;
    },

    async (error) => {
        const { config, response } = error;

        // 토큰 재발급 진행
        if (response.status === httpStatus.UNAUTHORIZED) {
            // /auth/refresh로 요청을 진행해야 하므로, 기존의 요청을 저장.
            const prevRequest = config;

            // 다른 요청이 토큰 갱신을 시도하고 있으면, 완료될 때까지 기다림
            if (tokenRefreshingMutex.isLocked()) {
                await tokenRefreshingMutex.waitForUnlock();
            }

            // 토큰이 없는 것을 가장 먼저 확인한 요청이 토큰 발급을 시도함
            // Critical Section below =========================================================
            try {
                tokenRefreshingMutex.acquire();

                // 새 액세스 토큰이 응답되면,
                const refreshResponse = await instance
                    .post("/reissue", {
                        withCredentials: true,
                    })
                    .then((response) => {
                        return Promise.resolve(response); // 결과를 리턴해서 refreshResponse에 넣어줌
                    });

                // 모든 요청에 access token이 포함되어 가도록 보장
                const newAccessToken = refreshResponse.headers.authorization;

                instance.defaults.headers.common["Authorization"] =
                    newAccessToken;
                prevRequest.headers.authorization = newAccessToken;
            } catch (error) {
                // 토큰 갱신에 실패하면 Circuit break
                // router.push({ name: "login" });
                return Promise.reject(error);
            } finally {
                tokenRefreshingMutex.release();
            }
            // Critical Section above ========================================================

            // Critical section 내부의 작업을 통해 모든 요청에 access token이 가는 것이 보장됨
            return instance(prevRequest);
        } else if (response.status === httpStatus.FORBIDDEN) {
            router.push({ name: "login" });
        }

        return Promise.reject(error);
    }
);
