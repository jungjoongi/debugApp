echo "debugApp build"
echo "=============="

BASE_PATH=/home/pi/server/deploy
APP_NAME=debugApp
BUILD_PATH=/home/pi/server/build/debugApp
JAR_NAME=$BUILD_PATH$debugApp.jar


echo "chmod +x gradlew"
chmod +x gradlew

echo "dos2unix gradlew"
dos2unix gradlew

echo "./gradlew clean build"
./gradlew clean build


