package com.mustafatosun.webservice.client;

import com.mustafatosun.webservice.client.generated.Country;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Configuration
public class DelegatorService {


    @Value("${ws.connectTimeout}")
    Integer connectTimeout;

    @Value("${ws.readTimeout}")
    Integer readTimeout;

    @Bean
    CountriesWebServiceClient countryServiceClient(){
        return new CountriesWebServiceClient(connectTimeout,readTimeout);
    }

    @GetMapping("/getCountry/{name}")
    public Country getCountry(@PathVariable("name") String name){
        return countryServiceClient().getCountry(name);
    }
}
