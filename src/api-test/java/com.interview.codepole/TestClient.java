package com.interview.codepole;

import java.util.List;

import com.interview.codepole.api.ItemRequestV1;
import com.interview.codepole.api.OrderRequestV1;
import com.interview.codepole.api.TotalValueResponseV1;
import com.interview.codepole.api.UserOrderResponseV1;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.*;

@Lazy
@TestComponent
public class TestClient {

    private final TestRestTemplate restTemplate;
    private final String host;


    public TestClient(TestRestTemplate restTemplate, @LocalServerPort int port) {
        this.restTemplate = restTemplate;
        this.host = "http://localhost:" + port;
    }


    public <T> ResponseEntity<T> postOrder(String userId, List<ItemRequestV1> items, Class responseClass) {
        OrderRequestV1 orderRequestV1 = new OrderRequestV1(items);
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-id", userId);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return restTemplate.exchange(host + "/v1/order", HttpMethod.POST, new HttpEntity<>(orderRequestV1, headers),  responseClass);
    }

    public ResponseEntity<UserOrderResponseV1> getListOrder(String userId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-id", userId);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        return restTemplate.exchange(host + "/v1/order", HttpMethod.GET, new HttpEntity<>(headers), UserOrderResponseV1.class);
    }

    public ResponseEntity<TotalValueResponseV1> getTotalOrderByTimeRange(String userId, String from, String to) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("user-id", userId);
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        String url = host + "/v1/order/total?from=" + from + "&to=" + to;
        return restTemplate.exchange(url, HttpMethod.GET, new HttpEntity<>(headers), TotalValueResponseV1.class);
    }

}
