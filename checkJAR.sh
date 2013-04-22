#!/bin/sh
echo Generating list of files in JAR
jar -tf jmagic.jar | sort > filesInJAR.txt
echo Generating list of source files
find bin/* -type d -printf "%p/\n" -o -print | sed -e "s_^bin/__" | sort > files.txt
echo "<" are missing from workspace, ">" are missing from JAR
diff filesInJAR.txt files.txt
rm filesInJAR.txt files.txt
