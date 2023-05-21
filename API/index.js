// Import and variables //
// Import
const express = require("express");
const mysql = require("mysql");

// Variables & prerequisites
const app = express();
app.use(express.json());
////app.use(cors());


// DB connection //
const connection = mysql.createConnection({
    host: 'localhost',
    user: 'root',
    password: 'password',
    database: 'stord_test_db'
});

connection.connect((err) => {
    if (err) {
      console.error('Error connecting to the database:', err);
      return;
    }
    console.log('Connected to the database');
});

//ROUTES//
//create a category
app.post('/api/create/category', (req, res) => {
    try {
        console.log(req.body);
    } catch (err) {
        console.log(err.message);
    }
});


//get all categories
app.get('/api/categories', (req, res) => {
    console.log(req);
    const sql = 'SELECT * FROM categories';

    connection.query(sql, (err, results) => {
        if (err) {
            console.error('Error executing query:', err);
            return;
        }
        
        console.log('Fetched users:', results);
    });
});

//get a category

//update a gategory

//delete a category

app.listen(5000, () => {
    console.log("Server has started on port 5000");
});