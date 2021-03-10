pipeline {

   //Donde se va a ejecutar el Pipeline
  agent {
    label 'Slave_Mac'
  }

    //Opciones específicas de Pipeline dentro del Pipeline
    options {
      	buildDiscarder(logRotator(numToKeepStr: '3'))
   	disableConcurrentBuilds()
    }

     //Una sección que define las herramientas “preinstaladas” en Jenkins
      tools {
        jdk 'JDK8_Mac' //Preinstalada en la Configuración del Master
      }
   
   //Aquí comienzan los “items” del Pipeline
  stages{
    /*stage('Checkout') {
      steps{
        echo "------------>Checkout<------------"
         checkout([
            $class: 'GitSCM', 
            branches: [[name: '*//*master']], 
            doGenerateSubmoduleConfigurations: false, 
            extensions: [], 
            gitTool: 'Default', 
            submoduleCfg: [], 
            userRemoteConfigs: [[
               credentialsId: 'GitHub_Alexisql', 
               url:'https://github.com/Alexisql/ADN-Ceiba'
            ]]
         ])

      }
    }*/  
	  
    /*stage('Clean'){
        steps{
         sh './gradlew --b build.gradle clean compileJava'
        }
     }*/
	  
    stage('Unit Tests') {
      steps{
        echo "------------>Unit Tests<------------"
	 sh './gradlew --b build.gradle test --scan'
	 /*sh './gradlew --b build.gradle jacocoTestReport'*/
      }
    }
	  
    stage('Build') {
      steps{
         echo "------------>Build<------------"
            //Construir sin tarea test que se ejecutó previamente
	    sh './gradlew --b build.gradle build -x test'
        }
     }

    stage('Static Code Analysis') {
	    steps{
				echo "------------>Static Code Analysis<------------"
		withSonarQubeEnv('Sonar'){
		    sh "${tool name: 'SonarScanner', type:'hudson.plugins.sonar.SonarRunnerInstallation'}/bin/sonar-scanner -Dproject.settings=sonar-project.properties"
		}
	    }
	}
  }
   
   post {
        always {
            echo 'This will always run'
        }
        success {
            echo 'This will run only if successful'
	    junit '**/test-results/testDebugUnitTest/*.xml'

        }
        failure {
            echo 'This will run only if failed'
            mail(to: 'alexis.quintero@ceiba.com.co',
                subject: "Failed Pipeline: ${currentBuild.fullDisplayName}",
                body: "Something is wrong with ${env.BUILD_URL}")
        }
        unstable {
            echo 'This will run only if the run was marked as unstable'
        }
        changed {
            echo 'This will run only if the state of the Pipeline has changed'
            echo 'For example, if the Pipeline was previously failing but is now successful'
        }
    }
}

