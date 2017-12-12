package com.mustafatosun.webservice.client;

import com.mustafatosun.webservice.client.generated.Country;
import com.mustafatosun.webservice.client.generated.GetCountryRequest;
import com.mustafatosun.webservice.client.generated.GetCountryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.transport.WebServiceMessageSender;
import org.springframework.ws.transport.http.HttpComponentsMessageSender;

import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

public class CountriesWebServiceClient extends WebServiceGatewaySupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(CountriesWebServiceClient.class);

    private final Integer connectTimeout;
    private final Integer readTimeout;

    public CountriesWebServiceClient(Integer connectTimeout, Integer readTimeout) {
        super();
        setMarshaller(marshaller());
        setUnmarshaller(marshaller());
        setDefaultUri("http://localhost:8080/ws");
        this.connectTimeout = connectTimeout;
        this.readTimeout = readTimeout;
        setMessageSender(httpComponentsMessageSender());
    }

    private WebServiceMessageSender httpComponentsMessageSender() {
        HttpComponentsMessageSender sender = new HttpComponentsMessageSender();
        sender.setConnectionTimeout(connectTimeout);
        sender.setReadTimeout(readTimeout);
        return sender;
    }

    private Jaxb2Marshaller marshaller(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("com.mustafatosun.webservice.client.generated");
        return marshaller;
    }

    @SuppressWarnings("unchecked")
    public Country getCountry(String countryName){
        LOGGER.info("Delegating request to remote service ... Country:{}", countryName);
        GetCountryRequest request = buildRequest(countryName);

        return ((GetCountryResponse) getWebServiceTemplate().marshalSendAndReceive(
                new JAXBElement<>(new QName("http://mustafatosun.com/webservice/server/generated","getCountryRequest"), GetCountryRequest.class,request)
        )).getCountry();
    }

    private GetCountryRequest buildRequest(String countryName) {
        GetCountryRequest request = new GetCountryRequest();
        request.setName(countryName);
        return request;
    }
}
