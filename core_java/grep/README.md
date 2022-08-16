# Core_java grep

## Introduction


## Quick Start


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


## Test


## Deployment



## Improvement
* Provide additional options that are built in the grep command, such as -n to display the line number, -i for case insensitive matching
* Give users the option of displaying the output to the terminal instead of writing it to a file
* Give users the option to exclude specific files or directories
