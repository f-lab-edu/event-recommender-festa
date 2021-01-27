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
          sh 'mvn clean vertify -DskipITs=true';
        }
        
        stage('Unit Test') {
          junit '**/target/surefire-reports/TEST-*.xml'
        }
  
        stage('Integration Test') {
          sh 'mvn clean vertify -Dsurefire.skip=true';
          junit allowEmptyResults: true, testResults: '**/target/failsafe-reports/TEST-*.xml'
        }
   }
  
  post { 
        success { 
            echo 'Build Success!'
        }
        failure {
            mail to: jes7077@gmail.com
          , subject: "Job Failes, Please check: '${env.JOB_NAME} [${env.BRANCH_NAME}] [${env.BUILD_NUMBER}]'"
        }
    }
}
