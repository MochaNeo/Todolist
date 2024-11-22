# ベースイメージとしてJava 17を使用
FROM eclipse-temurin:17-jdk-alpine

# 作業ディレクトリを設定
WORKDIR /app

# Mavenをインストール
RUN apk add --no-cache maven

# pom.xmlとソースコードをコピー
COPY .mvn/ .mvn/
COPY mvnw pom.xml ./
COPY src ./src/

# Mavenでビルド
RUN ./mvnw package -DskipTests

# アプリケーションの実行
EXPOSE 8080
CMD ["java", "-jar", "target/todo-0.0.1-SNAPSHOT.jar"]