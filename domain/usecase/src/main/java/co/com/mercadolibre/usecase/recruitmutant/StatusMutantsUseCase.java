package co.com.mercadolibre.usecase.recruitmutant;

import co.com.mercadolibre.model.mutantstat.MutantStat;
import co.com.mercadolibre.model.mutantstat.gateway.MutantStatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

@Log
@RequiredArgsConstructor
public class StatusMutantsUseCase {
    private final MutantStatsRepository mutantStatsRepository;


    public Mono<MutantStat> getStats(){
        return mutantStatsRepository.getStats()
                .onErrorResume(error -> Mono.error(new RuntimeException("Fallo al obtener stats de los mutantes")));

    }
}
