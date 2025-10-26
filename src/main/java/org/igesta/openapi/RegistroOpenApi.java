package org.igesta.openapi;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.groups.Default;
import org.igesta.dto.RegistroRequestDTO;
import org.igesta.dto.RegistroResponseDTO;
import org.igesta.model.Registro;
import org.igesta.validation.OnCreate;
import org.igesta.validation.OnPatch;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Registros", description = "API para gerenciamento do registro de condenas")
public interface RegistroOpenApi {

    @Operation(summary = "Buscar todos os registros",
            description = "Retorna uma lista com todos os registros cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
                    content = @Content(mediaType = "application/json"))
    })
    public ResponseEntity<List<RegistroResponseDTO>> listarRegistros();

    @Operation(summary = "Busca registro por ID",
            description = "Retorna um registro pelo seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistroResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    public ResponseEntity<RegistroResponseDTO> buscarRegistroPorId(@Parameter(description = "ID da condena a ser buscada") String id);

    @Operation(summary = "Insere um novo registro",
            description = "Cria um novo registro no sistema")
    @ApiResponse(responseCode = "200", description = "Registro criado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RegistroResponseDTO.class)))
    public ResponseEntity<Object> inserirRegistro(RegistroRequestDTO dto);

    @Operation(summary = "Excluir registro",
            description = "Exclui um registro pelo seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro excluido com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistroResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    public ResponseEntity<RegistroResponseDTO> excluirRegistro(@Parameter(description = "ID do registro a ser excluido") String id);

    @Operation(
            summary = "Atualizar registro",
            description = "Atualiza todos os dados de um registro existente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Registro atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistroResponseDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<Object> atualizarRegistro(@Parameter(description = "ID do registro a ser atulizado") String id, RegistroRequestDTO dto);

    @Operation(
            summary = "Atualização parcial de registro",
            description = "Atualiza apenas os campos informados de um registro existente"
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Reegistro atualizado parcialmente com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistroResponseDTO.class))
            ),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    public ResponseEntity<?> atualizarRegistroParcial(@Parameter(description = "ID da condena a ser atulizada parcialmente") String id, RegistroRequestDTO dto);
}
