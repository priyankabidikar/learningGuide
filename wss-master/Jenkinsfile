import java.text.SimpleDateFormat 
def startDate 
def endDate 
def projectId= "139" 
def projectName= "WSS" 
def moduleId= "55" 
def moduleName= "TERA" 
def newBuildId= "3.0.0.${BUILD_ID}" 
def sonarUrl= "http://168.62.166.122:9000/" 
def tomcatCredIdForJenkins= "tomcat21" 
def tomcatServer= "http://10.53.67.21:8012"


def CODE =  readJSON text: '{"url":"http://168.62.166.122:8888/root/wss.git","scm":"gitlab","branch":"master","credId":"gitlab"}'
def UNITTEST =  readJSON text: '{"command":"mvn clean test"}'
def BUILD =  readJSON text: '{"command":"mvn package"}'
def ANALYSIS =  readJSON text: '{"sonarkey":"wss"}'
def DEPLOYMENT =  readJSON text: '{"contextPath":"wss"}' 

pipeline {
agent any
options {
skipDefaultCheckout()
 }
	stages {

		stage('CODE'){
		        steps{
		        script{
					buildName newBuildId
				}
				 	
					 
		            git url: CODE.url, credentialsId: CODE.credId, branch: CODE.branch 
		        }

		}
		stage('UNIT-TEST'){
			
		      steps{
		            println "Test code ..."
		           	//executeCmd(UNITTEST.command);
		         }
		}
		stage('Build'){
		       steps{
		       		script{
		       		startDate = new Date()
		       		}
		       		println "Building code ...."
		            executeCmd(BUILD.command);
		       }
			   post{
				   always{
					   script{
						   echo "${currentBuild.currentResult}";
						   echo CODE.url
					   		endDate = new Date()
							adoptBuildFeedback  buildDisplayName: "${projectName}.${newBuildId}",
						 						buildStartedAt: "${startDate}",
						 						status: "${currentBuild.currentResult}",
						 						buildEndedAt: "${endDate}",
						 						buildUrl: "${BUILD_URL}"+"#"+CODE.url,
						 						projectId: projectId
					   }
				   }

				}
		}

		stage('SonarQube analysis') {
				steps {
					println "Static code Analysis..."
					withSonarQubeEnv('SonarQube') {
					executeCmd("mvn sonar:sonar -Dsonar.host.url="+ sonarUrl +" -Dsonar.projectKey="+ANALYSIS.sonarkey);
						  
					}
					 timeout(time: 1, unit: 'HOURS') {
						waitForQualityGate abortPipeline: true
					}
				}
				post{
				   success{
					   script{
						 adoptCodeAnalysisFeedback  buildDisplayName: "${projectName}.${newBuildId}",
						 							buildUrl: "${BUILD_URL}",
						 							sonarKey: "${ANALYSIS.sonarkey}",
						 							projectId: projectId
						 							
					   }
				   }
				 }
		   }
	
		stage('Deployment'){
			             steps{            
		                    deploy adapters: [tomcat8(credentialsId: tomcatCredIdForJenkins,
		                            path: '', url: tomcatServer)],
		                            contextPath: DEPLOYMENT.contextPath,
		                            onFailure: true,
		                            war: 'target/*.war'
		            }
		            post{
		                always{
								script{
										echo "${currentBuild.currentResult}";
                                		startDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").format(new Date())
                                		adoptDevDeploymentFeedback  buildDisplayName: "${projectName}.${newBuildId}", 
                                									deploymentStartedAt: startDate,
                                									environment: "DEV",
                                									moduleId: moduleId,
                                									moduleName: moduleName,
                                									projectName: projectName,
                                									status: "${currentBuild.currentResult}"
								}
							}
		                   
		            }
			
		}

	}
}

//Helper Methods 

void executeCmd(String CMD){
	if(isUnix()){
		sh "echo linux"
		sh CMD
	}
	else{
		 bat "echo windows"
		 bat CMD
	}
}
