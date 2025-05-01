def call(String RepoURL, String ProjectName, String RepoName, String ImageTag, String registryCred){
  withCredentials([usernamePassword(credentialsId: "${registryCred}", passwordVariable: 'registrypass', usernameVariable: 'registryuser')]) {
      sh "docker login -u ${registryuser} -p ${registrypass}"
      sh "docker push ${RepoURL}/${ProjectName}/${RepoName}:${ImageTag}"
  }
}
