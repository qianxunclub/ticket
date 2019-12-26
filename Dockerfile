FROM centos:7
RUN yum install -y vim wget curl java-1.8.0-openjdk.x86_64 python36-setuptools python36-pip
COPY python python
RUN cd python && rm -rf venv
RUN cd python && python3 -m venv venv
RUN cd python && source venv/bin/activate && pip3 install -r requirements.txt -i https://pypi.tuna.tsinghua.edu.cn/simple
VOLUME /tmp
ADD ticket-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5005 -Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
