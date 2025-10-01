package org.igesta.dto;

import java.time.LocalDateTime;
import java.util.Date;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RegistroResponseDTO {
    private String id;
    private String gestor;
    private String empresa;
    private Integer turno;
    private Date data;
}
