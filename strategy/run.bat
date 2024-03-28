if not exist bin mkdir bin
javac -d bin/ -sourcepath src/ src/strategy/Main.java
java -cp bin/ strategy.Main 