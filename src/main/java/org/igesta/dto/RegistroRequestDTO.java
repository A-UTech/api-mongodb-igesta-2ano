package org.igesta.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.igesta.validation.OnCreate;
import org.igesta.validation.OnPatch;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Objeto de requisição para criação ou atualização de um registro")
public class RegistroRequestDTO {

    @Schema(description = "Nome do gestor responsável pelo registro", example = "Paulo Vaz")
    @NotNull(groups = OnCreate.class)
    @Pattern(regexp = ".*\\S.*\\S.*", message = "O nome do gestor não pode estar em branco")
    private String gestor;

    @Schema(description = "Nome da empresa responsável pelo registro", example = "Khiata")
    @NotNull(groups = OnCreate.class)
    @Pattern(regexp = ".*\\S.*\\S.*", message = "A empresa não pode estar em branco")
    private String empresa;

    @Schema(description = "Identificador do turno (pode ser nulo)", example = "1")
    private Integer idTurno;

    @Schema(description = "Data do registro", example = "2025-10-15T00:00:00.000+00:00")
    @NotNull(groups = OnCreate.class)
    private Date data;

    @Schema(description = "Lote da condena registrada", example = "???")
    @NotNull(groups = OnCreate.class)
    @Pattern(regexp = ".*\\S.*\\S.*", message = "O lote não pode estar em branco")
    private String lote;

    @Schema(description = "Unidade responsável pelo registro", example = "Khiata Maranhão")
    @NotNull(groups = OnCreate.class)
    @Pattern(regexp = ".*\\S.*\\S.*", message = "A unidade não pode estar em branco")
    private String unidade;

    @Schema(description = "Lista de condenas associadas ao registro")
    @NotNull(groups = OnCreate.class)
    private List<CondenaRequestDTO> condenas;
}
