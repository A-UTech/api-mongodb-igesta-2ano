package org.igesta.controller;

import jakarta.validation.groups.Default;
import org.igesta.dto.RegistroRequestDTO;
import org.igesta.dto.RegistroResponseDTO;
import org.igesta.openapi.RegistroOpenApi;
import org.igesta.service.RegistroService;
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

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/igesta/registros")
public class RegistroController implements RegistroOpenApi {

    private final RegistroService registroService;

    public RegistroController(RegistroService registroService) {
        this.registroService = registroService;
    }

    @GetMapping("/selecionar")
    public ResponseEntity<List<RegistroResponseDTO>> buscarRegistros() {
        List<RegistroResponseDTO> registros = registroService.buscarRegistros();
        return ResponseEntity.ok(registros);
    }

    @GetMapping(value = "/selecionarPorId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegistroResponseDTO> buscarRegistroPorId(@PathVariable String id) {
        RegistroResponseDTO registro = registroService.buscarRegistroPorId(id);
        return ResponseEntity.ok(registro);
    }

    @GetMapping("/tipo-total-por-unidade/{unidade}")
    public ResponseEntity<Map<String, Object>> buscarTotaisPorUnidade(@PathVariable String unidade) {
        Map<String, Object> resultado = registroService.buscarTotaisPorUnidade(unidade);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/tipo-parcial-por-unidade/{unidade}")
    public ResponseEntity<Map<String, Object>> buscarParcialPorUnidade(@PathVariable String unidade) {
        Map<String, Object> resultado = registroService.buscarTotaisParcialPorUnidade(unidade);
        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/condenas-totais-mais-registradas/{unidade}")
    public ResponseEntity<List<Map<String, Object>>> condenasTotaisMaisRegistradasPorUnidade(@PathVariable String unidade) {
        return ResponseEntity.ok(registroService.buscarTop3CondenasTotalPorUnidade(unidade));
    }

    @GetMapping("/condenas-parciais-mais-registradas/{unidade}")
    public ResponseEntity<List<Map<String, Object>>> condenasParciaisMaisRegistradasPorUnidade(@PathVariable String unidade){
        return ResponseEntity.ok(registroService.buscarTop3CondenasParcialPorUnidade(unidade));
    }

    @GetMapping("/periodo/{inicio}/{fim}/{unidade}")
    public List<RegistroResponseDTO> buscarPorPeriodoEUnidade(
            @PathVariable("inicio") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataInicio,
            @PathVariable("fim") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date dataFim,
            @PathVariable("unidade") String unidade) {

        return registroService.buscarPorPeriodoEUnidade(dataInicio, dataFim, unidade);
    }

    @GetMapping("/total-condenas-registradas/{unidade}")
    public int contarTodasCondenasPorUnidade(@PathVariable String unidade) {
        return registroService.contarTodasCondenasPorUnidade(unidade);
    }

    @GetMapping("/comparar-mes-passado/{unidade}")
    public double compararComMesPassado(@PathVariable String unidade) {
        return registroService.buscarVariacaoCondenasMesPassado(unidade);
    }

    @PostMapping("/inserir")
    public ResponseEntity<Object> inserirRegistro(@RequestBody
                                                      @Validated({OnCreate.class, Default.class}) RegistroRequestDTO dto) {
        RegistroResponseDTO turno = registroService.inserirRegistro(dto);
        return ResponseEntity.ok(turno);
    }

    @DeleteMapping("/excluir/{id}")
    public ResponseEntity<RegistroResponseDTO> excluirRegistro(@PathVariable String id) {
        RegistroResponseDTO registro = registroService.removerRegistro(id);
        return ResponseEntity.ok(registro);
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Object> atualizarRegistro(@PathVariable String id,
                                                    @Validated({OnCreate.class, Default.class}) @RequestBody RegistroRequestDTO dto) {
        RegistroResponseDTO registro = registroService.atualizarRegistro(id, dto);
        return ResponseEntity.ok(registro);
    }

    @PatchMapping("/atualizarParcial/{id}")
    public ResponseEntity<?> atualizarRegistroParcial(@PathVariable String id, @RequestBody
    @Validated({OnPatch.class, Default.class}) RegistroRequestDTO dto) {
        RegistroResponseDTO turnoAtualizado = registroService.atualizarRegistroParcial(id, dto);
        return ResponseEntity.ok(turnoAtualizado);
    }
}
