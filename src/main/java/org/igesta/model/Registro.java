
package org.igesta.model;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Document(collection = "registros")
public class Registro {

    @Id
    private String id;
    private List<Condena> condenas;
    private String gestor;
    private String empresa;
    private Integer turno;
    private Date data;

}
