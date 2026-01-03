 Leitor-de-pdf

Welcome! I created this project to read my books, and I'm sharing it here so you can use it too. This is a Java-based PDF reader with a graphical interface.
 How to Build and Run

If the pre-built download doesn't work or if you are on Windows, you can build the application manually from the source code.
Prerequisites

    Java JDK (17 or higher recommended)

    Maven * Arch Linux: sudo pacman -S maven

        Windows: Download from maven.apache.org, extract it, and add the bin folder to your System Environment Variables (PATH).

 Build Instructions

    Clone the repository:
    Bash

git clone https://github.com/seu-usuario/Leitor-de-pdf.git
cd Leitor-de-pdf

Clean and Package (Generate the JAR):
Bash

mvn clean package

Generate the Native Executable (App Image): Run the following command to create a folder containing the executable and all necessary runtime files:
Bash

    jpackage --type app-image \
      --name "LeitorPDF-Igor" \
      --input target/ \
      --main-jar LeitorPdfwithGUi2-0-1.0-SNAPSHOT.jar \
      --main-class com.example.leitorpdfwithgui20.Launcher

Where is my app?

After running the jpackage command:

    Linux: A folder named LeitorPDF-Igor will be created. Go to bin/ inside it and run the file LeitorPDF-Igor.

    Windows: Look for the LeitorPDF-Igor folder in your project directory and run the .exe file inside.

 Common Issues

    Maven not found: Ensure Maven is correctly installed. On Windows, verify your MAVEN_HOME and PATH variables.

    Old Builds: If you want to start fresh, run rm -rf LeitorPDF-Igor (Linux) or delete the folder manually (Windows) before running the jpackage command again.




   Tecnologias Utilizadas

Este projeto foi desenvolvido utilizando as seguintes ferramentas e bibliotecas:

    Java: Linguagem principal do projeto.

    JavaFX: Para a criação da interface gráfica (GUI).

    Maven: Gestor de dependências e automação de build.

    JPackage: Ferramenta do JDK para criar imagens nativas do executável.

    Apache PDFBox (opcional): Se usaste esta biblioteca para ler o conteúdo dos PDFs, podes mencioná-la aqui.

Créditos e Contribuição

Projeto criado por Igor.

Se quiseres contribuir para este leitor de PDF:

    Faz um Fork do projeto.

    Cria uma Branch com a tua nova funcionalidade (git checkout -b feature/NovaFuncionalidade).

    Faz um Commit das tuas alterações (git commit -m 'Adicionei X funcionalidade').

    Faz um Push para a tua Branch (git push origin feature/NovaFuncionalidade).

    Abre um Pull Request.


<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>LeitorPdfwithGUi2-0</artifactId>
    <version>1.0-SNAPSHOT</version>
    <name>LeitorPdfwithGUi2.0</name>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <junit.version>5.12.1</junit.version>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-controls</artifactId>
            <version>21.0.6</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-fxml</artifactId>
            <version>21.0.6</version>
        </dependency>
        <dependency>
            <groupId>org.openjfx</groupId>
            <artifactId>javafx-swing</artifactId>
            <version>21</version>
        </dependency>
        <dependency>
            <groupId>org.controlsfx</groupId>
            <artifactId>controlsfx</artifactId>
            <version>11.2.1</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-api</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
        <dependency>
            <groupId>org.apache.pdfbox</groupId>
            <artifactId>pdfbox-app</artifactId>
            <version>3.0.6</version>
        </dependency>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter-engine</artifactId>
            <version>${junit.version}</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.13.0</version>
                <configuration>
                    <source>21</source>
                    <target>21</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.5.1</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <transformers>
                                <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>com.example.leitorpdfwithgui20.Launcher</mainClass>
                                </transformer>
                            </transformers>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
            <plugin>
                <groupId>org.openjfx</groupId>
                <artifactId>javafx-maven-plugin</artifactId>
                <version>0.0.8</version>
                <executions>
                    <execution>
                        <!-- Default configuration for running with: mvn clean javafx:run -->
                        <id>default-cli</id>
                        <configuration>
                            <mainClass>com.example.leitorpdfwithgui20/com.example.leitorpdfwithgui20.Application
                            </mainClass>
                            <launcher>app</launcher>
                            <jlinkZipName>app</jlinkZipName>
                            <jlinkImageName>app</jlinkImageName>
                            <noManPages>true</noManPages>
                            <stripDebug>true</stripDebug>
                            <noHeaderFiles>true</noHeaderFiles>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>
    </build>
</project>
