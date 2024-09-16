package pl.yndtask.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureWireMock(port = 0)
@TestPropertySource(properties = {
        "tor.check.url=http://localhost:${wiremock.server.port}",
        "spring.cache.caffeine.spec=maximumSize=500,expireAfterWrite=1m"
})
class TorExitNodeControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        WireMock.reset();
    }
    private static final String MOCK_TOR_EXIT_NODE_RESPONSE =
            """
                    ExitNode 64D74AAA74F30DC2CFB36343CE5D4451B9A4DBA8
                    Published 2024-09-11 04:01:53
                    LastStatus 2024-09-11 17:00:00
                    ExitAddress 171.25.193.25 2024-09-11 17:13:42
                    """;

    @Test
    void checkTorExitNode_success() throws Exception {
        stubFor(WireMock.get(urlEqualTo("/exit-addresses"))
                .willReturn(ok(MOCK_TOR_EXIT_NODE_RESPONSE)));

        mockMvc.perform(get("/api/v1/tor/171.25.193.25"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.exitNode").value("64D74AAA74F30DC2CFB36343CE5D4451B9A4DBA8"))
                .andExpect(jsonPath("$.exitAddress").value("171.25.193.25"))
                .andExpect(jsonPath("$.published").value("2024-09-11 04:01:53"))
                .andExpect(jsonPath("$.lastStatus").value("2024-09-11 17:00:00"))
                .andExpect(jsonPath("$.exitAddressTime").value("2024-09-11 17:13:42"));
    }

    @ParameterizedTest
    @ValueSource(strings = {MOCK_TOR_EXIT_NODE_RESPONSE, " "})
    @NullSource
    void checkTorExitNode_notFound(String body) throws Exception {
        stubFor(WireMock.get(urlEqualTo("/exit-addresses"))
                .willReturn(ok(body)));

        mockMvc.perform(get("/api/v1/tor/192.42.116.187"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

}
