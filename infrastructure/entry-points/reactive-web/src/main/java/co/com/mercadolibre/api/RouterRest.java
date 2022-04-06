package co.com.mercadolibre.api;

import co.com.mercadolibre.api.dto.RequestDto;
import co.com.mercadolibre.usecase.recruitMutant.ValidateMutantUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.FORBIDDEN;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RouterRest {
    private final ValidateMutantUseCase validateMutantUseCase;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/mutant/")
    public Mono<ResponseEntity<Object>> isMutant(@RequestBody RequestDto requestDto) {
        return validateMutantUseCase.validateMutant(requestDto.getDna())
                .map(aBoolean -> aBoolean.equals(Boolean.TRUE) ? ResponseEntity.ok().build()
                         : ResponseEntity.status(FORBIDDEN).build())
                .onErrorResume(throwable -> Mono.just(ResponseEntity.badRequest().build()));
    }

    @GetMapping(path = "/stats")
    public Mono<ResponseEntity<Object>> getStatsMutants() {
        return Mono.just("Stats")
                .map(s -> ResponseEntity.ok().body((Object) s));
    }
}
