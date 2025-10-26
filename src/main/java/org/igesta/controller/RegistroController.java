package org.igesta.controller;

import jakarta.validation.groups.Default;
import org.igesta.dto.RegistroRequestDTO;
import org.igesta.dto.RegistroResponseDTO;
import org.igesta.model.Registro;
import org.igesta.openapi.RegistroOpenApi;
import org.igesta.service.RegistroService;
import org.igesta.validation.OnCreate;
import org.igesta.validation.OnPatch;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/igesta/registros")
public class RegistroController implements RegistroOpenApi {


    private final RegistroService registroService;

    public RegistroController(RegistroService registroService) {
            this.registroService = registroService;
        }

    @GetMapping("/selecionar")
    public ResponseEntity<List<RegistroResponseDTO>> listarRegistros() {
        List<RegistroResponseDTO> registros = registroService.listarTodos();
        return ResponseEntity.ok(registros);
    }


    @GetMapping(value = "/selecionarPorId/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<RegistroResponseDTO> buscarRegistroPorId(@PathVariable String id) {
            RegistroResponseDTO registro = registroService.buscarRegistroPorId(id);
            return ResponseEntity.ok(registro);
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
