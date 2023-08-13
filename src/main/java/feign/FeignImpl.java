package feign;

import feign.jackson.JacksonDecoder;
import lombok.Getter;

public class FeignImpl {
    @Getter
    private final FeignClient client;

    public FeignImpl() {
        client = Feign.builder()
                .decoder(new JacksonDecoder())
                .target(FeignClient.class, "https://cat-fact.herokuapp.com");
    }

}
