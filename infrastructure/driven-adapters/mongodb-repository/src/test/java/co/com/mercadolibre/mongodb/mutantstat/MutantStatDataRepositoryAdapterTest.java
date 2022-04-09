package co.com.mercadolibre.mongodb.mutantstat;

import co.com.mercadolibre.model.mutantstat.MutantStat;
import co.com.mercadolibre.model.mutantstat.gateway.MutantStatsRepository;
import co.com.mercadolibre.mongodb.mutantstat.data.MutantStatData;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class MutantStatDataRepositoryAdapterTest {
    @InjectMocks
    private MutantStatDataRepositoryAdapter mutantStatDataRepositoryAdapter;

    @Mock
    private MutantStatDataRepository mutantStatDataRepository;

    @Mock
    private ReactiveMongoTemplate mongoTemplate;

    private final Boolean isMutant = true;

    private final String[] dnaMutantHorizontal = {"TTTT","TTCA","GGTA","TGAG"};

    private final MutantStat mutantStat = MutantStat.builder()
            .countMutantDna(1)
            .countHumanDna(1)
            .ratio(1D)
            .build();

    @Test
    public void getStatsTest(){
        Mockito.when(mongoTemplate.count(any(), (Class<?>) any())).thenReturn(Mono.just(1L));

        Mono<MutantStat> test = mutantStatDataRepositoryAdapter.getStats();

        assertThat(test.block()).isEqualTo(mutantStat);
    }

    @Test
    public void saveDnaTest(){
        Mockito.when(mutantStatDataRepository.save(any())).thenReturn(Mono.just(MutantStatData.builder().build()));

        Mono<Boolean> test = mutantStatDataRepositoryAdapter.saveDna(isMutant, dnaMutantHorizontal);

        assertThat(test.block()).isEqualTo(isMutant);

    }
}