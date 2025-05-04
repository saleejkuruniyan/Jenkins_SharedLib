def call(String apiKey, String cacheDir) {
    withCredentials([string(credentialsId: 'nvd-api-key', variable: 'NVD_API_KEY')]) {
        sh """
        dependency-check.sh \\
            --project "${env.JOB_NAME}" \\
            --scan "${env.WORKSPACE}" \\
            --nvdApiKey "$NVD_API_KEY" \\
            --data ${cacheDir} \\
            --format ALL \\
            --failOnCVSS 7 \\
            --disableAssembly \\
            --enableExperimental
        """
        dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
    }
}
