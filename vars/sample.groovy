def call(String repoUrl) {
pipeline{
agent any
stages{
stage("Checkout Code") {
steps {
git branch: 'master',
url: "${repoUrl}"
}
}
stage('Compile Stage'){
steps{
bat 'mvn clean compile'
}
}
stage('Test Stage'){
steps{
bat 'mvn test'
}
}
stage('Build Stage'){
steps{
bat 'mvn install'
}
}
}
}
}
