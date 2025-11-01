# ============================
# ETAPA 1: build da aplicação
# ============================
FROM gradle:8.5-jdk17-alpine AS build
WORKDIR /app

# copia tudo do projeto
COPY . .

# gera o jar, mas IGNORA testes
RUN gradle clean bootJar -x test --no-daemon

# ============================
# ETAPA 2: imagem final
# ============================
FROM eclipse-temurin:17-jre
WORKDIR /app

# copia o jar gerado da etapa de build
# normalmente o bootJar fica em build/libs/*.jar
COPY --from=build /app/build/libs/*.jar app.jar

# porta padrão da aplicação
EXPOSE 8080

# comando de execução
ENTRYPOINT ["java","-jar","/app/app.jar"]
