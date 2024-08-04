pipeline {
    agent any

    stages {
        stage('Checkout Code') {
            steps {
                // Pull code from the Git repository
                git branch: 'master', url: 'https://github.com/KietChan/sd2139-msa'
            }
        }
        stage('Apply Kubernetes files') {
            steps {
                withKubeConfig([credentialsId: 'local_k8s_secret', serverUrl: 'https://kubernetes.docker.internal:6443']) {
                    sh 'curl -LO "https://storage.googleapis.com/kubernetes-release/release/v1.20.5/bin/linux/amd64/kubectl"'  
                    sh 'chmod u+x ./kubectl'  
                    sh './kubectl get pods'
                    sh './kubectl apply -f nginx-test'
                }
            }
        }
    }
}
