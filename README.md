## Run program:

java -jar SmartXmlAnalyzer.jar <input_origin_file_path> <input_other_sample_file_path>

java -jar SmartXmlAnalyzer.jar src/main/resources/sample-0-origin.html src/main/resources/sample-1-evil-gemini.html

java -jar SmartXmlAnalyzer.jar <input_origin_file_path> <input_other_sample_file_path> <target_element_id>

java -jar SmartXmlAnalyzer.jar src/main/resources/sample-0-origin.html src/main/resources/sample-1-evil-gemini.html make-everything-ok-button

## Simple output:

sample-1-evil-gemini.html:

html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[1] > a[1]

sample-2-container-and-clone.html:

html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[1] > div[0] > a[0]

sample-3-the-escape.html:

html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[2] > a[0]

sample-4-the-mash.html:

html[0] > body[1] > div[0] > div[1] > div[2] > div[0] > div[0] > div[2] > a[0]

