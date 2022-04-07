package co.com.mercadolibre.mongodb.mutantstat.data;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("MutantStatData")
@Builder(toBuilder = true)
public class MutantStatData {
    @Id
    private String id;
    private Integer countMutantDna;
    private Integer countHumanDna;
    private Double ratio;
}
