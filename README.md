# Leitor-de-pdf
this project is for me read my books, then I created it repository to share  this project with you
case you was read this README and your dowload give erro or you is use windows you can try this comands:
give a git clone, after this inside project open terminal and enter this comands:

<optional> mvn clean package. , if you also try do a jar version.
<optional>rm -rf LeitorPDF-Igor, if you also try do a jar version.
mvn package.
jpackage --type app-image \
  --name "LeitorPDF-Igor" \
  --input target/ \
  --main-jar LeitorPdfwithGUi2-0-1.0-SNAPSHOT.jar \
  --main-class com.example.leitorpdfwithgui20.Launcher

  Caution: if you no dowload maven then dowload maven in the your system if not this comands gonna give you erros
  if you equal the me use arch, use "sudo pacman -S maven", otherwise if use windows use, go to site maven.apache.org/download.cgi. dowload the bin and configure your variables envarioment C:\Program Files\Apache\maven\apache-maven-3.8.4, normally when descompact package it gonna for this path get it and create your variable called MAVEN_HOME and put this path there.

