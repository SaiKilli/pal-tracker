package io.pivotal.pal.tracker.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    private Map<String, String> envMap;


    public EnvController(@Value("${PORT:NOT SET}") String PORT,
                         @Value("${MEMORY_LIMIT:NOT SET}") String MEMORY_LIMIT,
                         @Value("${CF_INSTANCE_INDEX:NOT SET}") String CF_INSTANCE_INDEX,
                         @Value("${CF_INSTANCE_ADDR:NOT SET}") String CF_INSTANCE_ADDR) {
        envMap = new HashMap<>();
        envMap.put("PORT", PORT);
        envMap.put("MEMORY_LIMIT", MEMORY_LIMIT);
        envMap.put("CF_INSTANCE_INDEX", CF_INSTANCE_INDEX);
        envMap.put("CF_INSTANCE_ADDR", CF_INSTANCE_ADDR);
    }

    @RequestMapping("/env")
    public Map<String, String> getEnv() {
        return envMap;
    }

  }
