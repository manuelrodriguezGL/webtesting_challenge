# Saucedemo Test Project

This Selenium automation project was designed to test Sauce Labs demo page https://www.saucedemo.com/

## How to run it

### Local run
In a terminal, run the following maven command:

`mvn clean install -Dtestng.dtd.http=true -Dsauce_user=$SAUCE_CREDENTIALS_USR 
    -Dsauce_psw=$SAUCE_CREDENTIALS_PSW -Dbrowser=Chrome -DheadlessMode=false 
    -DbaseUrl=https://www.saucedemo.com`
    
Replace `$SAUCE_CREDENTIALS_USR` and `$SAUCE_CREDENTIALS_PSW` with Saucedemo page credentials (user and password)

Also, to run the tests in headless mode, set `headlessMode=true`

Finally, `baseUrl` parameter is there, in case Sauce labs decides to move the page somewhere else.

### Jenkins run

#### Prerequisites
1. Install Maven plugin
2. Install Workspace cleanup plugin
3. Configure Saucedemo app credentials inside Jenkins manager

NOTE: There are more secure ways of handling credentials, but for the sake of this example, I used the most straightforward way.

#### Configuring a pipeline file

1. Add maven to your tools section
 
 `tools {
         maven 'Maven 3.6.1' 
     }`   

2. Add skipDefaultCheckout option to cleanup the workspace

 `options {
        // Clean before build options
        skipDefaultCheckout(true)
    }`

3. In your stage steps, first call the cleanup plugin `cleanWs()` Then, configure git to checkout from the desired branch

`git branch: 'testing',
    url: 'https://github.com/manuelrodriguezGL/webtesting_challenge.git'`

4. Setup an environment, where you read the credentials stored in Jenkins credentials manager. Make sure you are using the same credentials ID.
 
` environment{
    SAUCE_CREDENTIALS = credentials('secret_sauce')
} `

5. Finally, in your steps, call a shell script with maven command, similarly to a local run

`sh 'mvn clean install -Dtestng.dtd.http=true -Dsauce_user=$SAUCE_CREDENTIALS_USR ' +
    '-Dsauce_psw=$SAUCE_CREDENTIALS_PSW -Dbrowser=Chrome -DheadlessMode=false ' +
    '-DbaseUrl=https://www.saucedemo.com'
`    

Where: 

`$SAUCE_CREDENTIALS_USR` and `$SAUCE_CREDENTIALS_PSW` are default Jenkins variable names, based on the credentials ID you configured on credentials manager. 

`headlessMode=true` Is the flag for headless mode or not.

`baseUrl` parameter is there, in case Sauce labs decides to move the page somewhere else.

NOTE: Don't remove `testng.dtd.http=true`, since it is needed to run it from terminal. That's a workaround for a known TestNG bug.

