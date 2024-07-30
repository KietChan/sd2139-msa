pipeline {
    agent any

    stages {
        stage('Test Backend') {
            steps {
                dir('backend') {
                    sh 'npm install'
                    sh 'npm test'
                }
            }
        }
        stage('Test Frontend') {
            steps {
                dir('frontend') {
                    sh 'npm install'
                    sh 'npm test'
                }
            }
        }
        stage('Build Backend Docker Image') {
            steps {
                dir('backend') {
                    sh 'docker build -t my-backend-image .'
                }
            }
        }
        stage('Build Frontend Docker Image') {
            steps {
                dir('frontend') {
                    sh 'docker build -t my-frontend-image .'
                }
            }
        }
    }
}
