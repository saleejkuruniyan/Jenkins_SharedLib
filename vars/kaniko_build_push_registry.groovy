def call(String RegistryPath, List<List<String>> builds) {
    for (def args : builds) {
        String dir = args[0]
        String repo = args[1]
        String tag = args[2]
        def dest = "${RegistryPath}/${repo}:${tag}"

        sh """
            echo "---- DEBUG: Working directory: \$(pwd)"
            echo "---- DEBUG: Dockerfile path: ${dir}/Dockerfile"
            echo "---- DEBUG: Context path: \$(pwd)/${dir}"
            echo "---- DEBUG: Full Kaniko call:"
            echo "/kaniko/executor --dockerfile=${dir}/Dockerfile --context=\$(pwd)/${dir} --destination=${dest} --skip-tls-verify"
            
            /kaniko/executor \\
                --dockerfile=${dir}/Dockerfile \\
                --context=\$(pwd)/${dir} \\
                --destination=${dest} \\
                --cache=true \\
                --cache-dir=/cache \\
                --skip-tls-verify \\
                --build-arg http_proxy=$http_proxy \\
                --build-arg https_proxy=$https_proxy \\
                --build-arg no_proxy=$no_proxy \\
            rm -rf /kaniko/0/*
        """
    }
}
