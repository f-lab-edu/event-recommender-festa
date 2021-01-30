pipeline {
   
   agent any
   
   tools {
        maven 'maven'
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
                     sh 'mvn clean package -DskipTests=true';
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
                     sh 'mvn clean verify -Dsurefire.skip=true';
                     junit allowEmptyResults: true, testResults: '**/target/failsafe-reports/TEST-*.xml'
                }
            }
        }
   }

   post {
        always {
             script {
                  if(env.CHANGE_ID) {
                       pullRequest.comment("Show build result: ${currentBuild.absoluteUrl}")
                  }
             }
        }
   }
}
