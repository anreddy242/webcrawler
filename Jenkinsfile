pipeline {
    agent any
    
    stages {
        stage('Checkout') {
            steps {
                // Check out the code from your version control system (e.g., Git)
                git git@github.com:anreddy242/webcrawler.git
            }
        }
        
        stage('Build with Maven') {
            steps {
                // Use Maven to compile and package your Java program
                sh 'mvn clean install'
            }
        }
        
        stage('Build Docker Image') {
            steps {
                // Build a Docker image with the compiled Java program
                script {
                    def imageName = "webcrawler:latest"
                    docker.build(imageName, '-f Dockerfile .')
                }
            }
        }
        
        stage('Run Docker Image') {
            steps {
                // Run the Docker image
                script {
                    def containerName = "webcrawler"
                    def imageName = "webcrawler:latest"
                    docker.image(imageName).run("--name ${containerName} --network=host -d")
                    }
                }
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
