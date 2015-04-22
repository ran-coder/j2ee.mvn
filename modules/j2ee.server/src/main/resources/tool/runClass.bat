cd ../../../../
setlocal
set JAVA_HOME=D:\Server\jdk\jdk1.7.0
echo %JAVA_HOME%

rem clean compile
mvn exec:java -Dexec.mainClass="j2ee.server.jmemcached.JmemcachedMain" -Dmaven.test.skip=true 
rem mvn org.codehaus.mojo:exec-maven-plugin:1.1.1:java -Dexec.mainClass=com.vineetmanohar.module.Main