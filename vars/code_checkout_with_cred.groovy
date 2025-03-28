def call(String GitUrl, String GitBranch, String GithubCredId){
    withCredentials([usernamePassword(credentialsId: "${GithubCredId}", usernameVariable: 'GIT_USER', passwordVariable: 'GIT_TOKEN')]) {
        sh """
            git config --global credential.helper 'store'
            git clone https://\${GIT_USER}:\${GIT_TOKEN}@${GitUrl}.git
            git checkout ${GitBranch}
        """
    }
}
