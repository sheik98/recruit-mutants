package co.com.mercadolibre.usecase.recruitmutant;

import co.com.mercadolibre.model.mutantstat.gateway.MutantStatsRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;

@Log
@RequiredArgsConstructor
public class ValidateMutantUseCase {
    private final MutantStatsRepository mutantStatsRepository;

    public Mono<Boolean> validateMutant(String[] dna) {
        return Mono.just(dna)
                .flatMap(this::validateIn)
                .flatMap(this::isMutant)
                .flatMap(this::saveStats)
                .onErrorResume(error -> Mono.error(new IOException("Descripcion del error: " + error.getMessage())));
    }

    private Mono<Boolean> saveStats(Boolean isMutant) {
        return mutantStatsRepository.saveStat(isMutant);
    }

    private Mono<String[]> validateIn(String[] dna1) {
        return Mono.just(Arrays.stream(dna1)
                .allMatch(row ->
                        dna1.length >= 4 && dna1.length == row.length() && row.matches("[ATCG]*")))
                .flatMap(aBoolean -> aBoolean.equals(Boolean.TRUE) ?
                        Mono.just(dna1) : Mono.error(new IOException("Error en el dna")));
    }

    private Mono<Boolean> isMutant(String[] dna) {

        return Mono.just(validateHorizontal(dna) || validateVertical(dna) || validateDiagonal(dna));
    }

    private boolean validateDiagonal(String[] dna) {
        boolean mutant = false;

        //main diagonal
        for (int i = 0; i < dna.length - 3; i++) {
            for (int j = 0; j < dna.length - 3; j++) {
                if (dna[i].charAt(j) == dna[i + 1].charAt(j + 1) && dna[i].charAt(j) == dna[i + 2].charAt(j + 2) && dna[i].charAt(j) == dna[i + 3].charAt(j + 3)) {
                    mutant = true;
                    break;
                }

            }
            if (mutant) break;
        }

        //secondary diagonal
        if (!mutant) {
            for (int i = dna.length - 1; i > 2; i--) {
                for (int j = 0; j < dna.length - 3; j++) {
                    if (dna[i].charAt(j) == dna[i - 1].charAt(j + 1) && dna[i].charAt(j) == dna[i - 2].charAt(j + 2) && dna[i].charAt(j) == dna[i - 3].charAt(j + 3)) {
                        mutant = true;
                        break;
                    }
                }
                if (mutant) break;
            }
        }

        return mutant;
    }


    private boolean validateHorizontal(String[] strings) {
        return Arrays.stream(strings).anyMatch(row -> {
            for (int i = 0; i < row.length() - 3; i++) {
                if (row.charAt(i) == row.charAt(i + 1) && row.charAt(i) == row.charAt(i + 2) && row.charAt(i) == row.charAt(i + 3)) {
                    return true;
                }
            }
            return false;
        });
    }

    private boolean validateVertical(String[] dna) {
        boolean mutant = false;
        for (int i = 0; i < dna.length; i++) {
            for (int j = 0; j < dna.length - 3; j++) {
                if (dna[j].charAt(i) == dna[j + 1].charAt(i)
                        && dna[j].charAt(i) == dna[j + 2].charAt(i)
                        && dna[j].charAt(i) == dna[j + 3].charAt(i)) {

                    mutant = true;
                    break;
                }
                if (mutant) break;
            }
        }
        return mutant;
    }


}
