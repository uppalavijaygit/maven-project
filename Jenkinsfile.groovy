pipeline {
    agent any

    parameters {
         string(name: 'tomcat_dev', defaultValue: '18.224.22.2', description: 'Staging Server')
         string(name: 'tomcat_prod', defaultValue: '3.16.56.136', description: 'Production Server')
    }

    triggers {
         pollSCM('* * * * *')
     }

stages{
        stage('Build'){
            steps {
                bat 'mvn clean package'
            }
            post {
                success {
                    echo 'Now Archiving...'
                    archiveArtifacts artifacts: '**/target/*.war'
                }
            }
        }

        stage ('Deployments'){
            parallel{
                stage ('Deploy to Staging'){
                    steps {
                        bat "winscp -i V:/Program Files/Servers/NewKeyPair.pem **/target/*.war ec2-user@${params.tomcat_dev}:/opt/apache-tomcat-7.0.91/webapps"
                    }
                }

                stage ("Deploy to Production"){
                    steps {
                        bat "winscp -i V:/Program Files/Servers/NewKeyPair.pem **/target/*.war ec2-user@${params.tomcat_prod}:/opt/apache-tomcat-7.0.91/webapps"
                    }
                }
            }
        }
    }
}