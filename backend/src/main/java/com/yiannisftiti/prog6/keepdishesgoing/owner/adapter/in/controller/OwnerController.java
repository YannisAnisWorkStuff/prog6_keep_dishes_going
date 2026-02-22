package com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.in.controller;

import com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.in.request.RegisterOwnerRequest;
import com.yiannisftiti.prog6.keepdishesgoing.owner.adapter.in.response.OwnerDTO;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.Owner;
import com.yiannisftiti.prog6.keepdishesgoing.owner.domain.exceptions.OwnerNotFoundException;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.RegisterOwnerCommand;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.in.RegisterOwnerUseCase;
import com.yiannisftiti.prog6.keepdishesgoing.owner.port.out.owner.LoadOwnerPort;
import com.yiannisftiti.prog6.keepdishesgoing.shared.util.PasswordEncryption;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owners")
public class OwnerController {

    private final RegisterOwnerUseCase registerOwnerUseCase;
    private final PasswordEncryption passwordEncryption;
    private final LoadOwnerPort loadOwnerPort;

    public OwnerController(RegisterOwnerUseCase registerOwnerUseCase,
                           PasswordEncryption passwordEncryption,
                           LoadOwnerPort loadOwnerPort) {
        this.registerOwnerUseCase = registerOwnerUseCase;
        this.passwordEncryption = passwordEncryption;
        this.loadOwnerPort = loadOwnerPort;
    }

    @PostMapping
    public ResponseEntity<OwnerDTO> registerOwner(@RequestBody RegisterOwnerRequest request) {
        String passwordHash = passwordEncryption.encryptPassword(request.password());

        RegisterOwnerCommand command = new RegisterOwnerCommand(
                request.name(),
                request.email(),
                request.password(),
                passwordHash
        );

        Owner owner = registerOwnerUseCase.registerOwner(command);

        return ResponseEntity.ok(new OwnerDTO(owner.getId().id().toString(), owner.getName(), owner.getEmail()));
    }

    @GetMapping("/me")
    @PreAuthorize("hasAuthority('owner')")
    public ResponseEntity<OwnerDTO> getCurrentOwner(@AuthenticationPrincipal Jwt jwt) {
        String email = jwt.getClaimAsString("email");

        Owner owner = loadOwnerPort.loadByEmail(email)
                .orElseThrow(() -> new OwnerNotFoundException(email));

        return ResponseEntity.ok(new OwnerDTO(owner.getId().id().toString(), owner.getName(), owner.getEmail()));
    }
}
