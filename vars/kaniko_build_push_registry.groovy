def call(String RegistryURL, String ProjectName, String registryCred, List<List<String>> builds) {
    def scriptLines = []

    withCredentials([usernamePassword(credentialsId: "${registryCred}", passwordVariable: 'registrypass', usernameVariable: 'registryuser')]) {
        for (def args : builds) {
            String DockerfilePath = args[0]
            String RepoName = args[1]
            String ImageTag = args[2]
            def destination = "${RegistryURL}/${ProjectName}/${RepoName}:${ImageTag}"

            scriptLines << """
            echo "Building and pushing image for ${RepoName}:${ImageTag}..."
            /kaniko/executor \\
                --dockerfile=${DockerfilePath}/Dockerfile \\
                --context=${WORKSPACE}/${DockerfilePath} \\
                --destination=${destination} \\
                --skip-tls-verify \\
                --build-arg DOCKER_USERNAME=${registryuser} \\
                --build-arg DOCKER_PASSWORD=${registrypass}
            rm -rf /kaniko/0/*
            """
        }

        sh scriptLines.join("\n")
    }
}