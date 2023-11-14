package com.ronyelison.locadora.dto.usuario;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TokenDTO {
    private String token;
    private String refreshToken;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm", timezone = "BRT")
    private Instant criadoEm;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm", timezone = "BRT")
    private Instant validoAte;
    private Boolean autenticado;
}
