def call(String GitUrl, String GitBranch){
  git url: "${GitUrl}", branch: "${GitBranch}"
}
def call(String GitUrl, String GitBranch, String Github-cred){
  withCredentials([gitUsernamePassword(credentialsId: "${Github-cred}", gitToolName: 'Default')]) {
      git url: "${GitUrl}", branch: "${GitBranch}"
  }
}
