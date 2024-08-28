package com.custom.app.metrics;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MarksRestController {

    private final CustomMetricSource customMetricSource;

    public MarksRestController(CustomMetricSource customMetricSource){
        this.customMetricSource = customMetricSource;
    }

    @GetMapping("/{name}/{marks}")
    public void addData(@PathVariable String name, @PathVariable String marks){
        customMetricSource.addDataToMarksMap(name,marks);
    }
}
