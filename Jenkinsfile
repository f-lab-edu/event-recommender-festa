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
}
