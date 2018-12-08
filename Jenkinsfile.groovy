pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                bat 'mvn clean package'
                bat "docker build . -t uppalavijay/tomatwebapp:${env.BUILD_ID}"
            }
        }
    }
}