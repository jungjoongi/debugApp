echo "debugApp test"

cd /var/lib/jenkins/workspace/debugApp/debugApp

echo "chmod +x gradlew"
chmod +x gradlew

echo "dos2unix gradlew"
dos2unix gradlew

echo "./gradlew test"
./gradlew test


