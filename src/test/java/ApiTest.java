import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import queryManager.QueriesManager;

public class ApiTest {

    private final static Logger LOGGER = LoggerFactory.getLogger(ApiTest.class);

    /**
     * Тест запускается после старта приложения на порту 8080
     * @throws Exception
     */
    @Test
    public void testSaveBank() throws Exception {

        QueriesManager queriesManager = new QueriesManager();
        String response = queriesManager.sendRequest("GET", "http://localhost:8080/api/allMeasures", null);

        LOGGER.info(response);

        response = queriesManager.sendRequest("GET", "http://localhost:8080/api/history?id=0", null);

        LOGGER.info(response);

        response = queriesManager.sendRequest("GET", "http://localhost:8080/api/latest?id=0", null);

        LOGGER.info(response);

        response = queriesManager.sendRequest("GET", "http://localhost:8080/api/avg", null);

        LOGGER.info(response);
    }

}
