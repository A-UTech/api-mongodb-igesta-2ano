package org.igesta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Objeto de resposta com dados do registro")
public class RegistroResponseDTO {

    @Schema(description = "Nome do gestor responsável pelo registro", example = "Paulo Vaz")
    private String gestor;

    @Schema(description = "Nome da empresa responsável pelo registro", example = "Khiata")
    private String empresa;

    @Schema(description = "Identificador do turno (pode ser nulo)", example = "1")
    private Integer idTurno;

    @Schema(description = "Data do registro", example = "2025-10-15T00:00:00.000+00:00")
    private Date data;

    @Schema(description = "Lote da condena registrada", example = "???")
    private String lote;

    @Schema(description = "Unidade responsável pelo registro", example = "Khiata Maranhão")
    private String unidade;

    @Schema(description = "Lista de condenas associadas ao registro")
    private List<CondenaResponseDTO> condenas;
}
