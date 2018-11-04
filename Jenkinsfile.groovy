pipeline{
    agent any
    parameters{
        string (name:'tomcat-dev', defaultValue: '18.224.22.2', discripton:'staging sever')
        string (name:'tomcat-prod', defaultValue: '3.16.56.136', discripton:'staging sever')
    }

    triggers{
        pollSCM('* * * * *')
    }

    stages{
        parallel{
            stage('Deployment to Staging'){
                steps{
                    sh "scp -i V:/Program Files/Servers/NewKeyPair.pem **/target/*.war ec2-user@${params.tomcat-dev}:/var/lib/tomcat7/webapps"
                }
            }

            stage('Deployment to PDC'){
                steps{
                    sh "scp -i V:/Program Files/Servers/NewKeyPair.pem **/target/*.war ec2-user@${params.tomcat-prod}:/var/lib/tomcat7/webapps"
                }
            }
        }
    }
}