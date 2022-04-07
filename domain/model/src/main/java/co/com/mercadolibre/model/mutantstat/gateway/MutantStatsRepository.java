package co.com.mercadolibre.model.mutantstat.gateway;

import co.com.mercadolibre.model.mutantstat.MutantStat;
import reactor.core.publisher.Mono;

public interface MutantStatsRepository {
    Mono<Boolean> saveDna(Boolean isMutant, String[] dna);
    Mono<MutantStat> getStats();
}
