def call(String RegistryURL, String ProjectName, String DockerfilePath, String RepoName, String ImageTag){
    def destination = "${RegistryURL}/${ProjectName}/${RepoName}:${ImageTag}"
        sh """
            /kaniko/executor \\
                --dockerfile=${DockerfilePath}/Dockerfile \\
                --context=${WORKSPACE}/${DockerfilePath} \\
                --destination=${destination} \\
                --skip-tls-verify
        """
    }
}
