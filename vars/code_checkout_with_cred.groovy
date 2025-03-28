def call(String GitUrl, String GitBranch, String GithubCredId){
    withCredentials([gitUsernamePassword(credentialsId: "${GithubCredId}", gitToolName: 'Default')]) {
        sh """
            git config --global credential.helper 'cache'
            
            # Clone the repository using the configured credentials
            git clone ${GitUrl}
            
            // # Extract repo name from Git URL and enter the directory
            // cd \$(basename ${GitUrl} .git)
            
            # Checkout the required branch
            git checkout ${GitBranch}
        """
    }
}
