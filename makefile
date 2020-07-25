TIME=15

run: breaktime.jar
	java -jar breaktime.jar -v
	java -jar breaktime.jar -h
	java -jar breaktime.jar ${TIME} min 
