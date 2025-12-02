FROM tomcat:9.0
COPY NDECS_Core.war /usr/local/tomcat/webapps/NDECS_Core.war
EXPOSE 8080
CMD ["catalina.sh", "run"]
