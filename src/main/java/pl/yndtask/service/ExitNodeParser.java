package pl.yndtask.service;

import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pl.yndtask.exception.TorExitNodeParseException;
import pl.yndtask.model.ExitNodeInfo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import java.util.function.BiConsumer;

@Service
@Slf4j
public class ExitNodeParser {

    private final Map<String, BiConsumer<ExitNodeInfo, String>> fieldSetters = new HashMap<>();

    public ExitNodeParser() {
        fieldSetters.put("ExitNode", ExitNodeInfo::setExitNode);
        fieldSetters.put("Published", ExitNodeInfo::setPublished);
        fieldSetters.put("LastStatus", ExitNodeInfo::setLastStatus);
        fieldSetters.put("ExitAddress", this::setExitAddress);
    }

    public List<ExitNodeInfo> parseString(String input) {
        if (StringUtils.isEmpty(input)) {
            return Collections.emptyList();
        }
        log.info("Parsing Tor exit node data");
        List<ExitNodeInfo> exitNodeList = new ArrayList<>();
        ExitNodeInfo info = new ExitNodeInfo();
        try (BufferedReader br = new BufferedReader(new StringReader(input))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) {
                    continue;
                }

                String[] parts = line.split(" ", 2);
                String key = parts[0];
                String value = parts.length > 1 ? parts[1] : "";

                BiConsumer<ExitNodeInfo, String> setter = fieldSetters.get(key);
                if (setter != null) {
                    setter.accept(info, value);
                }

                if (key.equals("ExitAddress")) {
                    exitNodeList.add(info);
                    info = new ExitNodeInfo();
                }
            }
        } catch (Exception e) {
            log.error("Error while parsing Tor exit node data", e);
            throw new TorExitNodeParseException("Error while parsing Tor exit node data", e);
        }
        return exitNodeList;
    }

    private void setExitAddress(ExitNodeInfo info, String value) {
        String[] parts = value.split(" ");
        info.setExitAddress(parts[0]);
        info.setExitAddressTime(parts[1] + " " + parts[2]);
    }
}
