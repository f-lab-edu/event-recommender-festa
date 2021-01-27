pipeline {
   agent any

    tools {
        maven 'mvn3.6.3'
    }
   
   stages {
        stage('Poll') {
           steps {
               checkout scm
           }
        }

        stage('Build') {
            steps {
                 script {
                     sh 'mvn clean vertify -DskipITs=true';
                 }
            }
        }

        stage('Unit Test') {
           steps {
                 script {
                     sh 'mvn surefire:test'
                     junit '**/target/surefire-reports/TEST-*.xml'
                 }
            }
        }

        stage('Integration Test') {
           steps {
                script {
                     sh 'mvn clean vertify -Dsurefire.skip=true';
                     junit allowEmptyResults: true, testResults: '**/target/failsafe-reports/TEST-*.xml'
                }
            }
        }
   }

  post { 
        success { 
            echo 'Build Success!'
        }
     
        failure {
            mail to: "jes7077@gmail.com", 
            subject: "Job Failed!",
            body: "<b>Error at : </b><a href="${env.BUILD_URL}">${env.JOB_NAME} [${env.BRANCH_NAME}] [${env.BUILD_NUMBER}]</a>"
        }
    }
}
