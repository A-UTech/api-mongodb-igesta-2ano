package org.igesta.model;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "registros")
public class Registro {

    @Id
    private String id;
    private List<Condena> condenas;
    private Date data;
    private String empresa;
    private String gestor;
    @Field("id_turno")
    private Integer idTurno;
    private String lote;
    private String unidade;


}
