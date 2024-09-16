package pl.yndtask.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.yndtask.model.ExitNodeInfo;
import pl.yndtask.service.TorExitNodeService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tor")
@Slf4j
public class TorExitNodeController {

    private final TorExitNodeService torExitNodeService;

    @GetMapping("/{ip}")
    public ExitNodeInfo checkTorExitNode(@PathVariable String ip) {
        log.info("Checking if IP is a Tor exit node: {}", ip);
        return torExitNodeService.getTorExitNodeByIP(ip);
    }
}
