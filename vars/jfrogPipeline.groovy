def call(String url){
pipeline{
	agent any
	stages{
		stage('git cheakout'){
			steps{
				git branch: 'master',url: 'https://github.com/KavyaPusapati07/spring-boot-data-H2-embedded.git'
			}
		}
		stage('Clean and Install'){
			steps{
				bat 'mvn clean install'
			}
		}
		stage('package'){
			steps{
				bat 'mvn package'
			}
		}
		stage('server'){
			steps{
				rtServer(
					id: "jfrog",
					url: 'http://3.237.175.178:8082/artifactory',
					username: 'admin',
					password: 'Kavya62788#',
					bypassProxy:true,
					timeout:300
				)
			}
		}
		stage('upload'){
			steps{
				rtUpload(
					serverId:"jfrog",
					spec:'''{
						"files":[
							{
								"pattern": "target/*.jar",
								"target": "logic-ops-lab-libs-snapshot-local"
							}
								]
					}''',
				)
			}
		}
		stage('publish build info'){
			steps{
				rtPublishBuildInfo(
					serverId:"jfrog"
				)
			}
		}
	}
	parameters{
		String(name:'id',defaultValue: 'jfrog',description: '')
	}
}
}
