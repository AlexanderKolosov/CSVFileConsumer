Information about the application CSVFileConsumer:

CSV files consuming application destination is to retrieve information from CSV files, to verify the data state then insert good data
to SQLite database and bad data to .csv file.

User interface is present. It help users to make all necessary operations:
  1. In text field should be inputted full path to data base or could be used default value. Anyway, should be verified correct disc name.
  If default value don`t used the extension of database name should be .db.
  2. After database absolute path is inputted button "Create database" should be pushed.
  3. Button "Select CSV file" well open file chooser. There should be selected .csv file.
  4. After database is created and .csv file is selected appears button "START OPERATION".
  5. After button "START OPERATION" is pushed table name should be inputted. PLEASE, use letters only in the name of the table. In this 
  table good data will be inputted.
  6. After inserting appropriate table name application will start process.
  7. When process will be finished, application will insert all good values to database, all bad values to corresponding .csv file 
  and log with report to .log file. Also, report should be displayed. And in the notification message will be shown absolute paths to
  these files.
  8. After all application is ready to continue or it should be closed.
  
To use the application you need to have installed in the system Oracle Java platform
(Download page - https://www.java.com/ru/download/).

Previously verify if it`s already installed. Open console (Win + R and input 'cmd' command) and input command 'java -version'.
If you see response with java version it`s already installed in system. Then, just copy folder "target" with all content from the
repository to your computer. Find there file net.optimalsolutionshub.csvfileconsumer-1.0-SNAPSHOT.jar, push right mouse button and
select 
"Open with -> Java(TM) Platform SE(EE) binary".

Or after downloading open console (Win + R and input 'cmd' command). Go to the folder 'target' and input command
'java -jar net.optimalsolutionshub.csvfileconsumer-1.0-SNAPSHOT.jar'.
