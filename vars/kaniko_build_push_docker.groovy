def call(String RegistryURL, String ProjectName, String dockerHubCred, List<List<String>> builds) {
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

    withCredentials([usernamePassword(credentialsId: "${dockerHubCred}", passwordVariable: 'dockerhubpass', usernameVariable: 'dockerhubuser')]) {
        sh "docker login -u ${dockerhubuser} -p ${dockerhubpass}"

        sh scriptLines.join("\n")

        builds.each { args ->
            String RepoName = args[1]
            String ImageTag = args[2]
            def imageName = "${dockerhubuser}/${ProjectName}/${RepoName}:${ImageTag}"
            sh "docker push ${imageName}"
        }
    }
}