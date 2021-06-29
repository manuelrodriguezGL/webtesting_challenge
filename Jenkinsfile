pipeline{
    agent any
    tools {
        maven 'Maven 3.6.1' 
    }
    options {
        // Clean before build options
        skipDefaultCheckout(true)
    }
    stages{
        stage('SCM Checkout'){
            steps {
               // Clean before build
               cleanWs()
               git branch: 'development', 
                    url: 'https://github.com/manuelrodriguezGL/webtesting_challenge.git'
            }
        }
        stage('Test')
        {
            environment{
                SECRET_FILE_ID = credentials('secret_file')
                //SAUCE_USER = credentials('SAUCE_USER')
                //SAUCE_PWD = credentials('SAUCE_PWD')
            }

            steps{
                sh('echo Username: $SECRET_FILE_ID_USR')
                sh('echo Password: $SECRET_FILE_ID_PSW')
                sh 'mvn clean install -Dtestng.dtd.http=true'
            }
        }
    }
}
