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
        }
            steps{
                echo "####DISPLAYING SECRET_FILE_ID####"
           	    echo 'Global property file: ${SECRET_FILE_ID}'
                echo 'Global username: ${SAUCE_USER}'
                dir("webtesting"){
                    sh 'mvn clean install -Dtestng.dtd.http=true'
                }
            }
        }
    }
}
