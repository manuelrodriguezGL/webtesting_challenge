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
        stage('Build')
        {
            steps{
                dir("webtesting"){
                    sh 'mvn clean install -Dtestng.dtd.http=true'
                }
                dir("webtesting/target")
                {
                    sh 'java -jar webtesting-1.0-SNAPSHOT.jar'
                }
            }
        }
    }
}
