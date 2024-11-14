if not exist bin mkdir bin
javac -d bin/ -sourcepath src/ src/Main.java
java -cp bin/ Main