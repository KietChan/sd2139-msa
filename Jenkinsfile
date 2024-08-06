pipeline {
    agent any
    tools {
        nodejs "node_20" // Need to config jenkins first.
    }
    parameters {
        string(name: 'BRANCH', defaultValue: 'master', description: 'Barnch to checkout from')
        choice(name: 'MODE', choices: ['Full', 'Code Check Only', 'Package Only'], description: 'Execution Flow')
    }
    stages {
        stage('Test Frontend') {
            when {
                expression { params.MODE == 'Code Check Only' || params.MODE == 'Full' }
            }
            steps {
                dir('src/frontend') {
                    sh 'npm install'
                    sh 'npm test'
                }
            }
        }
        stage('Read Version from Source Code') {
            when {
                expression { params.MODE == 'Package Only' || params.MODE == 'Full' }
            }
            steps {
                script {
                    sh """
                        git checkout ${params.BRANCH}
                    """
                    def packageJson = readJSON file: 'src/frontend/package.json'
                    def versionParts = packageJson.version.tokenize('.')
                    versionParts[2] = (versionParts[2].toInteger() + 1).toString()
                    env.VERSION = versionParts.join('.')
                    packageJson.version = env.VERSION
                    writeJSON file: 'src/frontend/package.json', json: packageJson, pretty: 4
                    echo "New version: ${env.VERSION}"
                }
            }
        }
        stage('Commit Version Update') {
            when {
                expression { params.MODE == 'Package Only' || params.MODE == 'Full' }
            }
            steps {
                script {
                    withCredentials([sshUserPrivateKey(credentialsId: 'KietChan-Github-PrivateKey', keyFileVariable: 'SSH_KEY')]) {
                    sh """
                        # Configure SSH
                        eval "$(ssh-agent -s)"
                        ssh-add \$SSH_KEY

                        # Set up Git configuration
                        git config --global user.email "your-email@example.com"
                        git config --global user.name "Your Name"

                        # Commit and push changes
                        git add .
                        git commit -m "Your commit message"
                        git push
                    """
                    }
                }
            }
        }
        // TODO: Job to deploy to the Stage server if we have any.
    }
}
