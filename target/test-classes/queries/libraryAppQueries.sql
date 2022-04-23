-- us 01 query to get all columns

SELECT id
FROM users;

SELECT DISTINCT id
FROM users;

SELECT * FROM users;

-- us02 borrowed books number information must match with DB

SELECT *
FROM book_borrow;

SELECT COUNT(*)
FROM book_borrow
WHERE is_returned = 0;


-- us 03 -- query to find most popular book genre(category)

SELECT book_categories.name, COUNT(*) AS countofbookcategories
FROM book_borrow
         INNER JOIN books
             ON book_borrow.book_id = books.id
         INNER JOIN book_categories
             ON books.book_category_id = book_categories.id
GROUP BY book_categories.name
ORDER BY countofbookcategories DESC;


-- us 04 query to find most popular user

SELECT * FROM users;

SELECT  * FROM book_borrow;

SELECT full_name, COUNT(*) AS countofreadbooks
FROM users u
         INNER JOIN book_borrow bb ON u.id = bb.user_id
GROUP BY full_name
ORDER BY countofreadbooks DESC;







-- us05  query to get the book  "Chordeiles minor" information from books table
SELECT name, author, year
FROM books
WHERE name = 'Chordeiles minor';


-- us 06 query to get book categories
SELECT name
FROM book_categories;

