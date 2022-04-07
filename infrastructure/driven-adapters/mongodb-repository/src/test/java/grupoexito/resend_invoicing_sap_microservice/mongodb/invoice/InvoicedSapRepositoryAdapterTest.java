package grupoexito.resend_invoicing_sap_microservice.mongodb.invoice;

import grupoexito.resend_invoicing_sap_microservice.domain.common.ex.BusinessException;
import grupoexito.resend_invoicing_sap_microservice.domain.invoice.invoiced.InvoicedSalesOrderResponse;
import grupoexito.resend_invoicing_sap_microservice.domain.invoice.invoiced.InvoicedSap;
import grupoexito.resend_invoicing_sap_microservice.mongodb.invoice.data.InvoicedSalesOrderResponseData;
import grupoexito.resend_invoicing_sap_microservice.mongodb.invoice.data.InvoicedSapData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class InvoicedSapRepositoryAdapterTest {

    private static final String TEST = "TEST";

    @InjectMocks
    private InvoicedSapRepositoryAdapter adapter;

    @Mock
    private InvoicedSapDataRepository repository;

    @Mock
    private DataMapperInvoicedSap dataMapper;

    @Mock
    private ReactiveMongoTemplate mongoTemplate;

    private InvoicedSap model;
    private InvoicedSapData data;

    @Before
    public void init() {

        model = InvoicedSap.builder()
                .shipFromLocationId(TEST)
                .orderId(TEST)
                .salesOrderResponse(InvoicedSalesOrderResponse.builder()
                        .factura(TEST)
                        .build())
                .build();

        data = InvoicedSapData.builder()
                .shipFromLocationId(TEST)
                .orderId(TEST)
                .salesOrderResponse(InvoicedSalesOrderResponseData.builder()
                        .factura(TEST)
                        .build())
                .build();
    }

    @Test
    public void findOrderInvoicedSap() {

        Mockito.when(mongoTemplate.findOne(any(), any())).thenReturn(Mono.just(data));
        Mockito.when(dataMapper.mapToEntity(any(InvoicedSapData.class))).thenReturn(Mono.just(model));

        Mono<InvoicedSap> actual = adapter.findOrderInvoicedSap(TEST, TEST);
        StepVerifier.create(actual)
                .expectNext(model)
                .verifyComplete();
    }

    @Test
    public void findOrderInvoicedSapOrderNull() {
        Mono<InvoicedSap> actual = adapter.findOrderInvoicedSap(null, TEST);
        StepVerifier.create(actual).verifyComplete();
    }

    @Test
    public void errorFindingOrderInvoicedSap() {

        Mockito.when(mongoTemplate.findOne(any(), any()))
                .thenReturn(Mono.error(new RuntimeException()));

        Mono<InvoicedSap> actual = adapter.findOrderInvoicedSap(TEST, TEST);
        StepVerifier.create(actual).expectError(BusinessException.class);
    }

}