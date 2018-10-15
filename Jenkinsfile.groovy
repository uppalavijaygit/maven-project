pipeline{
    agent any
    stages{
        stage('Build'){
                steps{
                    bat 'mvn package'
                }
                post{
                    success{
                        echo 'Now Archving..'
                        archiveArtifacts artifacts: '**/target/*.war'
                    }
                }
        }
        stage('Deploy to Staging'){
            steps{
                    build job: 'deploy-to-staging'
                }
        }
    }
}