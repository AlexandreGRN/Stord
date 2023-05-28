// Import and variables //
// Import
const express = require("express");
const mysql = require("mysql");
const cors = require("cors");
const dotenv = require('dotenv').config();
const https = require("https");
const fs = require("fs");

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
    res.json({ Status: 'OK' });
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
            res.json({ Status: 'Error' });
            return;
        }
        res.json({ Status: 'OK' });
    })
    } catch (err) {
        res.json({ Status: 'Error' });
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
            res.json({ Status: 'Error' });
            return;
        }
        res.json({ Status: 'OK' });
    })
    } catch (err) {
        res.json({ Status: 'Error' });
    }
});

//GET ALL FROM A USER/A CATEGORY
//categories
app.get('/api/categories/:owner_id', async (req, res) => {
    var owner_id = req.params.owner_id
    const sql = `SELECT * FROM categories WHERE owner_id = ${owner_id}`;

    connection.query(sql, (err, results) => {
        if (err) {
            res.json({ Status: 'Error' });
            return;
        }
        res.json(results);
    });
});
//items
app.get('/api/items/:parent_category_id', async (req, res) => {
    var parent_category_id = req.params.parent_category_id
    const sql = `SELECT * FROM items WHERE parent_category_id = ${parent_category_id}`;

    connection.query(sql, (err, results) => {
        if (err) {
            res.json({ Status: 'Error' });
            return;
        }
        res.json(results);
    });
});
//all items
app.get('/api/allitems/:owner_id', async (req, res) => {
    var owner_id = req.params.owner_id
    var sql = `SELECT items.id, items.name, items.description, items.remaining, items.alert, items.imageURL, items.favorite, items.parent_category_id FROM items JOIN categories ON items.parent_category_id = categories.id WHERE categories.owner_id = ${owner_id}`;
    connection.query(sql, (err, results) => {
        if (err) {
            res.json({ Status: 'Error' });
            return;
        }
        res.json(results);
    });
});
//all subcategories
app.get('/api/categories/subcategories/:parent_category_id', async (req, res) => {
    var parent_category_id = req.params.parent_category_id
    var sql = `SELECT categories.id, categories.name, categories.description, categories.imageURL, categories.favorite, categories.owner_id, categories.parent_category_id FROM categories WHERE categories.parent_category_id = ${parent_category_id}`;
    connection.query(sql, (err, results) => {
        if (err) {
            console.log(err)
            res.json({ Status: 'Error' });
            return;
        }
        res.json(results);
    });
});

//GET ONE
//category
app.get('/api/category/:owner_id/:id', async (req, res) => {
    var id = req.params.id
    var owner_id = req.params.owner_id
    const sql = `SELECT * FROM categories WHERE id = ${id} AND owner_id = ${owner_id}`;
    connection.query(sql, (err, results) => {
        if (err) {
            res.json({ Status: 'Error' });
            return;
        }
        res.json(results);
    });
});
//item
app.get('/api/item/:parent_category_id/:id', async (req, res) => {
    var id = req.params.id
    var parent_category_id = req.params.parent_category_id
    const sql = `SELECT * FROM items WHERE id = ${id} AND parent_category_id = ${parent_category_id}`;
    connection.query(sql, (err, results) => {
        if (err) {
            res.json({ Status: 'Error' });
            return;
        }
        res.json(results);
    });
});

//UPDATE
//category
app.put('/api/update/category/:id', async (req, res) => {
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
            res.json({ Status: 'Error' });
            return;
        }
        res.json({ Status: 'OK' });
    });
});
//item
app.put('/api/update/item/:id', async (req, res) => {
    jsonItem = JSON.parse(JSON.stringify(req.body))
    var changes = ""
    if (jsonItem.name){
        changes = changes + "name = " + "'" + jsonItem.name + "'" +  ", "
    }
    if (jsonItem.description){
        changes = changes + "description = " + "'" +  jsonItem.description + "'" + ", "
    }
    if (jsonItem.remaining){
        changes = changes + "remaining = " +  jsonItem.remaining + ", "
    }
    if (jsonItem.alert){
        changes = changes + "alert = " +  jsonItem.alert + ", "
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
            res.json({ Status: 'Error' });
            console.log(err)
            return;
        }
        res.json({ Status: 'OK' });
    });
});

//DELETE
//category
app.delete('/api/delete/category/:id', async (req, res) => {
    const id = req.params.id
    sql = "DELETE FROM categories WHERE id = " + id
    connection.query(sql, (err, results) => {
        if (err) {
            res.json({ Status: 'Error' });
            return;
        }
        res.json({ Status: 'OK' });
    });
});
//item
app.delete('/api/delete/item/:id', async (req, res) => {
    const id = req.params.id
    sql = "DELETE FROM items WHERE id = " + id
    connection.query(sql, (err, results) => {
        if (err) {
            res.json({ Status: 'Error' });
            return;
        }
        res.json({ Status: 'OK' });
    });
});

// Listen if API is connected
var port = process.env.PORT || 5000;
if (process.env.TEST_API == "true") {
    app.listen(5000, () => {
        console.log("Server has started on port 5000");
    });
} else {
    https
    .createServer(
                    // Provide the private and public key to the server by reading each
                    // file's content with the readFileSync() method.
        {
        key: fs.readFileSync("key.pem"),
        cert: fs.readFileSync("cert.pem"),
        },
        app
    )
    .listen(443, () => {
        console.log("Server has started on port ", port);
    })
};
