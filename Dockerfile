# base image
FROM openjdk:11
#設定jar file 參數，目前在project根目錄
ARG JAR_FILE=target/*.jar
# 設定工作資料夾，並切換過去
WORKDIR /app
#當前目錄在/app，把jar檔複製到/app/app.jar，故'.' 表示/app
COPY ${JAR_FILE} ./app.jar
# copy ssh key to /app/file/sshkey/
COPY target/classes/sshKey/* ./file/sshKey/
#此Container 開放8081 port
EXPOSE 8081
#執行 java -jar /app/app.jar
CMD ["java","-jar","./app.jar"]

