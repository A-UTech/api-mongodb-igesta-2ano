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

@Service
public class RegistroService {

    private final RegistroRepository registroRepository;
    private final ObjectMapper objectMapper;

    public RegistroService(RegistroRepository registroRepository, ObjectMapper objectMapper) {
        this.registroRepository = registroRepository;
        this.objectMapper = objectMapper;
    }

    public List<Registro> listarTodos() {
        List<Registro> registros = registroRepository.findAll();
        System.out.println(">>>> QTD REGISTROS: " + registros.size());
        return registros;
    }

    public RegistroResponseDTO buscarRegistroPorId(String id) {
        return objectMapper.convertValue(registroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado")), RegistroResponseDTO.class);
    }

    @Transactional
    public RegistroResponseDTO inserirRegistro(RegistroRequestDTO dto){
        Registro registro = objectMapper.convertValue(dto, Registro.class);
        return objectMapper.convertValue(registroRepository.save(registro), RegistroResponseDTO.class);
    }

    @Transactional
    public RegistroResponseDTO atualizarRegistro(String id, RegistroRequestDTO dto) {
        if (!registroRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado para atualizar");
        }
        Registro atualizado =objectMapper.convertValue(dto, Registro.class);
        atualizado.setId(id);
        return objectMapper.convertValue(registroRepository.save(atualizado), RegistroResponseDTO.class);
    }

    @Transactional
    public RegistroResponseDTO atualizarRegistroParcial(String id, RegistroRequestDTO dto) {
        if (!registroRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado para atualizar");
        }
        Registro registro = registroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Registro não encontrado para atualizar"));

        Registro atualizacoes = objectMapper.convertValue(dto, Registro.class);

        if (atualizacoes.getGestor() != null) {
            String gestor = atualizacoes.getGestor();
            registro.setGestor(gestor);
        }

        if (atualizacoes.getTurno() != 0.0) {
            Integer turno = atualizacoes.getTurno();
            registro.setTurno(turno);
        }

        if (atualizacoes.getEmpresa() != null) {
            String empresa =  atualizacoes.getEmpresa();
            registro.setEmpresa(empresa);
        }

        return objectMapper.convertValue(registroRepository.save(registro), RegistroResponseDTO.class);
    }

    @Transactional
    public RegistroResponseDTO removerRegistro(String id){
        RegistroResponseDTO existente = buscarRegistroPorId(id);
        registroRepository.deleteById(id);
        return existente;
    }
}
