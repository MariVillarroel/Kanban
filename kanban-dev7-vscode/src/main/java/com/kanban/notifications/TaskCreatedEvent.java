package com.kanban.notifications;
public record TaskCreatedEvent(Long taskId, Long creatorId, Long projectId, String title) {}
