def call(String apiKey, String cacheDir) {
    sh """
    dependency-check.sh \\
        --project "${env.JOB_NAME}" \\
        --scan "${env.WORKSPACE}" \\
        --nvdApiKey ${apiKey} \\
        --data ${cacheDir} \\
        --format ALL \\
        --failOnCVSS 7 \\
        --disableAssembly \\
        --enableExperimental
    """
    dependencyCheckPublisher pattern: '**/dependency-check-report.xml'
}
