# Core_java grep

## Introduction
The Java Grep Application is an application that mimics the Linux grep command. This application takes in three inputs and outputs the line that matches a text pattern found in directories to a file. Two distinct implementations of reading and writing files are included in this application, one utilizing Lambda and Stream APIs and the other using BuffererReader and FileOutputStream. For this project, Maven was used to manage the build lifecycle and download dependencies. Docker was then used to deploy the grep app, making it easy to distribute and use.

## Quick Start
The application takes three arguments:
* `regex_pattern`: the regex pattern to be searched
* `rootPath`: the root directory path to be searched
* `outFile`: the output file name

1. Launch a JVM and run the app
```
mvn clean compile package 

#Approach 1: Classpath and class files
java -classpath target/classes ca.jrvs.apps.grep.JavaGrepImp .*Romeo.*Juliet.* ./data /out/grep.txt
#Approach 2: Jar file
java -cp target/grep-1.0-SNAPSHOT.jar ca.jrvs.apps.grep.JavaGrepImp .*Romeo.*Juliet.* ./data ./out/grep.txt
```

2. Run with Docker
```
mvn clean package
#build a new docker image locally
docker build -t ${docker_user}/grep .

#run docker container 
docker run --rm \
-v `pwd`/data:/data -v `pwd`/log:/log \
${docker_user}/grep .*Romeo.*Juliet.* /data /log/grep.out
```

## Implemenation
### Pseudocode
The void process() method, which is the heart of this application, calls numerous helper functions as seen in the following pseudocode:
```
for file in listFilesRecursively(rootDirPath)
  for line in readLines(file)
      if containsPattern(line)
        matchedLines.add(line)
writeToFile(matchedLines)
```

### Performance Issue

A limited amount of memory is allotted to the JVM when it is launched. This application will run out of memory and cause an OutOfMemoryError if the heap size is less than the size of the rootDirectory. This problem can be resolved by processing data using the Stream APIs instead of BufferedReader and Lists. Memory can be conserved in bigger sizes because streams don't store data and let elements be computed as needed.
Using the -Xmx option to increase the maximum heap size is also an option to solve the memory problem.

## Test
A single file containing the entire text of William Shakespeare's play was used to test the application. The test data was manually entered using various combinations of root directory paths, out filenames, and regex string patterns on the command line. The Linux grep command is then used to compare the output file to the output.

## Deployment
This app was dockerized for easier distribution.
The Maven build manager was used to initially package the Java Grep Application into a jar file. And then a docker image was built by implementing a DockerFile. 
Finally, the docker image was uploaded to the Docker hub for easier access.

## Improvement
* Provide additional options that are built in the grep command, such as -n to display the line number, -i for case insensitive matching
* Give users the option of displaying the output to the terminal instead of writing it to a file
* Give users the option to exclude specific files or directories
