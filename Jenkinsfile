pipeline {
    agent any 

    tools {
        // Tenta usar o Maven e JDK configurados no PATH do Windows. 
        // Se você tiver configurado no "Global Tool Configuration" do Jenkins com nomes específicos, ajuste aqui.
        maven 'maven' 
        jdk 'jdk17' 
    }

    environment {
        // Definições baseadas no seu PDF [cite: 387]
        DOCKER_IMAGE = 'leofonsecaa/ac2_ca'
        DOCKER_TAG = 'latest'
        
        // O ID da credencial que você já criou no Jenkins [cite: 293]
        // Certifique-se que o ID no Jenkins é 'docker_jenkins' ou ajuste aqui.
        DOCKER_CRED_ID = 'de542259-35b0-4652-945f-ecb2e51bfc55'
    }

    stages {
        // --------------------------------------------------
        // ESTÁGIO 1: DEV (Compilação e Testes)
        // Equivalente ao seu job "Pipeline_Dev_Test_Target" [cite: 6]
        // --------------------------------------------------
        stage('DEV: Build & Test') {
            steps {
                echo 'Iniciando Build e Testes Unitários...'
                // Roda o clean install como você fazia [cite: 197]
                bat 'mvn clean install'
            }
            post {
                always {
                    // Coleta resultados do JUnit [cite: 218]
                    junit '**/target/surefire-reports/*.xml'
                    
                    // Coleta análise estática do PMD [cite: 209]
                    // Requer o plugin "Warnings Next Generation" ou "PMD"
                    recordIssues(tools: [pmdParser(pattern: '**/pmd.xml')])
                    
                    // Coleta cobertura do JaCoCo [cite: 153]
                    // Requer o plugin "JaCoCo"
                    jacoco(
                        execPattern: '**/target/jacoco.exec',
                        classPattern: '**/target/classes',
                        sourcePattern: '**/src/main/java',
                        inclusionPattern: '**/*.class'
                    )
                }
            }
        }

        // --------------------------------------------------
        // ESTÁGIO 2: DOCKER (Build & Push)
        // Equivalente ao seu job "Image_Docker" [cite: 248]
        // --------------------------------------------------
        stage('Docker: Build & Push') {
            steps {
                script {
                    echo 'Gerando pacote JAR final (sem rodar testes de novo)...'
                    // Gera o JAR para o Dockerfile copiar [cite: 302]
                    bat 'mvn clean package -Dmaven.test.skip=true'

                    echo 'Construindo imagem Docker...'
                    // Build da imagem usando o Dockerfile da raiz
                    bat "docker build -t %DOCKER_IMAGE%:%DOCKER_TAG% ."

                    echo 'Login e Push para o Docker Hub...'
                    // Resolve o problema de login que você teve[cite: 308].
                    // O withCredentials injeta as variáveis de forma segura.
                    withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", passwordVariable: 'DOCKER_PASS', usernameVariable: 'DOCKER_USER')]) {
                        // Login seguro
                        bat 'docker login -u %DOCKER_USER% -p %DOCKER_PASS%'
                        // Push da imagem
                        bat "docker push %DOCKER_IMAGE%:%DOCKER_TAG%"
                    }
                }
            }
        }

        // --------------------------------------------------
        // ESTÁGIO 3: STAGING (Deploy)
        // Equivalente ao seu job "Pipeline Staging" [cite: 406]
        // --------------------------------------------------
        stage('Staging: Deploy') {
            steps {
                echo 'Iniciando Deploy em Staging...'
                
                // Garante que pegamos a imagem mais nova [cite: 419]
                bat 'docker-compose -f docker-compose.staging.yml pull'
                
                // Sobe o container recriando se necessário [cite: 421]
                bat 'docker-compose -f docker-compose.staging.yml up -d --force-recreate'
                
                echo 'Aguardando aplicação subir (60s)...'
                sleep(time: 60, unit: 'SECONDS') // [cite: 423]

                echo 'Verificando saúde da aplicação...'
                // Teste simples de curl como no seu script [cite: 430]
                // Se falhar, o pipeline falha
                bat 'curl -f http://localhost:8686 || exit 1'
            }
        }
    }

    post {
        always {
            cleanWs() // Limpa o workspace para economizar espaço
        }
        success {
            echo 'Pipeline DevOps finalizado com sucesso!'
        }
        failure {
            echo 'Falha no Pipeline. Verifique os logs.'
        }
    }
}