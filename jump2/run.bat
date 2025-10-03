if not exist bin mkdir bin
javac -d bin/ -sourcepath src/ src/jump2/Main.java
java -cp bin/ jump2.Main