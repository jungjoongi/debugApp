echo "debugApp deploy"
echo "=============="

echo 'cp /var/lib/jenkins/workspace/debugApp/debugApp/build/libs/debugapp.jar /home/pi/server/application/debugapp'
cp /var/lib/jenkins/workspace/debugApp/debugApp/build/libs/debugapp.jar /home/pi/server/application/debugapp

echo 'sh /home/pi/server/application/debugapp/start.sh'
sh /home/pi/server/application/debugapp/start.sh