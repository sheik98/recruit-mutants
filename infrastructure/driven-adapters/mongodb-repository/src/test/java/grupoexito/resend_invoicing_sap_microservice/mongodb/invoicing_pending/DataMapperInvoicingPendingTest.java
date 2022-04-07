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
import org.mockito.junit.MockitoJUnitRunner;
import org.reactivecommons.utils.ObjectMapper;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Collections;

import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class DataMapperInvoicingPendingTest {

    private static final String TEST = "TEST";

    @InjectMocks
    DataMapperInvoicingPending dataMapper;

    @Mock
    private ObjectMapper mapper;

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
    public void mapToEntityRequest() {
        when(mapper.mapBuilder(invoicingPendingData, InvoicingPending.InvoicingPendingBuilder.class))
                .thenReturn(invoicingPending.toBuilder());
        when(mapper.mapBuilder(invoicingPendingData.getSalesOrder().getHeadAnex(), HeadAnex.HeadAnexBuilder.class))
                .thenReturn(invoicingPending.getSalesOrder().getHeadAnex().toBuilder());
        when(mapper.mapBuilder(invoicingPendingData.getSalesOrder().getTitemsList().get(0), ItemPendingTitems.ItemPendingTitemsBuilder.class))
                .thenReturn(invoicingPending.getSalesOrder().getTitemsList().get(0).toBuilder());

        Mono<InvoicingPending> actual = dataMapper.mapToEntity(invoicingPendingData);
        StepVerifier.create(actual)
                .expectNext(invoicingPending)
                .verifyComplete();
    }
}