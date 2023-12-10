import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class WebCrawler {

    private static final Logger logger = LoggerFactory.getLogger(WebCrawler.class);

    public static void main(String[] args) {
        if (args.length != 1) {
            logger.error("Usage: java WebCrawler <properties_file_path>");
            System.exit(1);
        }

        String propertiesFilePath = args[0];
        Properties properties = loadProperties(propertiesFilePath);

        String[] urls = properties.getProperty("urls").split(",");
        String dbUrl = properties.getProperty("db.url");
        String dbUser = properties.getProperty("db.user");
        String dbPassword = properties.getProperty("db.password");

        for (String url : urls) {
            try {
                crawlAndStore(url, dbUrl, dbUser, dbPassword);
            } catch (Exception e) {
                logger.error("Error processing URL: {}", url, e);
            }
        }
    }

    private static void crawlAndStore(String url, String dbUrl, String dbUser, String dbPassword) throws IOException, SQLException {
        logger.info("Processing URL: {}", url);

        Document document = Jsoup.connect(url).get();

        String title = document.title();
        String description = document.select("meta[name=description]").attr("content");
        String body = document.body().text();

        logger.info("Title: {}", title);
        logger.info("Description: {}", description);
        logger.info("Body: {}", body);

        try (Connection connection = DriverManager.getConnection(dbUrl, dbUser, dbPassword)) {
            String insertQuery = "INSERT INTO webcrawler_data (url, title, description, body) VALUES (?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, url);
                preparedStatement.setString(2, title);
                preparedStatement.setString(3, description);
                preparedStatement.setString(4, body);
                preparedStatement.executeUpdate();
            }
        }
        logger.info("Data stored successfully.");
    }

    private static Properties loadProperties(String filePath) {
        Properties properties = new Properties();
        try {
            properties.load(WebCrawler.class.getClassLoader().getResourceAsStream(filePath));
        } catch (IOException e) {
            logger.error("Error loading properties file", e);
            System.exit(1);
        }
        return properties;
    }
}
