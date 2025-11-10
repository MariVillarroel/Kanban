package com.kanban.notifications;
import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="notification_logs")
public class NotificationLog {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private String channel; // IN_APP / EMAIL
  private String templateName;
  private Long userId;
  private String status; // SENT / ERROR
  @Column(length=1000) private String errorMsg;
  private Instant createdAt = Instant.now();

  public void mark(String channel, String template, Long userId, String status, String error){
    this.channel = channel; this.templateName = template; this.userId=userId;
    this.status=status; this.errorMsg=error;
  }
}
