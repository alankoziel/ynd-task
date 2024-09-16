package pl.yndtask.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import pl.yndtask.client.TorExitNodeClient;
import pl.yndtask.model.ExitNodeInfo;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TorExitNodeFetcher {

    private final TorExitNodeClient torExitNodeClient;
    private final ExitNodeParser exitNodeParser;

    @Cacheable(value = "torExitNodes", key = "'exitNodes'")
    public List<ExitNodeInfo> fetchTorExitNodes() {
        String response = torExitNodeClient.getTorExitNodes();
        return exitNodeParser.parseString(response);
    }
}
