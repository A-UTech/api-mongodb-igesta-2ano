package org.igesta.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class Condena {
    @Id
    private Integer idCondena;
    private String nome;
    private String tipo;
    private int quantidade;
}