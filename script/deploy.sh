echo "debugApp deploy"
echo "=============="

echo 'cp /var/lib/jenkins/workspace/debugApp/debugApp/build/libs/debugapp.jar /home/pi/server/application/debugapp'
cp /var/lib/jenkins/workspace/debugApp/debugApp/build/libs/debugapp.jar /home/pi/server/application/debugapp
cp /var/lib/jenkins/workspace/debugApp/debugApp/src/main/resources/env/prd01/application-prd01.yml /home/pi/server/application/debugapp
cp /var/lib/jenkins/workspace/debugApp/debugApp/src/main/resources/env/prd02/application-prd02.yml /home/pi/server/application/debugapp

echo 'sh /home/pi/server/application/debugapp/start.sh'
sh /home/pi/server/application/debugapp/start.sh