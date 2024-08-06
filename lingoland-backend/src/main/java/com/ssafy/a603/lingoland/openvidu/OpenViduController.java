package com.ssafy.a603.lingoland.openvidu;

import com.ssafy.a603.lingoland.member.security.CurrentUser;
import com.ssafy.a603.lingoland.member.security.CustomUserDetails;
import io.openvidu.java.client.OpenViduHttpException;
import io.openvidu.java.client.OpenViduJavaClientException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createSession() throws OpenViduJavaClientException, OpenViduHttpException {
        return ResponseEntity.status(HttpStatus.OK).body(openViduService.createSession());
    }

    @PostMapping("/sessions/{sessionId}/connections")
    public ResponseEntity<?> generateToken(@PathVariable(value = "sessionId") String sessionId,
                                        @CurrentUser CustomUserDetails customUserDetails) throws OpenViduJavaClientException, OpenViduHttpException {
        String token = openViduService.generateToken(sessionId, customUserDetails);
        return ResponseEntity.status(HttpStatus.OK).body(token);
    }
}
