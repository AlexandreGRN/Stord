// Import and variables //
// Import
const express = require("express");
const mysql = require("mysql");
const cors = require("cors");

// Variables & prerequisites
const app = express();
app.use(express.json());
app.use(cors());


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
// Status
app.get('/api/status', async (req, res) => {
    console.log("\{\"Status\": OK}");
});

//create a category
app.post('/api/create/category', async (req, res) => {
    jsonCategory = JSON.parse(JSON.stringify(req.body))

    if (jsonCategory.name){
        var name = jsonCategory.name
    } else {
        var name = "NULL"
    }
    if (jsonCategory.description){
        var description = jsonCategory.description
    } else {
        var description = "NULL"
    }
    if (jsonCategory.imageURL){
        var imageURL = jsonCategory.imageURL
    } else {
        var imageURL = "NULL"
    }
    if (jsonCategory.favorite){
        var favorite = jsonCategory.favorite
    } else {
        var favorite = "NULL"
    }
    if (jsonCategory.owner_id){
        var owner_id = jsonCategory.owner_id
    } else {
        var owner_id = "NULL"
    }
    if (jsonCategory.parent_category_id){
        var parent_category_id = jsonCategory.parent_category_id
    } else {
        var parent_category_id = "NULL"
    }
    var sql = 'INSERT INTO categories(name, description, imageURL, favorite, owner_id, parent_category_id) VALUES '
    sql = sql + `('${name}', '${description}', '${imageURL}', ${favorite}, ${owner_id}, ${parent_category_id})`
    try {
        connection.query(sql, (err, results) =>{
        if (err) {
            console.error('Error executing query:', err);
            return;
        }
    })
    } catch (err) {
        console.log(err.message);
    }
});

//get all categories
app.get('/api/categories', async (req, res) => {
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
app.get('/api/category/:id', async (req, res) => {
    try{
        var id = req.params.id
        const sql = `SELECT * FROM categories WHERE id = ${id}`;
        connection.query(sql, (err, results) => {
            if (err) {
                console.error('Error executing query:', err);
                return;
            }
            console.log('Fetched user:', results);
        });
    } catch (err) {
        console.log(err.message);
    }
});

//update a gategory
app.put('/api/update/category/:id', async (req, res) => {
    try{
        jsonCategory = JSON.parse(JSON.stringify(req.body))
        var changes = ""
        if (jsonCategory.name){
            changes = changes + "name = " + "'" + jsonCategory.name + "'" +  ", "
        }
        if (jsonCategory.description){
            changes = changes + "description = " + "'" +  jsonCategory.description + "'" + ", "
        }
        if (jsonCategory.imageURL){
            changes = changes + "imageURL = " + "'" +  jsonCategory.imageURL + "'" + ", "
        }
        if (jsonCategory.favorite){
            changes = changes + "favorite = " + jsonCategory.favorite + ", "
        }
        if (jsonCategory.owner_id){
            changes = changes + "owner_id = " + jsonCategory.owner_id + ", "
        }
        if (jsonCategory.parent_category_id){
            changes = changes + "parent_category_id = " + jsonCategory.parent_category_id + ", "
        }
        if (changes != ""){
            changes = changes.slice(0, -2)
        }
        var id = req.params.id
        var sql = 'UPDATE categories SET ' + changes + ' WHERE id = ' + id
        connection.query(sql, (err, results) => {
            if (err) {
                console.error('Error executing query:', err);
                return;
            }
            console.log('Updated user:', results);
        });
    } catch (err) {
        console.log(err.message);
    }
});
        
//delete a category
app.delete('/api/delete/category/:id', async (req, res) => {
    try{
        const id = req.params.id
        sql = "DELETE FROM categories WHERE id = " + id
    } catch (err) {
        console.log(err.message);
    }
});

// Listen if API is connected
app.listen(5000, () => {
    console.log("Server has started on port 5000");
});