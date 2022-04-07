package grupoexito.resend_invoicing_sap_microservice.mongodb.invoice;

import grupoexito.resend_invoicing_sap_microservice.domain.invoice.invoiced.InvoicedSalesOrderResponse;
import grupoexito.resend_invoicing_sap_microservice.domain.invoice.invoiced.InvoicedSap;
import grupoexito.resend_invoicing_sap_microservice.mongodb.invoice.data.InvoicedSalesOrderResponseData;
import grupoexito.resend_invoicing_sap_microservice.mongodb.invoice.data.InvoicedSapData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataMapperInvoicedSapTest {

    @InjectMocks
    private DataMapperInvoicedSap dataMapper;

    @Mock
    private ObjectMapper mapper;

    private static final String TEST = "TEST";

    private InvoicedSap model;
    private InvoicedSapData data;

    @Before
    public void init() {

        data = InvoicedSapData.builder()
                .shipFromLocationId(TEST)
                .orderId(TEST)
                .salesOrderResponse(InvoicedSalesOrderResponseData.builder()
                        .factura(TEST)
                        .build())
                .build();

        model = InvoicedSap.builder()
                .shipFromLocationId(TEST)
                .orderId(TEST)
                .salesOrderResponse(InvoicedSalesOrderResponse.builder()
                        .factura(TEST)
                        .build())
                .build();
    }

    @Test
    public void mapToEntityRequest() {
        when(mapper.mapBuilder(data, InvoicedSap.InvoicedSapBuilder.class))
                .thenReturn(model.toBuilder());
        when(mapper.mapBuilder(data.getSalesOrderResponse(), InvoicedSalesOrderResponse.InvoicedSalesOrderResponseBuilder.class))
                .thenReturn(model.getSalesOrderResponse().toBuilder());

        Mono<InvoicedSap> actual = dataMapper.mapToEntity(data);
        StepVerifier.create(actual)
                .expectNext(model)
                .verifyComplete();
    }

}