FROM tomcat:8.0

ADD ./web/target/*.war /usr/local/tomcat/webapp

EXPOSE 8080

CMD [ "catalina.sh","run" ]