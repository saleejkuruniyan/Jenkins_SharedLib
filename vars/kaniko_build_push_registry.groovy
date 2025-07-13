def call(String RegistryPath, List<List<String>> builds) {
    for (def args : builds) {
        String DockerfilePath = args[0]   // e.g., "backend"
        String RepoName = args[1]         // e.g., "wanderlust-backend-beta"
        String ImageTag = args[2]         // e.g., "1.0"

        def destination = "${RegistryPath}/${RepoName}:${ImageTag}"

        sh """
            echo "Building image for ${RepoName}:${ImageTag}..."
            echo "Running in: \$(pwd)"
            /kaniko/executor \\
                --dockerfile=${DockerfilePath}/Dockerfile \\
                --context=\$(pwd)/${DockerfilePath} \\
                --destination=${destination} \\
                --skip-tls-verify
            rm -rf /kaniko/0/*
        """
    }
}
