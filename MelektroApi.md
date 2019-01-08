gives swagger.json
http://localhost:8080/MelektroApi/

gives swagger ui
http://localhost:8080/MelektroApi/swagger-ui.html

Gives ok test:
http://localhost:8080/MelektroApi/greeting?name=World

TODO: Change to WAR 


Create a new folder, "local-maven-repo"

Add a local repo inside pom.xml:

<repositories>
    <repository>
        <id>local-maven-repo</id>
        <url>file:///${project.basedir}/local-maven-repo</url>
    </repository>
</repositories>

mvn deploy:deploy-file -DgroupId=com.melektro.Tools -DartifactId=MelektroTools -Dversion=1.0 -Durl=file:./local-maven-repo/ -DrepositoryId=local-maven-repo -DupdateReleaseInfo=true -Dfile=c:\Users\marius\Documents\NetBeansProjects\melektroTools\dist\melektroTools.jar



application.properties
server.contextPath=/
springfox.documentation.swagger.v2.path=/

add
    @Bean
    public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>
            webServerFactoryCustomizer() {
        return factory -> factory.setContextPath("/MelektroApi");
    }
