all:
	javac -cp . ./cs475/Classify.java -d ../out/
clean:
	rm -f ../predictions/* ../models/*
