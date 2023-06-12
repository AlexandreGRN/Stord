USE stord_test_db;

INSERT INTO owners(email)
VALUES
    ('1@gmail.com'),
    ('2@gmail.com'),
    ('3@gmail.com');

INSERT INTO categories(name, description, imageURL, favorite, owner_id, parent_category_id)
VALUES
    ('Transport', 'Ici est entreposé tout les services de transport et tout ce qui est en lien avec les voitures', 'https://cdn.pixabay.com/photo/2016/11/29/09/32/auto-1868726_960_720.jpg', 0, 1, NULL),
    ('Médecine', 'Ici est entreposé tout les services de médecine et tout ce qui est en lien avec les soins', 'https://cdn.pixabay.com/photo/2016/11/23/15/14/jars-1853439_960_720.jpg', 0, 1, NULL),
    ('Nourriture', 'Ici est entreposé tout les services de nourriture et tout ce qui est en lien avec la nourriture', 'https://cdn.pixabay.com/photo/2015/09/21/14/24/supermarket-949913_960_720.jpg', 0, 1, NULL);

INSERT INTO categories(name, description, imageURL, favorite, owner_id, parent_category_id)
VALUES
    ('Service de restoration', 'Préparation et fourniture de repas, boissons et service aux clients', 'https://cdn.pixabay.com/photo/2016/11/18/14/05/brick-wall-1834784_960_720.jpg', 0, 1, 3),
    ('Condiment', 'Assaisonnement pour améliorer le goût et accompagner les plats', 'https://cdn.pixabay.com/photo/2016/12/17/18/51/spices-1914130_1280.jpg', 0, 1, 3),
    ('Plat', 'Yummy', 'https://cdn.pixabay.com/photo/2014/11/05/15/57/salmon-518032_960_720.jpg', 0, 1, 3);

INSERT INTO items(name, description, remaining, alert, imageURL, favorite, parent_category_id)
VALUES
    ('Entrepot de nourriture', NULL, 3, 0, 'https://cdn.pixabay.com/photo/2016/11/22/19/24/archive-1850170_960_720.jpg', 0, 3),
    ('Crevettes', '1KG', 100, 5, 'https://cdn.pixabay.com/photo/2016/03/05/22/31/prawns-1239307_960_720.jpg', 0, 6),
    ('Sac de Riz', '1KG', 10000, 1000, 'https://cdn.pixabay.com/photo/2017/02/13/05/44/rice-2061877_960_720.jpg', 0, 6);

INSERT INTO users(username, email, password)
VALUES
    ('test', 'test', 'test'),
    ('test2', 'test2', 'test2'),
    ('test3', 'test3', 'test3');
