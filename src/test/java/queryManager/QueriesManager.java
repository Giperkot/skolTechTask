package queryManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Base64;

/**
 * Created by Ilya Stvolov on 21.09.2017.
 */
public class QueriesManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(QueriesManager.class);

    private String userAgent;
    private String requestpath;
    private String httpMethod;
    private String coockies;
    private String urlParameters;

    public QueriesManager() {
    }

    public String sendRequest(String method, String url, RequestProperties properties) {

        try {
            URL httpUrl = new URL(url);
            HttpURLConnection httpURLConnection;

            if (url.startsWith("https")) {
                httpURLConnection = (HttpsURLConnection)httpUrl.openConnection();
            } else {
                httpURLConnection = (HttpURLConnection)httpUrl.openConnection();
            }

            httpURLConnection.setRequestMethod(method);

            if (properties != null && properties.getHeaders() != null) {
                for (String key : properties.getHeaders().keySet()) {
                    httpURLConnection.setRequestProperty(key, properties.getHeaders().get(key));
                }
            } else {
                httpURLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.36");
                httpURLConnection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
                httpURLConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            }

            if (properties != null && properties.getBasicAuthentification() != null) {
                httpURLConnection.setRequestProperty("Authorization", "Basic " +
                        Base64.getEncoder().encodeToString((properties.getBasicAuthentification().getLogin() + ":" + properties.getBasicAuthentification().getPassword()).getBytes()));
            }


            if (properties != null && properties.getRequestBody() != null) {
                httpURLConnection.setRequestProperty("Content-Length", String.valueOf(properties.getRequestBody().getBytes().length));
            }

            if (this.coockies != null) {
                httpURLConnection.setRequestProperty("Cookie", this.coockies);
            }


            if (properties != null && properties.getRequestBody() != null) {
                httpURLConnection.setDoOutput(true);
                DataOutputStream wr = new DataOutputStream(httpURLConnection.getOutputStream());
                wr.write(Charset.forName("UTF-8").encode(properties.getRequestBody()).array());

                wr.flush();
                wr.close();
            }


            int responseCode = httpURLConnection.getResponseCode();

            BufferedReader in;

            try {
                in = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getInputStream()));

            } catch (Exception ex) {
                in = new BufferedReader(
                        new InputStreamReader(httpURLConnection.getErrorStream()));
            }

            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch (Exception ex) {
            LOGGER.error("Ошибка в запросе", ex);
            throw new RuntimeException("Ошибка в запросе", ex);
        }
    }

    /*public <T> T sendRequestWithObject (String method, String url, String requestBody, String coockies, Class<T> resultClass) throws IOException {

        String response = sendRequest(method, url, requestBody, coockies);

        LOGGER.info(response);

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(response, resultClass);
        } catch (Exception ex) {
            LOGGER.error(response, ex);
            throw new RuntimeException(response, ex);
        }
    }*/

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getRequestpath() {
        return requestpath;
    }

    public void setRequestpath(String requestpath) {
        this.requestpath = requestpath;
    }

    public String getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(String httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getCoockies() {
        return coockies;
    }

    public void setCoockies(String coockies) {
        this.coockies = coockies;
    }

    public String getUrlParameters() {
        return urlParameters;
    }

    public void setUrlParameters(String urlParameters) {
        this.urlParameters = urlParameters;
    }
}
