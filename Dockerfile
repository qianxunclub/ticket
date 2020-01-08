FROM centos:7
RUN yum install -y git vim wget curl java-1.8.0-openjdk.x86_64 maven python36-setuptools python36-pip
RUN git clone https://github.com/qianxunclub/ticket.git
RUN cd ticket && mvn clean package
RUN cp ticket/target/ticket-0.0.1-SNAPSHOT.jar app.jar
RUN cp -R ticket/python python
RUN cd python && python3 -m venv venv
RUN cd python && source venv/bin/activate && pip3 install -r requirements.txt -i https://pypi.tuna.tsinghua.edu.cn/simple
VOLUME /tmp
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005","-Djava.security.egd=file:/dev/./urandom","-jar","app.jar"]
