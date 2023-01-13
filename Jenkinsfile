pipeline{
    agent any
    tools {
        maven 'Maven 3.8.6' 
    }
    options {
        // Clean before build options
        skipDefaultCheckout(true)
    }
    parameters {
        string(name:'BROWSER', defaultValue: 'Chrome', description:'Browser where the suite is executed')
        booleanParam(name:'HEADLESS', defaultValue: true, description:'Whether execute in headless mode or not')
        string(name:'BASE_URL', defaultValue: 'https://www.saucedemo.com', description:'Site URL')
    }
    stages{
        stage('SCM Checkout'){
            steps {
               // Clean before build
               cleanWs()
               git branch: 'master',
                    url: 'https://github.com/manuelrodriguezGL/webtesting_challenge.git'
            }
        }
        stage('Test')
        {
            environment{
                SAUCE_CREDENTIALS = credentials('secret_sauce')
            }

            steps{
                sh("echo Reading Username...: $SAUCE_CREDENTIALS_USR")
                sh("echo Reading Password...")
                sh "mvn clean install -Dtestng.dtd.http=true -Dsauce_user=$SAUCE_CREDENTIALS_USR " +
                    "-Dsauce_psw=$SAUCE_CREDENTIALS_PSW -Dbrowser=${params.BROWSER} -DheadlessMode=${params.HEADLESS} " +
                    "-DbaseUrl=${params.BASE_URL}"
            }
        }
    }
}
