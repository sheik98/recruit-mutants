package grupoexito.resend_invoicing_sap_microservice.mongodb.order.data;

import grupoexito.resend_invoicing_sap_microservice.domain.orderdata.OrderOMS;
import grupoexito.resend_invoicing_sap_microservice.domain.orderdata.detailgetorder.*;
import grupoexito.resend_invoicing_sap_microservice.mongodb.order.data.detailgetorder.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.reactivecommons.utils.ObjectMapper;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParseDataToEntityTest {

    private static final String TEST = "TEST";

    @Mock
    private ObjectMapper mapper;

    @InjectMocks
    private ParseDataToEntity parseDataToEntity;

    @Test
    public void correctExecution() {
        OrderOMS expected = OrderOMS.builder()
                .id(TEST)
                .origin(TEST)
                .paymentMethod(TEST)
                .paymentSystem(TEST)
                .clientIdentificationDocument(TEST)
                .detailGetOrderList(Collections.singletonList(DetailGetOrder.builder()
                        .orderId(TEST)
                        .sellerOrderId(TEST)
                        .itemsAif(Collections.singletonList(Item.builder()
                                .id(TEST)
                                .price(1L)
                                .seller(TEST)
                                .priceTagsAif(Collections.singletonList(PriceTags.builder()
                                        .name(TEST)
                                        .value(1L)
                                        .build()))
                                .build()))
                        .ratesAndBenefitsDataAif(RatesAndBenefitsData.builder()
                                .id(TEST)
                                .rateAndBenefitsIdentifiersAif(Collections.singletonList(RateAndBenefitsIdentifiers.builder()
                                        .id(TEST)
                                        .name(TEST)
                                        .description(TEST)
                                        .build()))
                                .build())
                        .build()))
                .build();

        OrderData orderData = OrderData.builder()
                .id(TEST)
                .origin(TEST)
                .paymentMethod(TEST)
                .paymentSystem(TEST)
                .clientIdentificationDocument(TEST)
                .detailGetOrder(Collections.singletonList(DetailGetOrderData.builder()
                        .orderId(TEST)
                        .sellerOrderId(TEST)
                        .items(Collections.singletonList(ItemData.builder()
                                .idItemData(TEST)
                                .price(1L)
                                .seller(TEST)
                                .priceTags(Collections.singletonList(PriceTagsData.builder()
                                        .name(TEST)
                                        .value(1L)
                                        .build()))
                                .build()))
                        .ratesAndBenefitsData(RatesAndBenefitsDataData.builder()
                                .idRatesAndBenefitsData(TEST)
                                .rateAndBenefitsIdentifiers(Collections.singletonList(RateAndBenefitsIdentifiersData.builder()
                                        .idRateAndBenefitsIdentifiersData(TEST)
                                        .name(TEST)
                                        .description(TEST)
                                        .build()))
                                .build())
                        .build()))
                .build();

        when(mapper.mapBuilder(any(), eq(OrderOMS.OrderOMSBuilder.class)))
                .thenReturn(OrderOMS.builder()
                        .id(TEST)
                        .origin(TEST)
                        .paymentMethod(TEST)
                        .paymentSystem(TEST)
                        .clientIdentificationDocument(TEST));

        when(mapper.mapBuilder(any(), eq(DetailGetOrder.DetailGetOrderBuilder.class)))
                .thenReturn(DetailGetOrder.builder()
                        .orderId(TEST)
                        .sellerOrderId(TEST));

        when(mapper.mapBuilder(any(), eq(Item.ItemBuilder.class)))
                .thenReturn(Item.builder()
                        .id(TEST)
                        .price(1L)
                        .seller(TEST));

        when(mapper.mapBuilder(any(), eq(PriceTags.PriceTagsBuilder.class)))
                .thenReturn(PriceTags.builder()
                        .name(TEST)
                        .value(1L));

        when(mapper.mapBuilder(any(), eq(RateAndBenefitsIdentifiers.RateAndBenefitsIdentifiersBuilder.class)))
                .thenReturn(RateAndBenefitsIdentifiers.builder()
                        .id(TEST)
                        .name(TEST)
                        .description(TEST));

        assertThat(expected).isEqualToComparingFieldByField(parseDataToEntity.mapToEntity(orderData));
    }

    @Test
    public void failedExecution() {
        assertThatThrownBy(() -> {
            parseDataToEntity.mapToEntity(null);
        }).isInstanceOf(ClassCastException.class);
    }

}