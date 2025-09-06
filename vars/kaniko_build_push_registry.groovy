def call(String RegistryPath, List<List<String>> builds) {
    for (def args : builds) {
        String dir  = args[0]
        String repo = args[1]
        String tag  = args[2]
        String dockerfile = (args.size() > 3 && args[3]) ? args[3] : 'Dockerfile'
        def dest = "${RegistryPath}/${repo}:${tag}"
        def digestFile = "/kaniko/${repo}.digest"
        def refFile = "/kaniko/${repo}.ref"

        sh """
            echo "---- DEBUG: Working directory: \$(pwd)"
            echo "---- DEBUG: Dockerfile path: ${dir}/${dockerfile}"
            echo "---- DEBUG: Context path: \$(pwd)/${dir}"
            echo "---- DEBUG: Full Kaniko call:"
            echo "/kaniko/executor --dockerfile=${dir}/${dockerfile} --context=\$(pwd)/${dir} --destination=${dest} --digest-file=${digestFile} --image-name-tag-with-digest-file=${refFile} --skip-tls-verify"

            /kaniko/executor \\
                --dockerfile=${dir}/${dockerfile} \\
                --context=\$(pwd)/${dir} \\
                --destination=${dest} \\
                --cache=true \\
                --cache-dir=/cache \\
                --digest-file=${digestFile} \\
                --image-name-tag-with-digest-file=${refFile} \\
                --verbosity=info \\
                --skip-tls-verify

            echo "---- Built image reference:"
            cat ${refFile} || true
            echo "---- Image digest:"
            cat ${digestFile} || true

            rm -rf /kaniko/0/*
        """
    }
}