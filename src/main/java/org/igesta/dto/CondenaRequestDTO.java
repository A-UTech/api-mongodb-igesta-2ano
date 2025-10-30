package org.igesta.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.igesta.validation.OnCreate;

@Getter
@Setter
@NoArgsConstructor
@Schema(description = "Objeto de requisição para criação ou atualização de uma condena")
public class CondenaRequestDTO {

    @Schema(description = "Identificador único da condena", example = "1")
    private Integer idCondena;

    @Schema(description = "Nome da condena", example = "Aero Saculite T")
    @NotNull(groups = OnCreate.class)
    @Pattern(regexp = ".*\\S.*\\S.*", message = "O nome da condena não pode estar em branco!")
    @Size(max = 100, message = "O nome da condena deve ter no máximo 100 caracteres")
    private String nome;

    @Schema(description = "Tipo da condena", example = "Total")
    @NotNull(groups = OnCreate.class)
    @Pattern(regexp = ".*\\S.*\\S.*", message = "O tipo da condena não pode estar em branco!")
    @Size(max = 100, message = "O tipo da condena deve ter no máximo 100 caracteres")
    private String tipo;

    @Schema(description = "Quantidade de itens condenados", example = "5")
    @NotNull(groups = OnCreate.class)
    @Positive(message = "A quantidade deve ser um número positivo")
    private int quantidade;
}
