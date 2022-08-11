package ca.jrvs.apps.practice;

import ca.jrvs.apps.grep.JavaGrepImp;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JavaGrepLambdaImp extends JavaGrepImp {
    public static void main(String[] args) {
        if (args.length != 3){
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }
            //creating JavaGrepLambdaImp instead of JavaGrepImp
            //JavaGrepLambdaImp inherits all methods except two override methods
        JavaGrepLambdaImp javaGrepLambdaImp = new JavaGrepLambdaImp();
        javaGrepLambdaImp.setRegex(args[0]);
        javaGrepLambdaImp.setRootPath(args[1]);
        javaGrepLambdaImp.setOutFile(args[2]);

        try{
            //calling parent method
            //but it will call override method (in this class)
            javaGrepLambdaImp.process();;
        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
        
        /**
         * Implement using lambda and stream APIs
         */
        @Override
        public List<String> readLines (File inputFile){
            try (Stream<String> streamLine = Files.lines(Paths.get(String.valueOf(inputFile)))) {
                return streamLine.collect(Collectors.toList());
            } catch (Exception e) {
                return new ArrayList<>();
            }
        }
        
        /**
         * Implement using lambda and stream APIs
         */
        @Override
        public List<File> listFiles(String rootDir){
            List<File> files = new ArrayList<>();
            try (Stream<Path> stream = Files.walk(Paths.get(rootDir))){
                return stream
                        .filter(file -> !Files.isDirectory(file))
                        .map(Path::getFileName)
                        .map(Path::toFile)
                        .collect(Collectors.toList());
            }catch (Exception e){
                return files;
            }
        }
        
}
    
