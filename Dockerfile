# ============================
# ETAPA 1: build da aplicação
# ============================
FROM eclipse-temurin:17-jdk AS build
WORKDIR /app

# copia só o que o Gradle precisa primeiro (otimiza cache)
COPY gradlew ./
COPY gradle ./gradle
COPY build.gradle.kts settings.gradle.kts ./

# baixa as dependências (isso já falha se o projeto estiver quebrado)
RUN chmod +x ./gradlew
RUN ./gradlew --version

# agora copia o restante do código
COPY src ./src

# gera o jar SEM testes
RUN ./gradlew clean bootJar -x test --no-daemon

# ============================
# ETAPA 2: imagem final, leve
# ============================
FROM eclipse-temurin:17-jre
WORKDIR /app

# copia o jar gerado na etapa de build
COPY --from=build /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","/app/app.jar"]
