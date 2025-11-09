package org.example.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {

  private final MeterRegistry registry;

  public MetricsController(MeterRegistry registry) {
    this.registry = registry;
  }

  @GetMapping("/dashboard")
  public Map<String, Object> dashboard() {
    Timer timer = registry.find("tasks.assignment.duration").timer();
    long count = (timer != null) ? timer.count() : 0L;
    double totalMs = (timer != null) ? timer.totalTime(TimeUnit.MILLISECONDS) : 0.0;
    double avgMs = (count > 0) ? totalMs / count : 0.0;

    Counter total = registry.find("tasks.notification.total").counter();
    Counter errors = registry.find("tasks.notification.errors").counter();
    double totalVal = (total != null) ? total.count() : 0.0;
    double errorVal = (errors != null) ? errors.count() : 0.0;
    double errorRate = (totalVal > 0) ? (errorVal / totalVal) : 0.0;

    return Map.of(
      "assignment.count", count,
      "assignment.avgMs", avgMs,
      "notification.total", totalVal,
      "notification.errors", errorVal,
      "notification.errorRate", errorRate
    );
  }
}
