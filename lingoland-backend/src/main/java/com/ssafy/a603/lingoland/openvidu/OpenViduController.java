package com.ssafy.a603.lingoland.openvidu;

import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

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

    @PostMapping("/webhook")
    public ResponseEntity<?> handleWebhook(@RequestBody Map<String, Object> payload) {
        String event = (String) payload.get("event");
        String participant = (String) payload.get("participant");
        System.out.println("participant = " + participant);
        System.out.println("event = " + event);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/game-end")
    public ResponseEntity<?> gameEnd(@RequestBody Map<String, Object> payload) {
        String data = (String) payload.get("data");
        System.out.println("data = " + data);
        return ResponseEntity.ok().build();
    }
}
