pipeline {
    agent any
    
    stages {
        stage('Cleanup') {
            steps {
                sh 'echo removing old checkout'
                sh 'rm -rf /var/lib/jenkins/workspace/webcrawler-test/webcrawler'
                sh 'docker rm webcrawler'
                sh 'docker rmi webcrawler:latest'
            }
        }

        stage('Checkout') {
            steps {
                // Check out the code from your version control system (e.g., Git)
                sh 'git clone git@github.com:anreddy242/webcrawler.git'
            }
        }
        
        stage('Build with Maven') {
            steps {
                // Use Maven to compile and package your Java program
                sh 'mvn -f webcrawler/pom.xml clean package'
            }
        }
        
        stage('Build Docker Image') {
            steps {
                // Build a Docker image with the compiled Java program
                script {
                    def imageName = "webcrawler:latest"
                    docker.build(imageName, '-f ./webcrawler/Dockerfile ./webcrawler/')
                }
            }
        }

        stage('Docker Container Start') {
            steps {
                script {
                    // Customize Docker container options as needed
                    sh 'docker run -d --name webcrawler --network=host webcrawler:latest'
                }
            }
        }
        
        stage('Run Docker Image') {
            steps {
                // Check the logs
                sleep 10
                sh 'docker logs webcrawler'
                }
            }

        stage('Print the postgresql table contents') {
            steps {
                // Run psql command
                sh 'psql -h localhost -d webcrawler -U postgres -c "select * from webcrawler_data;"'
                }
            }
        }
    
    post {
        success {
            echo 'Pipeline succeeded! Clean up resources if necessary.'
        }
        failure {
            echo 'Pipeline failed! Handle the failure gracefully.'
        }
    }
}
