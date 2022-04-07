package co.com.mercadolibre.model.mutantstat;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class MutantStat {
    private final Integer countMutantDna;
    private final Integer countHumanDna;
    private final Double ratio;
}
