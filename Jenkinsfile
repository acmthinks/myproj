node {
   def mvnHome
   def app_name = "cloudconfigserveracc"
   
   
   stage('Code Checkout') { // for display purposes
      // Get some code from a GitHub repository
      git 'https://github.com/acmthinks/cloudconfigserver_acc.git'
      // Get the Maven tool.
      // ** NOTE: This 'M3' Maven tool must be configured
      // **       in the global configuration.           
      mvnHome = tool 'M3'
   }
   stage('Build') {
      // Run the maven build (let's clean up prior builds and get through to the package goal)
      if (isUnix()) {
         sh "'${mvnHome}/bin/mvn' -Dmaven.test.failure.ignore clean package"
      } else {
         bat(/"${mvnHome}\bin\mvn" -Dmaven.test.failure.ignore clean package/)
      }
   }
   stage('Unit Test') {
	  //run Junit test cases
      junit '**/target/surefire-reports/TEST-*.xml'
      //package the deployable code into a jar (default, /var/jenkins_home/workspace/<job>)
      archive 'target/*.jar'
   }
   stage('Deploy (dev)') {
      //deploy to IBM Cloud (public)
      withCredentials([string(credentialsId: 'BLUEMIX', variable: 'bluemix_api')]) {
        //predicated on the fact that cf-cli is installed on Jenkins agent AND cloud foundry plugin is installed in Jenkins
      	sh 'cf login -a https://api.ng.bluemix.net -u apikey -p $bluemix_api'
      	sh 'cf target -o acm@us.ibm.com -s dev'
      	//sh 'cf push $app_name'
      	sh 'cf logout'
      }
      echo "http://${app_name}.mybluemix.net/ConfigData/dev"
   }
   stage ('Integration Test') {
   	  //run integration tests, contract testing, component testing, package/bin scans
   }
   
   if (env.BRANCH_NAME == "qa" || env.BRANCH_NAME == "master") { 
     stage ('Deploy (QA)') {
        //deploy to IBM Cloud (public) Container Services (k8s)
        echo "Branch: ${env.BRANCH_NAME}"
        withCredentials([string(credentialsId: 'BLUEMIX', variable: 'bluemix_api')]) {
          //predicated on the fact that cf-cli is installed on Jenkins agent AND cloud foundry plugin is installed in Jenkins
      	  sh 'bx login -a https://api.ng.bluemix.net -apikey $bluemix_api'
      	  sh 'bx target -o acm@us.ibm.com -s dev'
      	  //sh 'bx dev build'
      	  //sh 'bx dev deploy'
      	  sh 'bx logout'
        }
        //activate monitors for QA environment
        //configure log aggregators
        echo "http://${app_name}.mybluemix.net/ConfigData/qa"
     }

     stage ('Security/Performance Test') {
        //run vulnerability scanning, penetration testing
        //run performance and load testing
     }
   }
   
   if (env.BRANCH_NAME == "master") { 
     stage ('Deploy (Production)') {
        //deploy to production environment, IBM Cloud Private (k8s)
        echo "Branch: ${env.BRANCH_NAME}"
        echo "http://${app_name}.mybluemix.net/ConfigData/prod"
     }
   }
}