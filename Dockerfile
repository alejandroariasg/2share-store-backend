# Usar una imagen base con JDK 17
FROM eclipse-temurin:17-jdk

# Crear directorio de trabajo
WORKDIR /app

# Copiar el wrapper y el proyecto
COPY . .

# Construir el proyecto (esto instala dependencias y compila)
RUN ./mvnw clean package -DskipTests

# Puerto que usará la app
EXPOSE 8080

# Comando para iniciar
CMD ["java", "-jar", "target/store-0.0.1-SNAPSHOT.jar"]
