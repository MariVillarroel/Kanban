package com.kanban.notifications;
import jakarta.persistence.*;

@Entity @Table(name="notification_preferences")
public class NotificationPreference {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY) private Long id;
  private Long userId;
  private boolean inApp = true;
  private boolean email = false;

  public Long getId(){return id;}
  public Long getUserId(){return userId;}
  public void setUserId(Long u){this.userId=u;}
  public boolean isInApp(){return inApp;}
  public void setInApp(boolean v){this.inApp=v;}
  public boolean isEmail(){return email;}
  public void setEmail(boolean v){this.email=v;}
}
