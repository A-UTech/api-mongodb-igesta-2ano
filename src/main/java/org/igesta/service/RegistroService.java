package org.igesta.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.igesta.dto.RegistroRequestDTO;
import org.igesta.dto.RegistroResponseDTO;
import org.igesta.model.Registro;
import org.igesta.repository.RegistroRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RegistroService {

    private final RegistroRepository registroRepository;
    private final ObjectMapper objectMapper;

    public RegistroService(RegistroRepository registroRepository, ObjectMapper objectMapper) {
        this.registroRepository = registroRepository;
        this.objectMapper = objectMapper;
    }

    public List<RegistroResponseDTO> listarTodos() {
        List<Registro> registros = registroRepository.findAll();
        return registros.stream()
                .map(registro -> objectMapper.convertValue(registro, RegistroResponseDTO.class))
                .collect(Collectors.toList());
    }

    public RegistroResponseDTO buscarRegistroPorId(String id) {
        Registro registro = registroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado"));

        return objectMapper.convertValue(registro, RegistroResponseDTO.class);
    }

    @Transactional
    public RegistroResponseDTO inserirRegistro(RegistroRequestDTO dto) {
        Registro registro = objectMapper.convertValue(dto, Registro.class);
        Registro salvo = registroRepository.save(registro);
        return objectMapper.convertValue(salvo, RegistroResponseDTO.class);
    }

    @Transactional
    public RegistroResponseDTO atualizarRegistro(String id, RegistroRequestDTO dto) {
        if (!registroRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado para atualizar");
        }

        Registro atualizado = objectMapper.convertValue(dto, Registro.class);
        atualizado.setId(id);

        Registro salvo = registroRepository.save(atualizado);
        return objectMapper.convertValue(salvo, RegistroResponseDTO.class);
    }

    @Transactional
    public RegistroResponseDTO atualizarRegistroParcial(String id, RegistroRequestDTO dto) {
        Registro registro = registroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado"));

        Registro atualizacoes = objectMapper.convertValue(dto, Registro.class);

        if (atualizacoes.getGestor() != null) {
            registro.setGestor(atualizacoes.getGestor());
        }

        if (atualizacoes.getEmpresa() != null) {
            registro.setEmpresa(atualizacoes.getEmpresa());
        }

        if (atualizacoes.getIdTurno() != null) {
            registro.setIdTurno(atualizacoes.getIdTurno());
        }

        if (atualizacoes.getData() != null) {
            registro.setData(atualizacoes.getData());
        }

        if (atualizacoes.getLote() != null) {
            registro.setLote(atualizacoes.getLote());
        }

        if (atualizacoes.getUnidade() != null) {
            registro.setUnidade(atualizacoes.getUnidade());
        }

        if (atualizacoes.getCondenas() != null && !atualizacoes.getCondenas().isEmpty()) {
            registro.setCondenas(atualizacoes.getCondenas());
        }

        Registro salvo = registroRepository.save(registro);

        return objectMapper.convertValue(salvo, RegistroResponseDTO.class);
    }


    @Transactional
    public RegistroResponseDTO removerRegistro(String id) {
        Registro existente = registroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado"));

        registroRepository.deleteById(id);
        return objectMapper.convertValue(existente, RegistroResponseDTO.class);
    }
}
