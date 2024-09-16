package pl.yndtask.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.yndtask.exception.TorExitNodeNotFoundException;
import pl.yndtask.model.ExitNodeInfo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TorExitNodeService {

    private final TorExitNodeFetcher torExitNodeFetcher;

    public ExitNodeInfo getTorExitNodeByIP(String ip) {
        List<ExitNodeInfo> torExitNodes = torExitNodeFetcher.fetchTorExitNodes();

        return torExitNodes.stream()
                .filter(exitNodeInfo -> exitNodeInfo.getExitAddress().equals(ip))
                .findFirst()
                .orElseThrow(() -> new TorExitNodeNotFoundException("IP is not a Tor exit node"));
    }

}
