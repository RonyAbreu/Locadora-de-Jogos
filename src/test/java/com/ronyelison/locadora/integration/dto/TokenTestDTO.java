package com.ronyelison.locadora.integration.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@XmlRootElement
public class TokenTestDTO {
    private String token;
    private String refreshToken;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm", timezone = "BRT")
    private Instant criadoEm;
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd/MM/yyyy HH:mm", timezone = "BRT")
    private Instant validoAte;
    private Boolean autenticado;
}
