package co.com.mercadolibre.usecase.recruitmutant;

import co.com.mercadolibre.model.mutantstat.gateway.MutantStatsRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;

@ExtendWith(MockitoExtension.class)
public class ValidateMutantUseCaseTest {
    @InjectMocks
    private ValidateMutantUseCase validateMutantUseCase;

    @Mock
    private MutantStatsRepository mutantStatsRepository;

    private final String[] dnaMutantHorizontal = {"TTTT","TTCA","GGTA","TGAG"};

    private final String[] dnaMutantVertical = {"TTGT","TTCA","TGTA","TGAG"};

    private final String[] dnaMutantDiagonal = {"ATGT","TACA","TGAA","TGAA"};

    private final String[] dnaError = {"ATGT"};


    private final Boolean isMutant = true;

    @Test
    public void isMutantDnaHorizontal(){
        Mockito.when(mutantStatsRepository.saveDna(any(), any())).thenReturn(Mono.just(true));

        Mono<Boolean> test = validateMutantUseCase.validateMutant(dnaMutantHorizontal);

        assertThat(test.block()).isEqualTo(isMutant);

    }

    @Test
    public void isMutantDnaVertical(){
        Mockito.when(mutantStatsRepository.saveDna(any(), any())).thenReturn(Mono.just(true));

        Mono<Boolean> test = validateMutantUseCase.validateMutant(dnaMutantVertical);

        assertThat(test.block()).isEqualTo(isMutant);

    }

    @Test
    public void isMutantDnaDiagonal(){
        Mockito.when(mutantStatsRepository.saveDna(any(), any())).thenReturn(Mono.just(true));

        Mono<Boolean> test = validateMutantUseCase.validateMutant(dnaMutantDiagonal);

        assertThat(test.block()).isEqualTo(isMutant);

    }

    @Test
    public void isMutantDnaError(){

        Mono<Boolean> test = validateMutantUseCase.validateMutant(dnaError);

        StepVerifier.create(test)
        .expectError();

    }



}