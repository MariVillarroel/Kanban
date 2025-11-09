package org.example.tareas.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

import java.util.concurrent.ThreadLocalRandom;

@Service
public class NotificationServiceImpl implements NotificationService {

  private final Counter total;
  private final Counter errors;

  public NotificationServiceImpl(MeterRegistry registry) {
    this.total = registry.counter("tasks.notification.total");
    this.errors = registry.counter("tasks.notification.errors");
  }

  @Override
  public boolean notifyAssignment(Long taskId, Long userId, boolean forceFail) {
    total.increment();
    boolean failed = forceFail || ThreadLocalRandom.current().nextInt(100) < 5; // 5% random fail
    if (failed) {
      errors.increment();
    }
    return failed;
  }
}
