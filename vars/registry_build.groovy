def call(String RegistryURL, String ProjectName, String RepoName, String ImageTag, String registryCred){
  withCredentials([usernamePassword(credentialsId: "${registryCred}", passwordVariable: 'registrypass', usernameVariable: 'registryuser')]) {
      sh "docker build -t ${RegistryURL}/${ProjectName}/${RepoName}:${ImageTag} ."
  }
}
