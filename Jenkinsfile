pipeline{
    agent any
    tools {
        maven 'Maven 3.6.1' 
    }
    options {
        skipDefaultCheckout(true)
    }
    stages{
        stage('SCM Checkout'){
            steps {
               // Clean before build
               cleanWs()
               git "https://github.com/manuelrodriguezGL/webtesting_challenge"
            }
        }
        stage('Test')
        {
            steps{
                sh 'mvn clean install -Dtestng.dtd.http=true'
            }
        }
    }
}
