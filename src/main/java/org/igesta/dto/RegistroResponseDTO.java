package org.igesta.dto;

import java.time.LocalDateTime;
import java.util.Date;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Objeto de resposta com dados da condena")
public class RegistroResponseDTO {
    @Schema(description = "ID único do registro", example = "1234")
    private String id;

    @Schema(description = "Nome do gestor responsável pelo registro", example = "João Veigas Sobral")
    private String gestor;

    @Schema(description = "Nome do empresa responsável pelo registro", example = "João Veigas Sobral")
    private String empresa;

    @Schema(description = "Nome do turno do momento do registro", example = "Manhã")
    private Integer turno;

    @Schema(description = "Data o registro", example = "2025-10-15T00:00:00.000+00:00")
    private Date data;
}
