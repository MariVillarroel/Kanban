package com.kanban.notifications;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class NotificationService {

  private final EmailService emailService;
  private final NotificationPreferenceRepo prefRepo;
  private final NotificationRepo notifRepo;
  private final NotificationLogRepo logRepo;

  public NotificationService(EmailService e, NotificationPreferenceRepo p, NotificationRepo n, NotificationLogRepo l){
    this.emailService=e; this.prefRepo=p; this.notifRepo=n; this.logRepo=l;
  }

  public void onTaskCreated(TaskCreatedEvent evt, List<Long> watcherUserIds){
    for(Long uid: watcherUserIds){
      // IN-APP
      NotificationEntity n = new NotificationEntity();
      n.setUserId(uid);
      n.setTitle("Nueva tarea creada");
      n.setBody("Título: " + evt.title() + " (#" + evt.taskId() + ")");
      notifRepo.save(n);

      NotificationLog log = new NotificationLog();
      log.mark("IN_APP", "task_created", uid, "SENT", null);
      logRepo.save(log);

      // EMAIL opcional
      sendEmailIfEnabled(uid, evt);
    }
  }

  @Async
  void sendEmailIfEnabled(Long userId, TaskCreatedEvent evt){
    try{
      NotificationPreference pref = prefRepo.findByUserId(userId);
      if (pref != null && pref.isEmail()){
        emailService.sendTaskCreated(userId, evt);
        NotificationLog log = new NotificationLog();
        log.mark("EMAIL", "task_created", userId, "SENT", null);
        logRepo.save(log);
      }
    }catch(Exception ex){
      NotificationLog log = new NotificationLog();
      log.mark("EMAIL", "task_created", userId, "ERROR", ex.getMessage());
      logRepo.save(log);
    }
  }
}
