package Mend.api;

import Mend.dto.ScanResponseDTO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class SSEController {

    // Store all active SSE emitters keyed by scanId
    private final Map<Integer, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    @GetMapping(value = "/api/scan-status/{scanId}/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter streamScanStatus(@PathVariable Integer scanId) {
        SseEmitter emitter = new SseEmitter();
        sseEmitters.put(scanId, emitter);

        emitter.onCompletion(() -> sseEmitters.remove(scanId));
        emitter.onTimeout(() -> sseEmitters.remove(scanId));

        return emitter;
    }

    public void sendScanUpdate(Integer scanId, ScanResponseDTO scanResponseDTO) {
        SseEmitter emitter = sseEmitters.get(scanId);
        if (emitter != null) {
            try {
                emitter.send(SseEmitter.event().name("scan-status").data(scanResponseDTO));
            } catch (IOException e) {
                emitter.completeWithError(e);
                sseEmitters.remove(scanId);
            }
        }
    }

    public void sendCompletion(Integer scanId) {
        SseEmitter emitter = sseEmitters.get(scanId);
        if (emitter != null) {
            emitter.complete();
            sseEmitters.remove(scanId);
        }
    }
}
