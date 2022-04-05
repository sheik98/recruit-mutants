package co.com.mercadolibre.api;

import co.com.mercadolibre.api.dto.RequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;


@CrossOrigin
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RouterRest {

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/mutant/")
    public Mono<HttpStatus> isMutant(@RequestBody RequestDto requestDto){
        return Mono.just(HttpStatus.OK);
    }

    @GetMapping(path = "/stats")
    public Mono<ServerResponse> getStatsMutants(){
        return ServerResponse.status(FORBIDDEN).build();
    }
}
