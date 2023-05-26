// Import and variables //
// Import
const express = require("express");
const mysql = require("mysql");
const cors = require("cors");
const dotenv = require('dotenv').config();
// Variables & prerequisites
const app = express();
app.use(express.json());
app.use(cors());


// DB connection //
const connection = mysql.createConnection({
    host: process.env.RDS_HOSTNAME,
    user: process.env.RDS_USERNAME,
    password: process.env.RDS_PASSWORD,
    port: process.env.RDS_PORT,
    database: process.env.RDS_DB_NAME
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
// Database check
app.get('/api/tables', async (req, res) => {
    var sql = 'SHOW TABLES'
    try {
        connection.query(sql, (err, results) =>{
        if (err) {
            console.error('Error executing query:', err);
            return;
        }
        console.log('Fetched tables:', results);
    })
    } catch (err) {
        console.log(err.message);
    }
});

//CREATE
//category
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
//item
app.post('/api/create/item', async (req, res) => {
    jsonItem = JSON.parse(JSON.stringify(req.body))

    if (jsonItem.name){
        var name = jsonItem.name
    } else {
        var name = "NULL"
    }
    if (jsonItem.description){
        var description = jsonItem.description
    } else {
        var description = "NULL"
    }
    if (jsonItem.remaining){
        var remaining = jsonItem.remaining
    } else {
        var remaining = "NULL"
    }
    if (jsonItem.alert ){
        var alert  = jsonItem.alert 
    } else {
        var alert  = "NULL"
    }
    if (jsonItem.imageURL){
        var imageURL = jsonItem.imageURL
    } else {
        var imageURL = "NULL"
    }
    if (jsonItem.favorite){
        var favorite = jsonItem.favorite
    } else {
        var favorite = "NULL"
    }
    if (jsonItem.parent_category_id){
        var parent_category_id = jsonItem.parent_category_id
    } else {
        var parent_category_id = "NULL"
    }
    var sql = 'INSERT INTO items(name, description, remaining, alert, imageURL, favorite, parent_category_id) VALUES '
    sql = sql + `('${name}', '${description}', ${remaining}, ${alert}, '${imageURL}', ${favorite}, ${parent_category_id})`
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

//GET ALL
//categories
app.get('/api/categories', async (req, res) => {
    const sql = 'SELECT * FROM categories';

    connection.query(sql, (err, results) => {
        if (err) {
            console.error('Error executing query:', err);
            return;
        }
        
        console.log('Fetched categories:', results);
    });
});
//items
app.get('/api/items', async (req, res) => {
    const sql = 'SELECT * FROM items';

    connection.query(sql, (err, results) => {
        if (err) {
            console.error('Error executing query:', err);
            return;
        }
        
        console.log('Fetched items:', results);
    });
});

//GET ONE
//category
app.get('/api/category/:id', async (req, res) => {
    try{
        var id = req.params.id
        const sql = `SELECT * FROM categories WHERE id = ${id}`;
        connection.query(sql, (err, results) => {
            if (err) {
                console.error('Error executing query:', err);
                return;
            }
            console.log('Fetched category:', results);
        });
    } catch (err) {
        console.log(err.message);
    }
});
//item
app.get('/api/item/:id', async (req, res) => {
    try{
        var id = req.params.id
        const sql = `SELECT * FROM items WHERE id = ${id}`;
        connection.query(sql, (err, results) => {
            if (err) {
                console.error('Error executing query:', err);
                return;
            }
            console.log('Fetched item:', results);
        });
    } catch (err) {
        console.log(err.message);
    }
});

//UPDATE
//category
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
//item
app.put('/api/update/item/:id', async (req, res) => {
    try{
        jsonItem = JSON.parse(JSON.stringify(req.body))
        var changes = ""
        if (jsonItem.name){
            changes = changes + "name = " + "'" + jsonItem.name + "'" +  ", "
        }
        if (jsonItem.description){
            changes = changes + "description = " + "'" +  jsonItem.description + "'" + ", "
        }
        if (jsonItem.remaining){
            changes = changes + "remaining = " +  jsonItem.description + ", "
        }
        if (jsonItem.alert){
            changes = changes + "alert = " +  jsonItem.description + ", "
        }
        if (jsonItem.imageURL){
            changes = changes + "imageURL = " + "'" +  jsonItem.imageURL + "'" + ", "
        }
        if (jsonItem.favorite){
            changes = changes + "favorite = " + jsonItem.favorite + ", "
        }
        if (jsonItem.parent_category_id){
            changes = changes + "parent_category_id = " + jsonItem.parent_category_id + ", "
        }
        if (changes != ""){
            changes = changes.slice(0, -2)
        }
        var id = req.params.id
        var sql = 'UPDATE items SET ' + changes + ' WHERE id = ' + id
        connection.query(sql, (err, results) => {
            if (err) {
                console.error('Error executing query:', err);
                return;
            }
            console.log('Updated item:', results);
        });
    } catch (err) {
        console.log(err.message);
    }
});

//DELETE
//category
app.delete('/api/delete/category/:id', async (req, res) => {
    try{
        const id = req.params.id
        sql = "DELETE FROM categories WHERE id = " + id
        connection.query(sql, (err, results) => {
            if (err) {
                console.error('Error executing query:', err);
                return;
            }
            console.log('Deleted category:', results);
        });
    } catch (err) {
        console.log(err.message);
    }
});
//item
app.delete('/api/delete/item/:id', async (req, res) => {
    try{
        const id = req.params.id
        sql = "DELETE FROM items WHERE id = " + id
        connection.query(sql, (err, results) => {
            if (err) {
                console.error('Error executing query:', err);
                return;
            }
            console.log('Deleted item:', results);
        });
    } catch (err) {
        console.log(err.message);
    }
});

// Listen if API is connected
app.listen(5000, () => {
    console.log("Server has started on port 5000");
});
