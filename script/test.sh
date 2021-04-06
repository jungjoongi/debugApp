echo "debugApp test"
echo "chmod +x gradlew"
chmod +x gradlew

echo "dos2unix gradlew"
dos2unix gradlew

echo "/var/lib/jenkins/workspace/debugApp/debugApp/gradlew test"
/var/lib/jenkins/workspace/debugApp/debugApp/gradlew test


