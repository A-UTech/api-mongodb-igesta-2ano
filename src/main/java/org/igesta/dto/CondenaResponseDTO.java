package org.igesta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Objeto de resposta com informações de uma condena")
public class CondenaResponseDTO {

    @Schema(description = "Nome da condena", example = "Aero Saculite T")
    private String nome;

    @Schema(description = "Tipo da condena", example = "Parcial")
    private String tipo;

    @Schema(description = "Quantidade de itens condenados", example = "5")
    private Integer quantidade;
}
