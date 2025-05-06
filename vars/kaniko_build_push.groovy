def call(String RegistryURL, String ProjectName, List<List<String>> builds) {
    def scriptLines = []

    for (def args : builds) {
        String DockerfilePath = args[0]
        String RepoName = args[1]
        String ImageTag = args[2]
        def destination = "${RegistryURL}/${ProjectName}/${RepoName}:${ImageTag}"

        scriptLines << """
        echo "Building image for ${RepoName}:${ImageTag}..."
        /kaniko/executor \\
            --dockerfile=${DockerfilePath}/Dockerfile \\
            --context=${WORKSPACE}/${DockerfilePath} \\
            --destination=${destination} \\
            --skip-tls-verify
        rm -rf /kaniko/0/*
        """
    }

    sh scriptLines.join("\n")
}