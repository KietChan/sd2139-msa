pipeline {
    agent any
    tools {
        nodejs "node_20" // Need to config jenkins first.
        // dockerTool 'docker_on_demand' // For local testing only.
    }
    environment {
        VERSION = ""
    }
    stages {
        // stage('Test Backend') {
        //     steps {
        //         dir('src/backend') {
        //             sh 'npm install'
        //             sh 'npm run lint' // Expect to fail due to styte check violation in the source code
        //         }
        //     }
        // }
        stage('Test Frontend') {
            steps {
                dir('src/frontend') {
                    sh 'npm install'
                    sh 'npm test'
                }
            }
        }
        stage('Read Version from Source Code') {
            steps {
                script {
                    def packageJson = readJSON file: 'src/frontend/package.json'
                    def versionParts = packageJson.version.tokenize('.')
                    versionParts[2] = (versionParts[2].toInteger() + 1).toString()
                    VERSION = versionParts.join('.')
                    packageJson.version = VERSION
                    writeJSON file: 'src/frontend/package.json', json: packageJson, pretty: 4
                    echo "New version: ${VERSION}"
                }
            }
        }
        stage('Commit Version Update') {
            steps {
                script {
                    withCredentials([sshUserPrivateKey(credentialsId: 'git_kiet', keyFileVariable: 'SSH_KEY')]) {
                        sh '''
                        git config user.email "jenkins@example.com"
                        git config user.name "Jenkins"
                        git add src/frontend/package.json
                        git commit -m "Increment version to ${VERSION}"
                        GIT_SSH_COMMAND="ssh -i ${SSH_KEY} -o StrictHostKeyChecking=no" git push origin HEAD:main
                        '''
                    }
                }
            }
        }
        // stage('Build Frontend Docker Image') {
        //     steps {
        //         dir('src/frontend') {
        //             sh "docker build -t my-frontend-image:${VERSION} ."
        //         }
        //     }
        // }


        // stage('Build Backend Docker Image') {
        //     steps {
        //         dir('src/backend') {
        //             sh "docker build -t my-backend-image:${VERSION} ."
        //         }
        //     }
        // }
        
        // stage('Build Backend Docker Image') {
        //     steps {
        //         dir('src/backend') {
        //             sh 'docker build -t my-backend-image .'
        //         }
        //     }
        // }
        // stage('Build Frontend Docker Image') {
        //     steps {
        //         dir('src/frontend') {
        //             sh 'docker build -t my-frontend-image .'
        //         }
        //     }
        // }
    }
}
