package com.kanban.notifications;
import org.springframework.data.jpa.repository.JpaRepository;

interface NotificationPreferenceRepo extends JpaRepository<NotificationPreference, Long>{
  NotificationPreference findByUserId(Long userId);
}

interface NotificationRepo extends JpaRepository<NotificationEntity, Long>{}

interface NotificationLogRepo extends JpaRepository<NotificationLog, Long>{}
