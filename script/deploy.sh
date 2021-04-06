echo "debugApp deploy"
echo "=============="

If [ ! -d /home/pi/server/application/debugapp ] ; then
  echo '/home/pi/server/application/debugapp 디렉토리가 없습니다. 디렉토리를 생성합니다.'
  echo 'mkdir /home/pi/server/application/debugapp'
  mkdir /home/pi/server/application/debugapp
fI

echo 'cp /var/lib/jenkins/workspace/debugApp/debugApp/build/libs/debugapp.jar /home/pi/server/application/debugapp'
cp /var/lib/jenkins/workspace/debugApp/debugApp/build/libs/debugapp.jar /home/pi/server/application/debugapp
cp /var/lib/jenkins/workspace/debugApp/debugApp/src/main/resources/env/prd01/application-prd01.yml /home/pi/server/application/debugapp
cp /var/lib/jenkins/workspace/debugApp/debugApp/src/main/resources/env/prd02/application-prd02.yml /home/pi/server/application/debugapp

echo 'sh /home/pi/server/application/debugapp/start.sh'
sh /home/pi/server/application/debugapp/start.sh