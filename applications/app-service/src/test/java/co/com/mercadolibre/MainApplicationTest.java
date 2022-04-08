package co.com.mercadolibre;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MainApplicationTest {
    MainApplication mainApplication;

    @Test
    public void correctExecution(){
        mainApplication = new MainApplication();

        Assertions.assertThat(mainApplication).isNotNull();
    }
}