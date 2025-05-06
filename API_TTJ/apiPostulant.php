<?php
header("Access-Control-Allow-Origin: *");
header("Content-Type: application/json; charset=UTF-8");
header("Access-Control-Allow-Methods: GET, POST, PUT, DELETE");
header("Access-Control-Allow-Headers: Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");


require 'db.php';


$method = $_SERVER['REQUEST_METHOD'];
/*
*Méthode switch pour identifier le type de méthode et exécuter 
*la fonction correspondante
*@parametre : $method peut prendre la valeur GET, POST, PUT ou DELETE
*@return : void
*/


switch ($method) {
    case 'GET':
        getGaragistes();
        break;

    case 'POST':
        if (isset($_GET['id'])) {
            getGaragiste($_GET['id']);
        } else {
            addGaragiste();
        }
        break;

    default:
        echo json_encode(["message" => "Méthode non supportée"]);
        break;
}

/*
*Méthode get récupère les données de la table et les
*transforme en format json
*@parametre : void
*@return : json
*/
function getGaragistes() {
    global $pdo;
    $stmt = $pdo->query("SELECT * FROM garagiste");
    echo json_encode($stmt->fetchAll(PDO::FETCH_ASSOC));
}
/*
*Méthode get, récupère les données d'un' table  et les
*transforme en format json
*@parametre : $id, l'id du 
*@return : json
*/
function getGaragiste(/*$id*/$identifiant, $motdepasse) {
    // global $pdo;
    // $stmt = $pdo->prepare("SELECT * FROM Garagiste WHERE id = ?");
    // $stmt->execute([$id]);
    // echo json_encode($stmt->fetch(PDO::FETCH_ASSOC));

    global $pdo;
    $motDePasseSaisi = $motdepasse;

    $hashEnregistre = $pdo->prepare("SELECT motdepasse FROM personnel WHERE mail = ? LIMIT 1");
    $hashEnregistre->execute([$mail])->fetch(PDO::FETCH_ASSOC);
    $hashEnregistre = $row['motdepasse']; // Le hash stocké en BDD

    if (password_verify($motDePasseSaisi, $hashEnregistre)) {
        echo "Mot de passe correct !";
    } else {
        echo "Mot de passe incorrect.";
    }

    $stmt = $pdo->prepare("SELECT id FROM personnel WHERE mail = ? AND motdepasse = ? LIMIT 1");
    $stmt->execute([$mail, $motdepasse]);
    if ($stmt->rowCount() > 0) {
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        $id = $row['id'];

        $stmt = $pdo->prepare("SELECT * FROM Garagiste WHERE id = ?");
        $stmt->execute([$id]);

        $garagiste = $stmt->fetch(PDO::FETCH_ASSOC);
        echo json_encode($garagiste);
    } else {
        echo json_encode(['error' => 'Utilisateur non trouvé']);
    }
}
