def call(String Project, String ImageTag, String dockerHubCred){
  withCredentials([usernamePassword(credentialsId: "${dockerHubCred}", passwordVariable: 'dockerhubpass', usernameVariable: 'dockerhubuser')]) {
      sh "docker login -u ${dockerhubuser} -p ${dockerhubpass}"
      sh "docker push ${dockerhubuser}/${Project}:${ImageTag}"
  }
}
