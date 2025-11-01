# Etapa 1: build da aplicação
FROM gradle:8.5-jdk17-alpine AS build
WORKDIR /app
COPY . .
RUN gradle clean bootJar --no-daemon

# Etapa 2: imagem final
FROM eclipse-temurin:17-jre
WORKDIR /app

# copia o jar gerado na etapa de build
COPY --from=build /app/build/libs/*.jar app.jar

# porta padrão da sua API (se for outra, troca aqui)
EXPOSE 8080

# sobe a aplicação
ENTRYPOINT ["java","-jar","/app/app.jar"]
