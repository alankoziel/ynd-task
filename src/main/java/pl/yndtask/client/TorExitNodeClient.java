package pl.yndtask.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "torExitNodeClient", url = "${tor.check.url}")
public interface TorExitNodeClient {

    @GetMapping("/exit-addresses")
    String getTorExitNodes();
}
