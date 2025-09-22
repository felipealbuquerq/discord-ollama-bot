# Use uma imagem base mais leve para a fase de construção
FROM openjdk:17-jdk-slim AS builder

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos de build do Gradle
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Copia o código-fonte
COPY src src

# Cria o JAR executável
RUN ./gradlew clean build

# --- Segunda etapa: Criação da imagem final de produção ---
# Usa uma imagem base leve para a execução
FROM openjdk:17-jre-slim

# Define o fuso horário
ENV TZ=UTC

# Define o diretório de trabalho
WORKDIR /app

# Copia o JAR do estágio de construção
COPY --from=builder /app/build/libs/discord-ollama-bot-0.0.1-SNAPSHOT.jar /app/discord-ollama-bot.jar

# Expõe a porta
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app/discord-ollama-bot.jar"]