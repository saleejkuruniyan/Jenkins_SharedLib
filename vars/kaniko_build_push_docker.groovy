def call(String dockerHubCred, List<List<String>> builds) {
    withCredentials([usernamePassword(
        credentialsId: "${dockerHubCred}",
        usernameVariable: 'DOCKERHUB_USER',
        passwordVariable: 'DOCKERHUB_PASS'
    )]) {
        def scriptLines = []
        
        scriptLines << "mkdir -p /kaniko/.docker"
        
        for (def args : builds) {
            String DockerfilePath = args[0]
            String RepoName = args[1]
            String ImageTag = args[2]
            def destination = "${DOCKERHUB_USER}/${RepoName}:${ImageTag}"

            scriptLines << """
            echo "Building image for ${RepoName}:${ImageTag}..."
            /kaniko/executor \\
                --dockerfile=${DockerfilePath}/Dockerfile \\
                --context=${WORKSPACE}/${DockerfilePath} \\
                --destination=${destination} \\
                --username=\$DOCKERHUB_USER \\
                --password=\$DOCKERHUB_PASS \\
                --skip-tls-verify
            rm -rf /kaniko/0/*
            """
        }

        sh scriptLines.join("\n")
    }
}