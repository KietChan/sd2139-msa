pipeline {
    agent any
    tools {
        nodejs "node_20" // Need to config jenkins first.
        // dockerTool 'docker_on_demand' // For local testing only.
    }
    stages {
        stage('Test Backend') {
            steps {
                dir('src/backend') {
                    sh 'npm install'
                    sh 'npm lint'
                }
            }
        }
        stage('Test Frontend') {
            steps {
                dir('src/frontend') {
                    sh 'npm install'
                    sh 'npm test'
                }
            }
        }
        stage('Build Backend Docker Image') {
            steps {
                dir('src/backend') {
                    sh 'docker build -t my-backend-image .'
                }
            }
        }
        stage('Build Frontend Docker Image') {
            steps {
                dir('src/frontend') {
                    sh 'docker build -t my-frontend-image .'
                }
            }
        }
    }
}
