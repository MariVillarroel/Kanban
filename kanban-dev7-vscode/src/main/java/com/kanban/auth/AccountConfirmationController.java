package com.kanban.auth;

import com.kanban.exception.BusinessException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/api/auth")
public class AccountConfirmationController {

  @PostMapping("/confirm")
  public ResponseEntity<?> confirm(@RequestParam("token") String token){
    if (token == null || token.isBlank())
      throw new BusinessException("INVALID_TOKEN", "Token inválido o expirado."){};
    // Aquí validar firma/expiración y activar usuario (mock ok)
    return ResponseEntity.ok().build();
  }
}
