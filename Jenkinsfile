pipeline {
    agent any

    stages {
        stage('Build with Maven') {
            steps {
                echo "📦 Building the project..."
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Run Tests') {
            steps {
                echo "🧪 Running tests..."
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                echo "🐳 Building Docker image..."
                sh 'docker build -t student-performance-tracker .'
            }
        }

        stage('Deploy with Ansible') {
            steps {
                echo "🚀 Deploying..."
                sh 'ansible-playbook ansible/deploy.yml'
            }
        }
    }

    post {
        success {
            echo "✅ CI/CD pipeline succeeded!"
        }
        failure {
            echo "❌ CI/CD pipeline failed!"
        }
    }
}

