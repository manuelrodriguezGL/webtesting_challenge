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
            steps{
                dir("webtesting"){
                    sh 'mvn clean install -Dtestng.dtd.http=true'
                }
            }
        }
    }
}
