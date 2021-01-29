package netty.utils;

import com.google.api.client.http.*;
import com.google.api.client.http.javanet.NetHttpTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class HttpUtils {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
    private static HttpTransport httpTransport;
    static {
        try {
        	httpTransport = new NetHttpTransport.Builder().build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     * http Get
     * 
     * @param resource 链接
     * @return 返回调用内容
     */
    public static String getContent(String resource) throws IOException {
        HttpRequestFactory factory = httpTransport.createRequestFactory();
        HttpRequest request = factory.buildGetRequest(new GenericUrl(resource));
        HttpResponse resp = request.execute();
        String body = null;
        if (logger.isDebugEnabled()) {
            logger.debug("response code: {}", resp.getStatusCode());
        }
        body = resp.parseAsString();
        logger.info("response body: {}", body);
        return body;
    }


}
