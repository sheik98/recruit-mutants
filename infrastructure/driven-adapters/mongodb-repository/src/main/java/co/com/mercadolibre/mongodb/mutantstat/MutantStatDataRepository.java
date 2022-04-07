package co.com.mercadolibre.mongodb.mutantstat;

import co.com.mercadolibre.mongodb.mutantstat.data.MutantStatData;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import reactor.core.publisher.Mono;

public interface MutantStatDataRepository extends ReactiveMongoRepository<MutantStatData, String>,
        ReactiveQueryByExampleExecutor<MutantStatData> {

}
