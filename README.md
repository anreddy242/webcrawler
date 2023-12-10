# webcrawler
core webcrawler

# Question #1:
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

Example run: http://34.125.49.138:8080/job/webcrawler/job/main/7/console


This pipeline has multiple stages to 
1. Cleanup the workspace,
2. Checkout the code from github repository
3. Compile the java program, build a package using maven
4. Build a docker image
5. Run the webcrawler container which can crawl through the URL's defined in the crawl.properties and then stop
6. Printing the webcrawler container logs
7. printing the metadata inserted in to the postgresql database table


# Question #2:
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

Key Monitoring metrics i would choose:
1. Crawl rate (Number of pages crawled per second)
2. Latency (Time taken to crawl a single page) (average and then percentiles)
3. Duplication rate (percentage of duplicate pages encountered)
4. HTTP status codes from each of the crawled pages
5. Error rate (percentage of crawled pages which returned errors)
6. CPU and memory utilization rate (all components such as crawler, queuing system, databses etc. along with the underlying infrastructure)
7. Network bandwidth utlization rate
8. Queue Size (Number of URL's in the queue waiting to be crawled)
9. Crawl distribution rate
10. Age of crawled content
11. Crawling depth rate
12. robots.txt compliance rate
13. Crawled content rediness

Service Level Indicators: 
Above 13 key monitoring metrics can be taken as SLI's in order to define the SLO's and SLA's

Few example SLO's:
1. Crawlrate: The system should be capable of crawling and indexing a minimum of 1,000 pages per minute
2. Latency: 99% of HTTP requests for fetching web pages should be completed within 750 milliseconds
3. Availability: The crawling system should be available and responsive 99.9% of the time on a monthly basis
4. Age of crawled content: Web pages should be re-crawled and indexed within 24 hours of any changes being detected
5. Error rate: Keep the HTTP error rate below 1% for crawled pages
6. robots.txt compliance: Ensure compliance with robots.txt rules for at least 99% of crawled websites
7. Resource utilization: CPU utilization below 70% and memory usage below 80% during peak crawling periods and network utilization below 90%
8. Crawled content readiness: Ensure that newly crawled content is indexed and searchable within 15 minutes

SLA's are nothing but the level of service expected by the consumer from the provider so the above SLI and SLO should straight forward convert in to an SLA

# Question #3:
As a part of proof of concept, we need to implement a web crawler in a development environment. Things that need to be considered with the proof of concept are as below

1. Choosing the Programming language for the crawler. This is very critical in order to make sure that all the features that we want to build for the crawler must be supported by the programming language or any of the additional plugins or libraries with in that language
2. Choosing the suitable crawler framework 
3. Choosing  what queuing system to use. This is very critical in order to acheive the scalability that we have in mind
4. Choosing the database which can be used to store the crawled metadata and then optimizing it and making it available for the customers or for the other datawarehousing components (SQL vs NOSQL etc.)
5. Platform to run all the components. This is important in order to make sure we have enough capacity when we are ready to scale. Any public cloud is a better option than hosting it in inhouse datacenter unless the data center has good buffer capacity
6. Choosing the system architecture, including components like URL frontier, downloader, parser, and storage


Different phases of implementation:
1. Defining requirements and scope
2. System architecture design
3. Choosing the technology stack
4. proof of concept
5. Addressing the potential issues/blockers identified in proof of concept
6. coming up with the implementation schdules for different environments and types of testing
7. Quality Assurance testing (unit, integration, performance and security)
8. Release planning
9. Deployment and post release support (Full scale Monitoring setup, Automating the infra provisioning, CICD Pipelines, Database setup and support)
10. Documentation

Proof of concept:
1. Start with a limited scope or a subset of features
2. Implement a basic data model and storage mechanism
3. Develop the core crawling logic and initial parsers
4. Evaluate the functionality and performance under a fraction of production load in order to estimate the scalability

Potential Blockers:
1. Respecting robots.txt: Ensure compliance with website rules specified in robots.txt. We may get blocked while figuring out the logic
2. Some websites implements captcha's and ip blocking as an anti scarring technique so it will be difficult to create alogic which can address all the scenarios
3. Handling websites with dynamic content may become difficult
4. Ensuring compliance with legal and ethical standards
5. Handling variations in URL's
6. Addressing duplicate content


Timelines:
Requirements Gathering: 1 week
System Architecture design: 2 weeks
Choosing the technology stack: 1 week
Proof of concept: 4 weeks (We can also setup CICD pipelines during this phase without any additional time)
Addressing the identified issues and developing the code to cover entire scope: 6 weeks
Test environment provisioning: 2 weeks (includes infra provisioning, Docker/K8s cluster setup, Database setup)
Testing: 2 weeks for integration and performance
Release planning and Deployment to production: 1 week
Adressing the monitoring gaps, documentation: 1 week


