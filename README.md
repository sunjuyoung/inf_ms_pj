## microservice demo application

{





}



## Apache Kafka

#### windows mode

### kafka_2.8.1
https://kafka.apache.org/downloads


Zookeeper 및 Kafka 서버 기동

./bin/windows/zookeeper-server-start.bat ./config/zookeeper.properties

./bin/windows/kafka-server-start.bat ./config/server.properties



Topic 생성
./bin/windows/kafka-topics.bat --create --topic [토픽이름] --bootstrap-server localhost:9092 --partitions 1

Topic 목록 확인
./bin/windows/kafka-topics.bat --bootstrap-server localhost:9092 --list

Topic 정보 확인
./bin/windows/kafka-topics.bat --bootstrap-server localhost:9092  --describe --topic [토픽이름]


### Producer/Consumer TEST

메시지 생산
/bin/windows/kafka-console-producer.bat --broker-list localhost:9092 --topic [토픽이름]


메시지 소비
/bin/windows/kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic [토픽이름] --from-beginning



### kafka connect 
코드 없이 Configuration으로 Data import/export 가능
RESTful API 지원
Stream, Batch 형태로 데이터 전송 가능
S3,Mysql, .... 데이터 연동 plugin 제공


https://mariadb.org/download
mariadb



### confluent-7.0.1(kafka connect)
https://www.confluent.io/

### JDBC Connector (Source and Sink)
https://www.confluent.io/hub/confluentinc/kafka-connect-jdbc

confluent-7.0.1/etc/kafka/connect-distributed.properties 파일 마지막에 아래 plugin 정보 추가
- plugin.path=[confluentinc-kafka-connect-jdbc-10.0.1 폴더]
ex) plugin.path=\C:\\kafka\\connect-jdbc-10.2.6\\lib

JdbcSourceConnector에서 MariaDB 사용하기 위해 mariadb 드라이버 복사
confluent-7.0.1/share/java/kafka/ 폴더에 mariadb-java-client-2.7.3.jar 파일 복사
(jar파일은 .m2 에서 copy)



Kafka Connect 실행 (confluent-7.0.1)
./bin/windows/connect-distributed ./etc/kafka/connect-distributed.properties


Classpath is empty. Please build the project first e.g. by running ‘gradlew jarAll’ 오류 발생시
\bin\windows\kafka-run-class.bat 파일에서 
rem Classpath addition for core 부분을 찾아서, 그 위에 아래 코드 삽입
```
rem Classpath addition for LSB style path
if exist %BASE_DIR%\share\java\kafka\* (
	call :concat %BASE_DIR%\share\java\kafka\*
)
```

log4j 오류 발생....

\bin\windows\connect-distributed.bat 파일에서 경로를 수정해줌 
/config/connect-log4j.properties  -> /etc/kafka/connect-log4j.properties
```
rem Log4j settings
IF ["%KAFKA_LOG4J_OPTS%"] EQU [""] (
	set KAFKA_LOG4J_OPTS=-Dlog4j.configuration=file:%BASE_DIR%/etc/kafka/connect-log4j.properties
)
```