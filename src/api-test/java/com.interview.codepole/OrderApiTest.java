package com.interview.codepole;

import com.interview.codepole.api.ItemRequestV1;
import com.interview.codepole.api.TotalValueResponseV1;
import com.interview.codepole.api.UserOrderResponseV1;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = CodePoleChallengeApplication.class)
@Import({
        TestClient.class
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class OrderApiTest {

    @Autowired
    private TestClient testClient;
    Pattern UUID_REGEX =
            Pattern.compile("^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$");

    @Test
    public void testOrderApi() {
        ResponseEntity<UUID> response = testClient.postOrder("3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e1",
                List.of(new ItemRequestV1("03add9f9-fef2-4f3d-a0b0-d7693d974dab", 1, 100.0, "Product 1")), UUID.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(UUID_REGEX.matcher(Objects.requireNonNull(response.getBody()).toString()).matches()).isTrue();
    }

    @Test
    public void testOrderApiWithInvalidUserId() {
        ResponseEntity<UUID> response = testClient.postOrder("3dcd71ee-3f86-4a2f-",
                List.of(new ItemRequestV1("03add9f9-fef2-4f3d-a0b0-d7693d974dab", 1, 100.0, "Product 1")), String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    public void testOrderApiWithInvalidItem() {
        ResponseEntity<UUID> response = testClient.postOrder("3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e1",
                List.of(new ItemRequestV1("03add9f9-fef2-4f3d-a0b0-", 1, 100.0, "Product 1"),
                        new ItemRequestV1("03add9f9-fef2-4f3d-a0b0-d7693d974dab", 1, 100.0, "Product 1")), String.class);
        assertThat(response.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    void testTotalOrderValue() {
        ResponseEntity<TotalValueResponseV1> responseList = testClient.getTotalOrderByTimeRange("3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e1", "2021-03-01 00:00:00", "2021-12-31 23:59:59");
        assertThat(responseList.getStatusCode().value()).isEqualTo(200);
    }

    @Test
    void testTotalOrderValueWithInvalidPayload() {
        ResponseEntity<TotalValueResponseV1> responseList = testClient.getTotalOrderByTimeRange("3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e1", "2021-01-01 ", "2021-12-31 23:59:59");
        assertThat(responseList.getStatusCode().value()).isEqualTo(400);
    }

    @Test
    public void getListOrderWithUserId() {
        ResponseEntity<UUID> response = testClient.postOrder("3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e1",
                List.of(new ItemRequestV1("03add9f9-fef2-4f3d-a0b0-d7693d974dab", 5, 100.0, "Product 1"),
                        new ItemRequestV1("03add9f9-fef2-4f3d-a0b0-d7693d974dab", 10, 100.0, "Product 1")), UUID.class);
        assertThat(response.getStatusCode().value()).isEqualTo(200);

        ResponseEntity<UserOrderResponseV1> responseList = testClient.getListOrder("3dcd71ee-3f86-4a2f-8dea-9f8e4f0f16e1");
        assertThat(responseList.getStatusCode().value()).isEqualTo(200);
    }

}
