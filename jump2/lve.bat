if not exist bin mkdir bin
javac -d bin/ -sourcepath src/ src/lve/Main.java
java -cp bin/ lve.Main 