if not exist bin mkdir bin
javac -d bin/ -sourcepath src/ src/led/Main.java
java -cp bin/ led.Main