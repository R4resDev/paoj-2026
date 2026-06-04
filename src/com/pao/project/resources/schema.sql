DROP TABLE IF EXISTS imprumuturi;
DROP TABLE IF EXISTS carti;
DROP TABLE IF EXISTS autori;
DROP TABLE IF EXISTS cititori;

CREATE TABLE autori (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nume VARCHAR(100) NOT NULL
);

CREATE TABLE cititori (
    id INT PRIMARY KEY,
    nume VARCHAR(100) NOT NULL
);

CREATE TABLE carti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titlu VARCHAR(200) NOT NULL,
    isbn VARCHAR(50) UNIQUE,
    disponibila BOOLEAN DEFAULT TRUE,
    autor_id INT,
    FOREIGN KEY (autor_id) REFERENCES autori(id)
);

CREATE TABLE imprumuturi (
    id INT AUTO_INCREMENT PRIMARY KEY,
    carte_id INT,
    cititor_id INT,
    activ BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (carte_id) REFERENCES carti(id),
    FOREIGN KEY (cititor_id) REFERENCES cititori(id)
);