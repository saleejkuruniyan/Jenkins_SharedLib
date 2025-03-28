def call(String GitUrl, String GitBranch, String GithubCredId){
  git url: "${GitUrl}", branch: "${GitBranch}", credentialsId: "${GithubCredId}"
}
