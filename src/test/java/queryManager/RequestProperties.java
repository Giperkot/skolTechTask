package queryManager;

import java.util.Map;

public class RequestProperties {

    private String requestBody;

    private String coockies;

    private Map<String, String> headers;

    private BasicAuthentification basicAuthentification;


    public String getRequestBody() {
        return requestBody;
    }

    public void setRequestBody(String requestBody) {
        this.requestBody = requestBody;
    }

    public String getCoockies() {
        return coockies;
    }

    public void setCoockies(String coockies) {
        this.coockies = coockies;
    }

    public BasicAuthentification getBasicAuthentification() {
        return basicAuthentification;
    }

    public void setBasicAuthentification(BasicAuthentification basicAuthentification) {
        this.basicAuthentification = basicAuthentification;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }
}
