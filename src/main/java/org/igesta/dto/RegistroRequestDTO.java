package org.igesta.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.igesta.validation.OnCreate;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Objeto para criação/atualização de registro de condenas")
public class RegistroRequestDTO {

    @Schema(description = "Nome do gestor responsável pelo registro", example = "João Veigas Sobral")
    @NotNull(groups = OnCreate.class)
    @Pattern(regexp = ".*\\S.*\\S.*", message = "O nome não pode estar em branco!")
    private String gestor;

    @Schema(description = "Nome do empresa responsável pelo registro", example = "João Veigas Sobral")
    @NotNull(groups = OnCreate.class)
    @Pattern(regexp = ".*\\S.*\\S.*", message = "O nome não pode estar em branco!")
    private String empresa;

    @Schema(description = "Nome do turno do momento do registro", example = "Manhã")
    private Integer turno;

    @Schema(description = "Data o registro", example = "2025-10-15T00:00:00.000+00:00")
    private Date data;
}