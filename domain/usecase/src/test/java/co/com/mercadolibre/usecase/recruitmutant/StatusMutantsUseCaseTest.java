package co.com.mercadolibre.usecase.recruitmutant;

import co.com.mercadolibre.model.mutantstat.MutantStat;
import co.com.mercadolibre.model.mutantstat.gateway.MutantStatsRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StatusMutantsUseCaseTest {
    @InjectMocks
    private StatusMutantsUseCase statusMutantsUseCase;

    @Mock
    private MutantStatsRepository mutantStatsRepository;

    private final MutantStat mutantStat = MutantStat.builder()
            .countMutantDna(15)
            .countHumanDna(10)
            .ratio(15D/10D)
            .build();

    @Test
    public void getMutantStatsOk(){
        Mockito.when(mutantStatsRepository.getStats()).thenReturn(Mono.just(mutantStat));

        Mono<MutantStat> test = statusMutantsUseCase.getStats();

        assertThat(test.block()).isEqualTo(mutantStat);

    }

    @Test
    public void getMutantStatError(){
        Mockito.when(mutantStatsRepository.getStats()).thenReturn(Mono.error(new RuntimeException()));

        Mono<MutantStat> test = statusMutantsUseCase.getStats();

        StepVerifier.create(test)
                .expectError();
    }
}