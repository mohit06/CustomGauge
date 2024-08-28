package com.custom.app.metrics;

import io.micrometer.core.instrument.Gauge;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tag;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class CustomMetricSource {

    private final MeterRegistry registry;
    HashMap<String, String> marksData = new HashMap<String, String>();
    private Gauge gauge;
    private AtomicInteger value = new AtomicInteger(0);
    private static final String metricName = "students.marks.data";

    public CustomMetricSource(MeterRegistry registry){
        this.registry = registry;
        initializeMap();

        gauge = Gauge.builder(metricName, () -> this.value)
                .description("This metrics show the marks obtained by students")
                .tags(getTags())
                .register(registry);

    }

    private void initializeMap() {
        marksData.put("Rohan","78");
        marksData.put("Sohan","65");
        marksData.put("Vijay","89");
        marksData.put("Roy","91");
        this.value.addAndGet(4);
    }

    private void addTagToExistingMetric(MeterRegistry registry, Tag tag) {
        this.value.incrementAndGet();
        this.gauge = Gauge.builder(metricName, () -> 0)
                .description("This metrics show the marks obtained by students")
                .tag(tag.getKey(),tag.getValue())
                .register(registry);
    }

    private List<Tag> getTags() {
        return marksData.keySet().stream().map(key -> Tag.of(key,marksData.get(key))).toList();
    }

    public void addDataToMarksMap(String name, String marks) {
        marksData.put(name,marks);
        addTagToExistingMetric(this.registry, Tag.of(name, marks));
    }

}