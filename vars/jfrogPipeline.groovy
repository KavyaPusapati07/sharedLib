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
					id: "${params.id}",
					url: "${params.url}",
					username: 'kavya',
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
		string(name:'id',defaultValue: 'jfrog',description: '')
		string(name:'url',defaultValue: 'https://kavyapusapati07.jfrog.io/artifactory',description:'')
		
	}
}
}
