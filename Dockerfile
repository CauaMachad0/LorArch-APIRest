# ============================
# ETAPA 1: build da aplicação (com JDK 21)
# ============================
FROM eclipse-temurin:21-jdk AS build
WORKDIR /app

# copia primeiro os arquivos de build
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts ./

# garante permissão no wrapper
RUN chmod +x ./gradlew

# baixa dependências básicas e testa o gradle
RUN ./gradlew --version

# agora copia o código-fonte
COPY src ./src

# gera o JAR SEM rodar teste
RUN ./gradlew clean bootJar -x test --no-daemon

# ============================
# ETAPA 2: imagem final (JRE 21)
# ============================
FROM eclipse-temurin:21-jre
WORKDIR /app

# copia o jar gerado na etapa 1
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
