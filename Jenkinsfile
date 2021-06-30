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
                SAUCE_CREDENTIALS = credentials('secret_sauce')
            }

            steps{
                sh('echo Username: $SAUCE_CREDENTIALS_USR')
                sh('echo Password: $SAUCE_CREDENTIALS_PSW')
                sh 'mvn clean install -Dtestng.dtd.http=true -Dsauce_user=$SAUCE_CREDENTIALS_USR -Dsauce_psw=$SAUCE_CREDENTIALS_PSW'
                //-ea -Dtestng.dtd.http=true -Dsauce_user=standard_user -Dsauce_psw=secret_sauce
            }
        }
    }
}
