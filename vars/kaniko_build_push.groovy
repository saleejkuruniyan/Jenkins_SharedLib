def call(String registryPath, String registryCred, List<List<String>> builds, String registryUrl = 'https://index.docker.io/v1/') {
    withCredentials([usernamePassword(
        credentialsId: "${registryCred}",
        usernameVariable: 'REGISTRY_USER',
        passwordVariable: 'REGISTRY_PASS'
    )]) {
        def scriptLines = []
        
        scriptLines << '''
        mkdir -p /kaniko/.docker
        echo '{"auths":{"${registryUrl}":{"auth":"'$(echo -n "$REGISTRY_USER:$REGISTRY_PASS" | base64 -w0)'"}}}' > /kaniko/.docker/config.json
        '''

        for (def args : builds) {
            String DockerfilePath = args[0]
            String RepoName = args[1]
            String ImageTag = args[2]
            def destination = "${registryPath}/${RepoName}:${ImageTag}"

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
}