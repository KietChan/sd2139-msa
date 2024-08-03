pipeline {
    agent any
    tools {
        nodejs "node_20" // Need to config jenkins first.
        // dockerTool 'docker_on_demand' // For local testing only.
    }
    stages {
        // stage('Test Frontend') {
        //     steps {
        //         dir('src/frontend') {
        //             sh 'npm install'
        //             sh 'npm test'
        //         }
        //     }
        // }
        stage('Read Version from Source Code') {
            steps {
                script {
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
            steps {
                script {
                    echo "Another version: ${env.VERSION}"
                }
                script {
                    sh '''
                        echo "Another version222 2: ${env.VERSION}"
                    '''
                }
            }
        }
        // stage('Commit Version Update') {
        //     steps {
        //         script {
        //             withCredentials([sshUserPrivateKey(credentialsId: 'git_kiet', keyFileVariable: 'SSH_KEY')]) {
        //                 sh '''
        //                 git config user.email "jenkins@example.com"
        //                 git config user.name "Jenkins"
        //                 git add src/frontend/package.json
        //                 git commit -m "Increment version to ${env.VERSION}"
        //                 GIT_SSH_COMMAND="ssh -i ${SSH_KEY} -o StrictHostKeyChecking=no" git push origin HEAD:main
        //                 '''
        //             }
        //         }
        //     }
        // }
    }
}
