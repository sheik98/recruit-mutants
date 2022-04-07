package co.com.mercadolibre.model.mutantstat.gateway;

import co.com.mercadolibre.model.mutantstat.MutantStat;
import reactor.core.publisher.Mono;

public interface MutantStatsRepository {
    Mono<Boolean> saveStat(Boolean isMutant);
    Mono<MutantStat> getStats();
}
