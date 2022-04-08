package co.com.mercadolibre.api;

import co.com.mercadolibre.api.dto.RequestDto;
import co.com.mercadolibre.usecase.recruitmutant.StatusMutantsUseCase;
import co.com.mercadolibre.usecase.recruitmutant.ValidateMutantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.FORBIDDEN;


@CrossOrigin
@RestController
@RequiredArgsConstructor
public class RouterRest {
    private final ValidateMutantUseCase validateMutantUseCase;
    private final StatusMutantsUseCase statusMutantsUseCase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/mutant/")
    public Mono<ResponseEntity<String>> isMutant(@RequestBody RequestDto requestDto) {
        return validateMutantUseCase.validateMutant(requestDto.getDna())
                .map(aBoolean -> aBoolean.equals(Boolean.TRUE) ? ResponseEntity.ok().body("Mutante!!!")
                         : ResponseEntity.status(FORBIDDEN).body("Humano"))
                .onErrorResume(error -> Mono.just(ResponseEntity.badRequest().body(error.getMessage())));
    }

    @GetMapping(path = "/stats")
    public Mono<ResponseEntity<Object>> getStatsMutants() {
        return statusMutantsUseCase.getStats()
                .map(mutantStat -> ResponseEntity.ok().body((Object) mutantStat))
                .onErrorResume(error -> Mono.just(ResponseEntity.internalServerError().body(error.getMessage())));
    }
}
