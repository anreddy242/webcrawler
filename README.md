# webcrawler
core webcrawler

Question #1:
Requirements:
Create a core webcrawler that takes the URL's as input and retrieves the page metadata (title, description, body etc.)

Not considered: Terraform code for provisioning compute engine, security best practices in managing the passwords outside of the code repository, not respecting robots.txt, not using any user agents


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

Setup:
I have provisioned a Google compute engine which i am using to run
1. Jenkins pipeline
2. Webcrawler docker container
3. Backend postgresql DB engine


Jenkins URL to run the pipeline: http://34.125.49.138:8080/job/webcrawler/job/main/
username: webcrawler
password: WebCrawler4test


This pipeline has multiple stages to 
1. Cleanup the workspace,
2. Checkout the code from github repository
3. Compile the java program, build a package using maven
4. Build a docker image
5. Run the webcrawler container which can crawl through the URL's defined in the crawl.properties and then stop
6. Printing the webcrawler container logs
7. printing the metadata inserted in to the postgresql database table


Question #2:
I have not considered any factors that can optimize the cost, reliability, performance and scale and only focused on the basic code needed to make a working crawler in the interest of time. But if i can get enough time, below are few things that i would love to consider

1. Cost
  --> Automating the entire build and deployment pipelines
  --> Crawler to be able to resume crawling from where it left off in case of interruptions
  --> Identifying the duplicate URL's and avoid crawling the same page multiple times
  --> Decising between Depth-First Vs Breadth-First based on the requirements
  --> Avoid running the web crawler 24*7. it should only run when there are new URL's to crawl
  --> Use batch processing

2. Reliability
  --> Respecting the website's robots.txt file
  --> Using delay intervals to avoid getting blocked
  --> Using throttling mechanism to avoid hitting rate limits and adjusting crawl speed based on the rate limits
  --> Prioritizing the crawl url's based on some mechanism
  --> Handle different types of content and page structures gracefully
  --> Choosing the correct backup and restore strategies for database

3. Performance
  --> Using a multi threaded design to improve the concurrency
  --> Using some sort of queue mechanism to distribute the crawl URL's to the crawler processes
  --> Asynchronous design where ever possible
  --> Database indexing mechanisms for quick reads

4. Scale
  --> Handle different types of content, including images, documents, and multimedia files
  --> Implement logging to record the crawling process and identify any issues.
  --> Set up monitoring to track the crawler's performance and detect anomalies.
  --> Choosing the correct database that can scale up to support billions of URL's




