Scrapping data from  https://www.thehindu.com/archive/
The process fetches all the articles from 
 https://www.thehindu.com/archive/web/{year}/{month}/{day}
 https://www.thehindu.com/archive/print/{year}/{month}/{day}
 and updates in database for later search operations.
 
 Please note that the process is <u><b>NOT</b></u> full fledged and hence it requires to manual removal of data from database if any failures.
 
 <u>The below are the steps to run the process:</u>
 1. Create tables. The same script is present in file <u>creation.sql</u> in resources folder.
 
 create table AUTHOR(
 AUTHOR_ID int auto_increment,
 AUTHOR_NAME varchar(100),
 primary key(AUTHOR_ID));
 
 create table ARTICLE(
 ARTICLE_ID int auto_increment,
 AUTHOR_ID int references AUTHOR.AUTHOR_ID,
 ARTICLE_NAME varchar(1000),
 ARTICLE_DESC varchar(1000),
 primary key(ARTICLE_ID));
 
 2. Run com.techdisqus.process.TheHinduWebScrapper
 3. The process would take a while to complete as it has to scan for all the articles ranging from 01-Jan-2000 to till date
 
 
 In case of process failure, before retrying:
 1. delete from Article
 2. delete from author
 3. Repeat the steps above
 
 Running Rest API and making calls:
 All the API's are GET calls
 Follow the below steps:
 1. Start com.techdisqus.App
 2. To List all the Authors:
    a. curl "http://localhost:8080/authors/all"
 3. Partial search on authors
    a. curl "http://localhost:8080/authors/name/I"
 4. List all the article by author name
    a. curl "http://localhost:8080/author/articles/name/NIKHIL%20VARMA"
 5. List all the article by author ID
    a. curl "http://localhost:8080/author/articles/id/4"
 6. Search articles by title
    a. curl "http://localhost:8080/articles/title/best/"
 7. Search articles by description
    a. curl "http://localhost:8080/articles/desc/anjan/"
 8. Search articles by title and description
    a. curl "http://localhost:8080/best/anjan"
    
 
 
 
 