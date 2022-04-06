package co.com.mercadolibre.usecase.recruitMutant;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

@Log
@RequiredArgsConstructor
public class ValidateMutantUseCase {

    public Mono<Boolean> validateMutant(String[] dna){
        return Mono.just(dna)
                .flatMap(this::validateIn)
                .flatMap(this::isMutant)
                .onErrorResume(error -> Mono.error(new IOException("Errores")));
    }

    private Mono<String[]> validateIn(String[] dna1) {
        return Mono.just(Arrays.stream(dna1)
                .allMatch(row ->
                        dna1.length > 1 && dna1.length == row.length() && row.matches("[ATCG]*")))
                .flatMap(aBoolean -> aBoolean.equals(Boolean.TRUE)  ?
                        Mono.just(dna1) : Mono.error(new IOException("Error en el dna")));
    }

    private Mono<Boolean> isMutant(String[] dna) {
        boolean isMutant = false;

        return Mono.just(validateHorizontal(dna));
               // && validateVertical(dna) && validateDiagonal(dna));
    }

    private boolean validateHorizontal(String[] strings) {
        return Arrays.stream(strings).anyMatch(row -> {
            for (int i = 0; i < row.length() - 4; i++) {
                return row.charAt(i) == row.charAt(i + 1) && row.charAt(i) == row.charAt(i + 2) && row.charAt(i) == row.charAt(i + 3);
            }
            return false;
        });
    }

    private boolean validateDiagonal(String[] dna) {
        return false;
    }


}
