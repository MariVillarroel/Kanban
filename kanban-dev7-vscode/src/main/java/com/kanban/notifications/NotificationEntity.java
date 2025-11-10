package com.kanban.notifications;
import jakarta.persistence.*;
import java.time.Instant;

@Entity @Table(name="notifications")
public class NotificationEntity {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private Long userId;
  private String title;
  @Column(length=2000) private String body;
  private Instant createdAt = Instant.now();
  private boolean readFlag = false;

  public Long getId(){return id;}
  public Long getUserId(){return userId;}
  public void setUserId(Long u){this.userId=u;}
  public String getTitle(){return title;}
  public void setTitle(String t){this.title=t;}
  public String getBody(){return body;}
  public void setBody(String b){this.body=b;}
  public Instant getCreatedAt(){return createdAt;}
  public boolean isReadFlag(){return readFlag;}
  public void setReadFlag(boolean r){this.readFlag=r;}
}
