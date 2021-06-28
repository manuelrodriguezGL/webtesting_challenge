pipeline{
    agent any
    tools {
        maven 'Maven 3.6.1' 
    }
    stages{
        stage('SCM Checkout'){
            steps {
               git "https://github.com/manuelrodriguezGL/webtesting_challenge"
            }
        }
        stage('Test')
        {
        environment{
            SECRET_FILE_ID = credentials('secret_file')
            SAUCE_USER = credentials('SAUCE_USER')
            SAUCE_PDW = credentials('SAUCE_PDW')
        }
            steps{
                dir("webtesting"){
                    sh 'mvn clean install -Dtestng.dtd.http=true'
                }
            }
        }
    }
}
