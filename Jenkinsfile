pipeline {
    agent any 

    environment {
        DOCKER_IMAGE = 'leofonsecaa/ac2_ca'
        DOCKER_TAG = 'latest'
        // ATENÇÃO: Garanta que o ID da credencial no Jenkins é 'docker_jenkins'
        // Se for outro, mude apenas o valor entre aspas abaixo.
        DOCKER_CRED_ID = 'docker_jenkins'
    }

    stages {
        // --------------------------------------------------
        // ESTÁGIO 1: DEV (Compilação e Testes)
        // --------------------------------------------------
        stage('DEV: Build & Test') {
            steps {
                echo 'Iniciando Build e Testes Unitários...'
                bat 'mvn clean install'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                    recordIssues(tools: [pmdParser(pattern: '**/pmd.xml')])
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
        // --------------------------------------------------
        stage('Docker: Build & Push') {
            steps {
                script {
                    echo 'Gerando pacote JAR final...'
                    bat 'mvn clean package -Dmaven.test.skip=true'

                    echo 'Construindo imagem Docker...'
                    bat "docker build -t %DOCKER_IMAGE%:%DOCKER_TAG% ."

                    echo 'Login e Push para o Docker Hub...'
                    
                    // AQUI ESTÁ A CORREÇÃO DO NOME DA VARIÁVEL:
                    // O withCredentials cria as variáveis de ambiente seguras (DOCKER_USER e DOCKER_PASS)
                    // Isso substitui o "Secret Text" que você usava no Freestyle
                    withCredentials([usernamePassword(credentialsId: "${DOCKER_CRED_ID}", passwordVariable: 'DOCKER_PASS', usernameVariable: 'DOCKER_USER')]) {
                        
                        // Workaround: Login manual via linha de comando usando as variáveis injetadas
                        bat 'docker login -u %DOCKER_USER% -p %DOCKER_PASS%'
                        
                        // Push manual
                        bat "docker push %DOCKER_IMAGE%:%DOCKER_TAG%"
                    }
                }
            }
        }

        // --------------------------------------------------
        // ESTÁGIO 3: STAGING (Deploy)
        // --------------------------------------------------
        stage('Staging: Deploy') {
            steps {
                echo 'Iniciando Deploy em Staging...'
                bat 'docker-compose -f docker-compose.staging.yml pull'
                bat 'docker-compose -f docker-compose.staging.yml up -d --force-recreate'
                
                echo 'Aguardando aplicação subir (60s)...'
                sleep(time: 60, unit: 'SECONDS')

                echo 'Verificando saúde da aplicação...'
                bat 'curl -f http://localhost:8686 || exit 1'
            }
        }
    }

    post {
        always {
            cleanWs()
        }
        success {
            echo 'Pipeline DevOps finalizado com sucesso!'
        }
        failure {
            echo 'Falha no Pipeline. Verifique os logs.'
        }
    }
}