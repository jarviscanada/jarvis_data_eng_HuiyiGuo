package ca.jrvs.apps.grep;

import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class JavaGrepImp implements JavaGrep{
    final Logger logger = LoggerFactory.getLogger(JavaGrepImp.class);

    public static void main(String[] args) {
        if (args.length != 3){
            throw new IllegalArgumentException("USAGE: JavaGrep regex rootPath outFile");
        }

        //Use default logger config
        BasicConfigurator.configure();

        JavaGrepImp javaGrepImp = new JavaGrepImp();
        javaGrepImp.setRegex(args[0]);
        javaGrepImp.setRootPath(args[1]);
        javaGrepImp.setOutFile(args[2]);

        try{
            javaGrepImp.process();
        }catch (Exception ex){
            javaGrepImp.logger.error("Error: Unable to process", ex);
        }

    }
    private String regex;
    private String rootPath;
    private String outFile;

    @Override
    public String getRegex() {
        return regex;
    }

    @Override
    public void setRegex(String regex) {
        this.regex = regex;
    }

    @Override
    public List<File> listFiles(String rootDir) {
        List<File> resultFile = new ArrayList<File>();

        File dir = new File(rootDir);
        File[] fileList = dir.listFiles();
        if (fileList != null){
            for (File file : fileList){
                if (file.isFile()){
                    resultFile.add(file);
                }else if (file.isDirectory()){
                    resultFile.addAll(listFiles(file.getAbsolutePath()));
                }

            }
        }
        return resultFile;
    }

    @Override
    public List<String> readLines(File inputFile) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))){
            String line;
            while ((line = br.readLine() )!= null){
                lines.add(line);
            }
            br.close();
        }catch (IllegalArgumentException | IOException e){
            logger.error("inputFile is DNE or not a file", e);
        }

        return lines;
    }

    @Override
    public boolean containsPattern(String line) {
        return Pattern.matches(getRegex(), line);

    }

    @Override
    public void writeToFile(List<String> lines) throws IOException {
        FileWriter fw = new FileWriter(getOutFile());
        BufferedWriter bw = new BufferedWriter(fw);
        for (String line: lines){
            bw.write(line);
            bw.newLine();
        }
        bw.close();
    }

    @Override
    public void process() throws IOException {
        List<String> lines = new ArrayList();
        for (File file: listFiles(getRootPath())){
            for (String readline: readLines(file)){
                if (containsPattern(readline)){
                    lines.add(readline);
                }
            }
        }
        if (lines.size() > 0){
            try{
                this.writeToFile(lines);
            }catch (Exception e){
                throw new IOException("Unable to write fi");
            }
        }else{
            logger.error("No matched line");
        }

    }

    @Override
    public String getRootPath() {
        return rootPath;
    }

    @Override
    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    @Override
    public String getOutFile() {
        return outFile;
    }

    @Override
    public void setOutFile(String outFile) {
        this.outFile = outFile;
    }


}
