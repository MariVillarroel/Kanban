package com.kanban.notifications;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
  private final JavaMailSender mailSender;
  public EmailService(JavaMailSender sender){ this.mailSender = sender; }

  public void sendTaskCreated(Long userId, TaskCreatedEvent evt){
    // TODO: lookup real email by userId; for demo, a placeholder
    String to = "demo@example.com";
    SimpleMailMessage msg = new SimpleMailMessage();
    msg.setTo(to);
    msg.setSubject("[Kanban] Nueva tarea: " + evt.title());
    msg.setText("Se creó la tarea #" + evt.taskId() + " en tu proyecto.");
    mailSender.send(msg);
  }
}
