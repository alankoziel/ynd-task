package pl.yndtask.unit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import pl.yndtask.exception.TorExitNodeNotFoundException;
import pl.yndtask.model.ExitNodeInfo;
import pl.yndtask.service.TorExitNodeFetcher;
import pl.yndtask.service.TorExitNodeService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TorExitNodeServiceTest {

    @Mock
    private TorExitNodeFetcher torExitNodeFetcher;

    @InjectMocks
    private TorExitNodeService torExitNodeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldReturnExitNodeInfo_whenIPExists() {
        //given
        String testIp = "171.25.193.25";
        ExitNodeInfo exitNodeInfo = ExitNodeInfo.builder()
                .exitNode("64D74AAA74F30DC2CFB36343CE5D4451B9A4DBA8")
                .exitAddress(testIp)
                .published("2024-09-11 04:01:53")
                .lastStatus("2024-09-11 17:00:00")
                .exitAddressTime("2024-09-11 17:13:42")
                .build();

        when(torExitNodeFetcher.fetchTorExitNodes()).thenReturn(List.of(exitNodeInfo));

        //when
        ExitNodeInfo result = torExitNodeService.getTorExitNodeByIP(testIp);

        //then
        assertNotNull(result);
        assertEquals(testIp, result.getExitAddress());
        assertEquals("64D74AAA74F30DC2CFB36343CE5D4451B9A4DBA8", result.getExitNode());
        assertEquals("2024-09-11 04:01:53", result.getPublished());
        assertEquals("2024-09-11 17:00:00", result.getLastStatus());
        assertEquals("2024-09-11 17:13:42", result.getExitAddressTime());
    }

    @Test
    void shouldThrowTorExitNodeNotFoundException_whenIPDoesNotExist() {
        //given
        String nonExistentIp = "192.42.116.187";

        when(torExitNodeFetcher.fetchTorExitNodes()).thenReturn(List.of());

        //when & then
        assertThrows(TorExitNodeNotFoundException.class, () -> torExitNodeService.getTorExitNodeByIP(nonExistentIp));
    }
}
