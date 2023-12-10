# webcrawler
core webcrawler

Requirements:
Create a core webcrawler that takes the URL's as input and retrieves the page metadata (title, description, body etc.)


Prerequisites software:
OpenJDK
Maven
Jenkins
Docker
Postgresql

WebCrawler.java:
This is a basic program that reads the crawler.properties from the classpath and
1. Crawls through the URL's defined in crawler.properties urls property. We can defined n number of URL's with comma as the delimiter
2. Connects to the Postgresql database defined in crawler.properties and inserts the page metadata (title, description, body) along with the url in the webcrawler_data table under webcrawler database.

crawler.properties:
This file will have the database connection details along with the URL's that needs to be crawled
