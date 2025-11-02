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

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RegistroService {

    private final RegistroRepository registroRepository;
    private final ObjectMapper objectMapper;

    public RegistroService(RegistroRepository registroRepository, ObjectMapper objectMapper) {
        this.registroRepository = registroRepository;
        this.objectMapper = objectMapper;
    }

    public List<RegistroResponseDTO> buscarRegistros() {
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

    public List<RegistroResponseDTO> buscarPorPeriodoEUnidade(Date dataInicio, Date dataFim, String unidade) {
        List<Registro> registros = registroRepository.findByDataBetweenAndUnidade(dataInicio, dataFim, unidade);
        return registros.stream()
                .map(registro -> objectMapper.convertValue(registro, RegistroResponseDTO.class))
                .collect(Collectors.toList());
    }

    public int contarTodasCondenasPorUnidade(String unidade) {
        List<Map<String, Object>> resultado = registroRepository.contarTodasCondenasPorUnidade(unidade);
        if (resultado.isEmpty()) {
            return 0;
        }
        Object total = resultado.get(0).get("totalQuantidade");
        return total != null ? ((Number) total).intValue() : 0;
    }

    public int buscarTotaisPorUnidade(String unidade) {
        List<Map<String, Object>> resultado = registroRepository.somarCondenasTotalPorUnidade(unidade);
        if (resultado.isEmpty()) {
            return 0;
        }
        Object total = resultado.get(0).get("totalQuantidade");
        return total != null ? ((Number) total).intValue() : 0;
    }

    public int buscarTotaisParcialPorUnidade(String unidade) {
        List<Map<String, Object>> resultado = registroRepository.somarCondenasParcialPorUnidade(unidade);
        if (resultado.isEmpty()) {
            return 0;
        }
        Object total = resultado.get(0).get("totalQuantidade");
        return total != null ? ((Number) total).intValue() : 0;
    }

    public List<Map<String, Object>> buscarTop3CondenasTotalPorUnidade(String unidade) {
        return registroRepository.buscarTop3CondenasTotalPorUnidade(unidade);
    }

    public List<Map<String, Object>> buscarTop3CondenasParcialPorUnidade(String unidade) {
        return registroRepository.buscarTop3CondenasParcialPorUnidade(unidade);
    }

    public double buscarVariacaoCondenasMesPassado(String unidade) {
        LocalDate hoje = LocalDate.now();
        int mesAtual = hoje.getMonthValue();
        int mesAnterior = (mesAtual == 1) ? 12 : mesAtual - 1;

        List<Map<String, Object>> atualList = registroRepository.somarTodasCondenasPorMes(unidade, mesAtual);
        List<Map<String, Object>> anteriorList = registroRepository.somarTodasCondenasPorMes(unidade, mesAnterior);

        double atual = (atualList.isEmpty()) ? 0.0 : ((Number) atualList.get(0).get("totalQuantidade")).doubleValue();
        double anterior = (anteriorList.isEmpty()) ? 0.0 : ((Number) anteriorList.get(0).get("totalQuantidade")).doubleValue();

        if (anterior == 0.0) {
            return atual > 0.0 ? 100.0 : 0.0;
        }
        return ((atual - anterior) / anterior) * 100.0;
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

        if (atualizacoes.getGestor() != null) registro.setGestor(atualizacoes.getGestor());
        if (atualizacoes.getEmpresa() != null) registro.setEmpresa(atualizacoes.getEmpresa());
        if (atualizacoes.getIdTurno() != null) registro.setIdTurno(atualizacoes.getIdTurno());
        if (atualizacoes.getData() != null) registro.setData(atualizacoes.getData());
        if (atualizacoes.getLote() != null) registro.setLote(atualizacoes.getLote());
        if (atualizacoes.getUnidade() != null) registro.setUnidade(atualizacoes.getUnidade());
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
