from command line run 
mvn exec:java

if MelektroToolsMvn changed, copy files to pi and then
mvn install:install-file -DgroupId=com.melektro -DartifactId=melektrotoolsmvn -Dversion=1.8 -Dpackaging=jar -Dfile=../melektrotoolsmvn/target/melektrotoolsmvn-1.8.jar

make sure that sudo nano /etc/maven/settings.xml has the <localRepository>/home/pi/.m2/repository</localRepository> setup

on windows:
mvn install:install-file -DgroupId=com.melektro -DartifactId=MelektroToolsMvn -Dversion=1.8 -Dpackaging=jar -Dfile=c:\Users\mariu\Documents\NetBeansProjects\melektrotoolsmvn\target\melektrotoolsmvn-1.8.jar

note: this was added to the pom to expose the main method:
    <build>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>exec-maven-plugin</artifactId>
                <executions>
                    <execution>
                        <goals>
                            <goal>java</goal>
                        </goals>
                    </execution>
                </executions>
                <configuration>
                    <mainClass>com.melektro.MelektroApi.MelektroApiApplication</mainClass>
                    <!--arguments>
                        <argument>foo</argument>
                        <argument>bar</argument>
                    </arguments-->
                </configuration>
            </plugin>        
        </plugins>
    </build>


Run on port 80:
  public class MelektroApiApplication {

	public static void main(String[] args) {
            SpringApplication app = new SpringApplication(MelektroApiApplication.class);
            app.setDefaultProperties(Collections.singletonMap("server.port", "80"));
            app.run(args);
		//SpringApplication.run(MelektroApiApplication.class, args);
	}
	
sudo systemctl stop tomcat8
sudo systemctl start tomcat8
	
gives swagger.json
http://pastei05.local/MelektroApi/
http://localhost:8080/MelektroApi/
http://localhost/MelektroApi/

gives swagger ui
http://localhost:8080/MelektroApi/swagger-ui.html
http://localhost/MelektroApi/swagger-ui.html
http://pastei05.local/MelektroApi/swagger-ui.html

Gives ok test:
http://localhost:8080/MelektroApi/greeting?name=World
http://localhost:/MelektroApi/greeting?name=World

TODO: Change to WAR
      GetIss timestamp to datetime  


Create a new folder, "local-maven-repo"

Add a local repo inside pom.xml:

<repositories>
    <repository>
        <id>local-maven-repo</id>
        <url>file:///${project.basedir}/local-maven-repo</url>
    </repository>
</repositories>

mvn deploy:deploy-file -DgroupId=com.melektro.Tools -DartifactId=MelektroTools -Dversion=1.0 -Durl=file:./local-maven-repo/ -DrepositoryId=local-maven-repo -DupdateReleaseInfo=true -Dfile=c:\Users\mariu\Documents\NetBeansProjects\melektroTools\dist\melektroTools.jar



application.properties
server.contextPath=/
springfox.documentation.swagger.v2.path=/

add
    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
            webServerFactoryCustomizer() {
        return factory -> factory.setContextPath("/MelektroApi");
    }
