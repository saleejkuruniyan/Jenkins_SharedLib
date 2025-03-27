// Define function
def call(String ProjectName, String ImageTag, String dockerHubCred){
  withCredentials([usernamePassword(credentialsId: "${dockerHubCred}", passwordVariable: 'dockerhubpass', usernameVariable: 'dockerhubuser')]) {
      sh "docker build -t ${dockerhubuser}/${ProjectName}:${ImageTag} ."
  }
}
