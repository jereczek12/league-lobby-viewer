package feign.ssl;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

/**
 * SSL socket factory, which will trust all certificates.
 * Used in {@link feign.LeagueChatClientImpl}.
 */
public class InsecureSslSocketFactory {
    /**
     * @return A SSLSocketFactory using {@link InsecureTrustManager} to trust all certificates.
     */
    public static SSLSocketFactory getInsecureSslSocketFactory() {
        try {
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new InsecureTrustManager[]{new InsecureTrustManager()}, new java.security.SecureRandom());

            SSLSocketFactory sslSocketFactory;
            return sslSocketFactory = sslContext.getSocketFactory();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize RegionClientImpl", e);
        }
    }
}
