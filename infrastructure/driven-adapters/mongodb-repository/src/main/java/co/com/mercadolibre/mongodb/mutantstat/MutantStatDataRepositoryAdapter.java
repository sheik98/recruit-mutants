package co.com.mercadolibre.mongodb.mutantstat;


import co.com.mercadolibre.model.mutantstat.MutantStat;
import co.com.mercadolibre.model.mutantstat.gateway.MutantStatsRepository;
import co.com.mercadolibre.mongodb.mutantstat.data.MutantStatData;
import co.com.mercadolibre.mongodb.mutantstat.helper.AdapterOperations;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public class MutantStatDataRepositoryAdapter extends AdapterOperations<MutantStat, MutantStatData, String, MutantStatDataRepository> implements MutantStatsRepository {


    private final ReactiveMongoTemplate mongoTemplate;

    @Autowired
    public MutantStatDataRepositoryAdapter(MutantStatDataRepository repository,
                                           ObjectMapper mapper,
                                           ReactiveMongoTemplate mongoTemplate) {
        super(repository, mapper, d -> mapper.mapBuilder(d, MutantStat.MutantStatBuilder.class).build());
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Mono<Boolean> saveStat(Boolean isMutant) {
        Integer mutant = isMutant ? 1 : 0;
        Integer human = !isMutant ? 1 : 0;
        return mongoTemplate.findById(Query.query(Criteria.where("_id").is("MutantStatsId")), MutantStatData.class)
                .flatMap(mutantStatData ->
                        mongoTemplate.updateFirst(
                                Query.query(Criteria.where("_id").is("MutantStatsId")),
                                Update.update("ratio", getRatio(mutant, human, mutantStatData))
                                        .inc("countMutantDna", mutant).inc("countHumanDna", human),
                                MutantStatData.class))
                .thenReturn(isMutant);

    }

    private int getRatio(Integer mutant, Integer human, MutantStatData mutantStatData) {
        return mutantStatData.getCountHumanDna() == 0 && human == 0 ? 0 : (mutantStatData.getCountMutantDna() + mutant) / (mutantStatData.getCountHumanDna() + human);
    }

    @Override
    public Mono<MutantStat> getStats() {
        return null;
    }
}
