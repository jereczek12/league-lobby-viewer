package feign.ssl;

import javax.net.ssl.X509TrustManager;
import java.security.cert.X509Certificate;

/**
 * Trust Manager that will trust all certificates.
 * <p>
 * This implementation is insecure and will make the connection vulnerable.
 * </p>
 */
public class InsecureTrustManager implements X509TrustManager {
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) {
        // Allows all clients
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) {
        // Allows all servers
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return new X509Certificate[0];
    }
}
