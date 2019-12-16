#！/bin/bash
port=$(netstat -nlp |grep 8088 | awk '{print $7}' | cut -d "/" -f1)

kill -9 $port

nohup java -jar /home/priceCalculateManagement/PriceCalculateManagement-0.0.1-SNAPSHOT.jar  &  tail -f nohup.out
