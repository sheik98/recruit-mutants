package co.com.mercadolibre.mongodb.mutantstat;


import co.com.mercadolibre.model.mutantstat.MutantStat;
import co.com.mercadolibre.model.mutantstat.gateway.MutantStatsRepository;
import co.com.mercadolibre.mongodb.mutantstat.data.MutantStatData;
import co.com.mercadolibre.mongodb.mutantstat.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class MutantStatDataRepositoryAdapter extends AdapterOperations<MutantStat, MutantStatData, String, MutantStatDataRepository> implements MutantStatsRepository {


    private final ReactiveMongoTemplate mongoTemplate;

    private static final String IS_MUTANT= "isMutant";

    @Autowired
    public MutantStatDataRepositoryAdapter(MutantStatDataRepository repository,
                                           ObjectMapper mapper,
                                           ReactiveMongoTemplate mongoTemplate) {
        super(repository, mapper, d -> mapper.mapBuilder(d, MutantStat.MutantStatBuilder.class).build());
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Mono<Boolean> saveDna(Boolean isMutant, String[] dna) {
        return Mono.just(MutantStatData.builder()
                .dna(dna)
                .isMutant(isMutant)
                .build())
                .flatMap(mutantStatData -> repository.save(mutantStatData))
                .thenReturn(isMutant);

    }

    @Override
    public Mono<MutantStat> getStats() {
        return mongoTemplate.count(Query.query(Criteria.where(IS_MUTANT).is(true)), MutantStatData.class)
                .flatMap(mutants -> mongoTemplate.count(Query.query(Criteria.where(IS_MUTANT).is(false)), MutantStatData.class)
                        .map(humans -> MutantStat.builder()
                                .countMutantDna(Math.toIntExact(mutants))
                                .countHumanDna(Math.toIntExact(humans))
                                .ratio(humans==0 ? 0 : ((double) mutants/ (double) humans))
                                .build()));
    }
}
