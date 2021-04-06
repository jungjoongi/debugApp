echo "debugApp build"
echo "=============="

cd /var/lib/jenkins/workspace/debugApp/debugApp
cp /var/lib/jenkins/workspace/debugApp/debugApp/src/main/resources/env/prd01/application-prd01.yml /var/lib/jenkins/workspace/debugApp/debugApp/src/main/resources
cp /var/lib/jenkins/workspace/debugApp/debugApp/src/main/resources/env/prd02/application-prd02.yml /var/lib/jenkins/workspace/debugApp/debugApp/src/main/resources

echo './gradlew bootjar'
./gradlew bootjar