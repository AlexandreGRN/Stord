# Stord
Stord is a Storage Management Application for Android

## Version
|Version| Summurize|
|-------|-------|
|V.0.0.1 | Added initial resources for MVP|
|V.0.1.0 | Created the frame holder and its navigation bar|
|V.0.2.0 | Frame 1 added (Category fragment)|
|V.0.2.1 | Disabling the automatic rotation|
|V.0.3.0 | Category object created|
|V.0.3.1 | Item object created|
|V.0.4.0 | Frame 2 added (Item and subcategory fragment)|
|V.0.5.0 | Frame 3 added (Favorite Frame)|
|V.0.5.1 | Handle no favorite item display|
|V.0.5.2 | Handle empty category display|
|V.0.5.3 | Handle empty user account (no categories at all)|
|V.0.6.0 |Create blueprint for item popup|
|V.0.6.1 |Create blueprint for category popup|
|V.0.6.2 |Item popup reponsive|
|V.0.6.3 |Category popup reponsive|
|V.0.6.4 |Change item object|
|V.0.6.5 |Create all buttons in item popup|
|V.0.6.6 |Create all buttons in category popup|
|V.0.7.0 |Frame4 added (add_new_item Frame) |
|V.0.8.0 |Environment setup and makefile creation|
|V.0.8.1 |SQL tables created|
|V.0.8.2 |SQL tests objects created|
|V.0.8.3 |API created for categories|
|V.0.8.4 |API created for items too|
|V.0.9.0 |API Hosted in AWS|
|V.0.10.0|Database Hosted in AWS|
|V.0.11.0|Added More API route|
|V.0.11.1|Created the SSL encryption method to encrypt data when doing https requests|
|V.0.11.2|Enhancing security of the application|
|V.0.12.0|Changing object models|
|V.0.12.1|Implement AWS requests for popup|
|V.0.12.2|Made all API call Asynchronous|
|V.0.13.0|Navigation Bar added|
|V.0.13.1|Change access to subcategories via onclick event on a category|
|V.0.14.0|Restructuration of the Application
|
## How to run
This repository contain multiple part of the project. The `Stord` folder contains:
### The API
The `API` folder contains the source code for the API. To test the API, you need to have Node.js installed in your computer. You can install all the prerequisites by running `make install` in the `Stord` folder, then git clone this repo and run `make setup` in the `Stord` folder. Then, you can run the API by running `node index.js` in the `API` folder. The API will be running on port 80 and will only support HTTPS requests.

The route for the API is `/api/`. The API supports the following requests:
/api/status
- `POST /api/create/category`: Creates a new category
- `POST /api/create/item`: Creates a new item
- `GET /api/categories/:owner_id`: Returns all the categories from a specific user
- `GET /api/categories/subcategories/:parent_category_id`: Returns all the subcategories from a specific category###
- `GET /api/items/:parent_category_id`: Returns all the items from a specific category
- `GET /api/allitems/:owner_id`: Returns all the items from a specific user
- `GET /api/category/:owner_id/:id`: Returns a specific category with the given id from a specific user
- `GET /api/item/:parent_category_id/:id`: Returns a specific item with the given id from a specific category
- `PUT /api/update/category/:id`: Updates the category with the given id
- `PUT /api/update/item/:id`: Updates the item with the given id
- `DELETE /api/delete/category/:id`: Deletes the category with the given id
- `DELETE /api/delete/item/:id`: Deletes the item with the given id

### The App
The `App` folder contains the source code for the Android application. To test the application, you need to have Android Studio installed in your computer. You can download Android Studio from [here](https://developer.android.com/studio). After installing Android Studio, you can clone this repository and open it in Android Studio. Then, you can run the application on your phone or on an emulator.

To use the Application you can download the APK (Not available publicly yet)
