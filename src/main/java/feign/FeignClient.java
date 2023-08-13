package feign;

public interface FeignClient {

    @RequestLine("GET /facts/")
    Response get();
}
