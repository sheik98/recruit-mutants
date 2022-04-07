package co.com.mercadolibre.mongodb.mutantstat.config;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MongoDBSecret {
private final String uri;
}
