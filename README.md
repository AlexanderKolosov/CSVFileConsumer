Information aboute the application CSVFileConsumer:

CSV files consuming application destination is to retrieve information from CSV files, to verify its state, to insert good data
to SQLite data base and badData to .csv file.

Here is user interface. It helps to user to make all necessary operations:
  1. In text field should be iserted full path to data base or could be used default value. Anyway, should be verifyed correct name of 
  memory tom. If default value d`not used the extension of data base name should be .db.
  2. After database absolute path is insereted button "Create database" should be pushed.
  3. Button "Select CSV file" well open file chooser. There should be selected .csv file.
  4. After data base is created and .csv file is selected appears button "START OPERATION".
  5. After button "START OPERATION" is pushed table name should be inserted. PLEASE, use letters only in the name of the table. In this 
  table good data will be inserted.
  6. After inserting apropriate table name application will start process.
  7. Wen process will be finished, application will insert all good valies to data base, all bad values to coresponding .csv file 
  and log with report to .log file. Also, report should be displayd. And in the notification message will be absolute paths to
  these files.
  8. After all application is ready to continue or it should be closed.
  
To use the application you need to have installed in the system Oracle Java platform
(Download page - https://www.java.com/ru/download/).

Previously verify if it`s already installed. Open console (Win + R and input 'cmd' command) and insert command 'java -version'.
If you see response with java version it`s already installed in system. Then, just copy folder "target" with all content from the
repository to your computer. Find there file net.optimalsolutionshub.csvfileconsumer-1.0-SNAPSHOT.jar, push right mouse button and
select 
"Open with -> Java(TM) Platform SE(EE) binary".

Or after downloading open console (Win + R and input 'cmd' command). Go to the folder 'target' and insert command
'java -jar net.optimalsolutionshub.csvfileconsumer-1.0-SNAPSHOT.jar'.
