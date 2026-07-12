FROM ubuntu:24.04

RUN apt-get update && apt-get install -y openjdk-17-jdk openjfx && rm -rf /var/lib/apt/lists/*

WORKDIR /app

COPY . .

RUN mkdir -p out
RUN find src -name "*.java" > fuentes.txt
RUN javac -encoding UTF-8 -Xdiags:verbose --module-path /usr/share/openjfx/lib --add-modules javafx.controls -d out @fuentes.txt

CMD ["java", "--module-path", "/usr/share/openjfx/lib", "--add-modules", "javafx.controls", "-cp", "out", "Main"]
