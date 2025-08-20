# ---------- Build stage ----------
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Copy pom first for dependency caching
COPY pom.xml .
RUN mvn -B -q -DskipTests dependency:go-offline

# Copy the rest and build
COPY . .
RUN mvn -B -q -DskipTests clean package

# ---------- Run stage ----------
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar

# Render sets PORT (defaults to 10000); keep 8080 as local fallback
ENV PORT=8080
EXPOSE 8080
ENTRYPOINT ["java","-jar","app.jar"]
