package com.ssafy.a603.lingoland.openvidu;

import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class OpenViduController {

    private final OpenViduService openViduService;


    @PostMapping("/sessions")
    public String createSession() throws OpenViduJavaClientException, OpenViduHttpException {
        return openViduService.createSession();
    }

    @PostMapping("/sessions/{sessionId}/connections")
    public String generateToken(@PathVariable(value = "sessionId") String sessionId) throws OpenViduJavaClientException, OpenViduHttpException {
        return openViduService.generateToken(sessionId);
    }
}
