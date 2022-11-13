echo =======================
echo 自动化部署脚本启动
echo =======================

echo 停止原来运行中的工程

APP_NAME=ruiji
tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
if [ ${tpid} ];then
  echo  'stop process ....'
  kill -15 $tpid
fi

sleep 2
tpid=`ps -ef|grep $APP_NAME|grep -v grep|grep -v kill|awk '{print $2}'`
if [ ${tpid} ]; then
  echo
  kill -9 $tpid
  else
    echo 'stop Sucess!'
fi

echo 准备从git仓库拉去最新代码
cd /usr/local/app/ruiji

echo 开始从git 仓库拉去最新代码
git pull
echo  码拉去完成

echo 开始打包
 output=`mvn clean package -Dmaven.test.skip=true`

 cd tartget

 echo 启动项目

 nohub java -jar saller_ruiji-1.0-SNAPSHOT.jar &> ruiji.log &

 echo 启动完成





