package com.kanban.security;
public final class Sanitizer {
  private Sanitizer(){}
  public static String safe(String input){
    if (input == null) return null;
    return input.replaceAll("[\n\r\t]", " ")
                .replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;");
  }
}
