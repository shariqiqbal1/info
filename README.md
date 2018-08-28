Approach:

Using BufferedReader to read source file as a stream line-by-line, so that files larger than memory can be read (input file: source.txt)

Splitting each line read into words (splitting at white-space), and removing speacial chars from words -- storing only alphanumeric values

Using a TreeSet to store words (since using a treeset will keep strings unique and sorted in asceding order).

There's a possibility that storing all unique words in a single TreeSet object can make it large enough to throw OutOfMemory exception, for this purpose we'll set a specific set-size/length and once the set has reached that size we'll flush the contents into a temp file and get a new set. We'll repeat this until the complete source file has been read.

Then we'll start reading the temp files word by word (readline) and write this data to the output file -- read 1st word from all temp files and store all those in a treemap with key as word and value and tempfile no., we use treemap because treemap will hold only unique and asc order keys, this will take care of handling non-unique matches in different temp files and update sorting as well. As the treemap data is passed to output file we delete that entry from treemap and add another line from the temp file whose data was deleted into the treemap. We do this till treemap is empty cos no new lines can be read from any tempfiles and passed into it.

the final output file (output.txt) is completed when all tempfile lines are read

at end we're deleting all tempfiles , also added timestamps in beginning and end of program to measure runtime

To run:

Put your source file source.txt at location C:\data\files (or update filepath and sourceFileName variable in the program with location to your file with its filename)
Run the main method in your Java env
output.txt will be the final output file, it'll be created at filepath location (with it's location/filename printed on console)
Note: if you're running with a smaller source file and set.size limit is not reached then job will be done by just creating one single sorted tempfile with non-dup values and that will be the output file, prog will exit there.
