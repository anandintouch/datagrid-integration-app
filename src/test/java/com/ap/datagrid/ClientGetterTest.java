package com.ap.datagrid;

import org.junit.Test;

import com.ap.datagrid.spring.core.client.Client;
import com.ap.datagrid.spring.core.client.ClientGetter;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;

public class ClientGetterTest {

    @Test
    public void shouldGenerateRandomClients() {
        //given
        ClientGetter repository = new ClientGetter() {

            @Override
            protected void randomDelay() {
                //ignore delay
            }
        };

        //when
         List<Client> bestClients = repository.getBestClients();
        //Client bestClients = repository.getBestClients();

        //then
        assertThat(bestClients).hasSize(10);
        //assertEquals("Jan", bestClients.getFirstName());
    }

}