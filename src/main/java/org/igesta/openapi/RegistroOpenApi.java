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
import org.igesta.validation.OnCreate;
import org.igesta.validation.OnPatch;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Tag(name = "Registros", description = "API para gerenciamento do registro de condenas")
public interface RegistroOpenApi {

    @Operation(summary = "Buscar todos os registros",
            description = "Retorna uma lista com todos os registros cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RegistroResponseDTO.class)))
    ResponseEntity<List<RegistroResponseDTO>> buscarRegistros();

    @Operation(summary = "Buscar registro por ID",
            description = "Retorna um registro específico pelo seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistroResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    ResponseEntity<RegistroResponseDTO> buscarRegistroPorId(
            @Parameter(description = "ID do registro a ser buscado") @PathVariable String id);

    @Operation(summary = "Buscar total de condenas por unidade",
            description = "Retorna o número total de condenas (tipo total) para uma unidade específica")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Integer.class)))
    ResponseEntity<Integer> buscarTotaisPorUnidade(
            @Parameter(description = "Nome da unidade") @PathVariable String unidade);

    @Operation(summary = "Buscar condenas parciais por unidade",
            description = "Retorna o número de condenas parciais para uma unidade específica")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Integer.class)))
    ResponseEntity<Integer> buscarParcialPorUnidade(
            @Parameter(description = "Nome da unidade") @PathVariable String unidade);

    @Operation(summary = "Top 3 condenas totais mais registradas por unidade",
            description = "Retorna as três condenas totais mais registradas em uma unidade")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso",
            content = @Content(mediaType = "application/json"))
    ResponseEntity<List<Map<String, Object>>> condenasTotaisMaisRegistradasPorUnidade(
            @Parameter(description = "Nome da unidade") @PathVariable String unidade);

    @Operation(summary = "Top 3 condenas parciais mais registradas por unidade",
            description = "Retorna as três condenas parciais mais registradas em uma unidade")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso",
            content = @Content(mediaType = "application/json"))
    ResponseEntity<List<Map<String, Object>>> condenasParciaisMaisRegistradasPorUnidade(
            @Parameter(description = "Nome da unidade") @PathVariable String unidade);

    @Operation(summary = "Buscar registros por período e unidade",
            description = "Retorna os registros cadastrados em um intervalo de datas para uma unidade específica")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RegistroResponseDTO.class)))
    List<RegistroResponseDTO> buscarPorPeriodoEUnidade(
            @Parameter(description = "Data inicial no formato yyyy-MM-dd")
            @PathVariable("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataInicio,
            @Parameter(description = "Data final no formato yyyy-MM-dd")
            @PathVariable("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataFim,
            @Parameter(description = "Nome da unidade") @PathVariable("unidade") String unidade);

    @Operation(summary = "Contar todas as condenas registradas por unidade",
            description = "Retorna a quantidade total de condenas (independente do tipo) registradas por unidade")
    @ApiResponse(responseCode = "200", description = "Contagem realizada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Integer.class)))
    int contarTodasCondenasPorUnidade(
            @Parameter(description = "Nome da unidade") @PathVariable String unidade);

    @Operation(summary = "Comparar condenas com o mês passado",
            description = "Retorna a variação percentual de condenas em relação ao mês anterior")
    @ApiResponse(responseCode = "200", description = "Comparação realizada com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = Double.class)))
    double compararComMesPassado(
            @Parameter(description = "Nome da unidade") @PathVariable String unidade);

    @Operation(summary = "Inserir novo registro",
            description = "Cria um novo registro de condena no sistema")
    @ApiResponse(responseCode = "200", description = "Registro criado com sucesso",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = RegistroResponseDTO.class)))
    ResponseEntity<Object> inserirRegistro(
            @RequestBody @Validated({OnCreate.class, Default.class}) RegistroRequestDTO dto);

    @Operation(summary = "Excluir registro",
            description = "Exclui um registro existente pelo seu ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro excluído com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistroResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado")
    })
    ResponseEntity<RegistroResponseDTO> excluirRegistro(
            @Parameter(description = "ID do registro a ser excluído") @PathVariable String id);

    @Operation(summary = "Atualizar registro",
            description = "Atualiza completamente um registro existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro atualizado com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistroResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<Object> atualizarRegistro(
            @Parameter(description = "ID do registro a ser atualizado") @PathVariable String id,
            @RequestBody @Validated({OnCreate.class, Default.class}) RegistroRequestDTO dto);

    @Operation(summary = "Atualizar parcialmente um registro",
            description = "Atualiza apenas os campos informados de um registro existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Registro atualizado parcialmente com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = RegistroResponseDTO.class))),
            @ApiResponse(responseCode = "404", description = "Registro não encontrado"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    ResponseEntity<?> atualizarRegistroParcial(
            @Parameter(description = "ID do registro a ser atualizado parcialmente") @PathVariable String id,
            @RequestBody @Validated({OnPatch.class, Default.class}) RegistroRequestDTO dto);
}
