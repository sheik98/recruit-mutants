package grupoexito.resend_invoicing_sap_microservice.mongodb.invoicing_pending;

import grupoexito.resend_invoicing_sap_microservice.domain.invoice.invoicing_pending.HeadAnex;
import grupoexito.resend_invoicing_sap_microservice.domain.invoice.invoicing_pending.InvoicingPending;
import grupoexito.resend_invoicing_sap_microservice.domain.invoice.invoicing_pending.ItemPendingTitems;
import grupoexito.resend_invoicing_sap_microservice.domain.invoice.invoicing_pending.SalesOrder;
import grupoexito.resend_invoicing_sap_microservice.mongodb.invoicing_pending.data.HeadAnexData;
import grupoexito.resend_invoicing_sap_microservice.mongodb.invoicing_pending.data.InvoicingPendingData;
import grupoexito.resend_invoicing_sap_microservice.mongodb.invoicing_pending.data.ItemTitemsData;
import grupoexito.resend_invoicing_sap_microservice.mongodb.invoicing_pending.data.SalesOrderData;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;

@RunWith(MockitoJUnitRunner.class)
public class InvoicingPendingRepositoryAdapterTest {

    private static final String TEST = "TEST";

    @InjectMocks
    private InvoicingPendingRepositoryAdapter adapter;

    @Mock
    private InvoicingPendingDataRepository repository;

    @Mock
    private ObjectMapper mapper;

    @Mock
    private DataMapperInvoicingPending dataMapperInvoicingPending;

    @Mock
    private ReactiveMongoTemplate mongoTemplate;

    private InvoicingPending invoicingPending;
    private InvoicingPendingData invoicingPendingData;

    @Before
    public void setUp() {

        ItemPendingTitems itemTitems = ItemPendingTitems.builder()
                .plu(TEST)
                .chargeTotal(1)
                .build();

        SalesOrder salesOrder = SalesOrder.builder()
                .headAnex(HeadAnex.builder().vbelnVf(TEST).build())
                .titemsList(Collections.singletonList(itemTitems))
                .build();

        invoicingPending = InvoicingPending.builder()
                .orderId(TEST)
                .creationDate(null)
                .salesOrder(salesOrder)
                .build();

        ItemTitemsData titemsData = ItemTitemsData.builder()
                .plu(TEST)
                .chargeTotal(1)
                .build();

        SalesOrderData salesOrderData = SalesOrderData.builder()
                .headAnex(HeadAnexData.builder().vbelnVf(TEST).build())
                .titemsList(Collections.singletonList(titemsData))
                .build();

        invoicingPendingData = InvoicingPendingData.builder()
                .id(TEST)
                .orderId(TEST)
                .creationDate(null)
                .salesOrder(salesOrderData)
                .build();
    }

    @Test
    public void findInvoicingPendingReprocess() {

        Mockito.when(mongoTemplate.findOne(any(), any()))
                .thenReturn(Mono.just(invoicingPendingData));

        Mockito.when(dataMapperInvoicingPending.mapToEntity(any(InvoicingPendingData.class)))
                .thenReturn(Mono.just(invoicingPending));

        Mono<InvoicingPending> actual = adapter.findInvoicingPending(TEST, TEST);
        StepVerifier.create(actual)
                .expectNext(invoicingPending)
                .verifyComplete();
    }

}