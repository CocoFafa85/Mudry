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
        if (isset($_GET['id'])) {
            getEmploi($_GET['id']);
        } else {
            getEmplois();
        }
        break;


    case 'POST':
        addEmploi();
        break;


    case 'PUT':
        parse_str(file_get_contents("php://input"), $_PUT);
        updateEmploi($_PUT);
        break;


    case 'DELETE':
        parse_str(file_get_contents("php://input"), $_DELETE);
        deleteEmploi($_DELETE['id']);
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
function getEmplois() {
    global $pdo;
    $stmt = $pdo->query("SELECT * FROM Emploi");
    echo json_encode($stmt->fetchAll(PDO::FETCH_ASSOC));
}
/*
*Méthode get, récupère les données d'un' table  et les
*transforme en format json
*@parametre : $id, l'id du 
*@return : json
*/
function getEmploi($id) {
    global $pdo;
    $stmt = $pdo->prepare("SELECT * FROM Emploi WHERE id = ?");
    $stmt->execute([$id]);
    echo json_encode($stmt->fetch(PDO::FETCH_ASSOC));
}
/*
*Méthode , ajoute un  dans la table 
*@parametre :void
*@return : json pour informer si le  a été ajouté ou non
*/
function addEmploi() {
    global $pdo;
    $data = json_decode(file_get_contents("php://input"), true);
    if (!isset($data['titre'], $data['salaire'], $data['niveauEtudes'], $data['description'], $data['image'], $data['type'])) {
        echo json_encode(["message" => "Données invalides"]);
        return;
    }
    $stmt = $pdo->prepare("INSERT INTO Emploi (titre, salaire, niveauEtudes, description, image, type) VALUES (?, ?, ?, ?, ?, ?)");
    $stmt->execute([$data['titre'], $data['salaire'], $data['niveauEtudes'], $data['description'], $data['image'], $data['type'] ?? null]);
    echo json_encode(["message" => "Emploi ajouté"]);
}
/*
*Méthode modifie les données d'un 
*@parametre :$data données du  à modifier
*@return : json pour informer si le  a été modifié
*/
function updateEmploi($data) {
    global $pdo;
    $data = json_decode(file_get_contents("php://input"), true);
    if (!isset($data['titre'], $data['salaire'], $data['niveauEtudes'], $data['description'], $data['image'], $data['type'])) {
        echo json_encode(["message" => "Données invalides"]);
        return;
    }
    $stmt = $pdo->prepare("UPDATE Emploi SET titre=?, salaire=?, niveauEtudes=?, description=?, image=?, type=? WHERE id=?");
    $stmt->execute([$data['titre'], $data['salaire'], $data['niveauEtudes'], $data['description'], $data['image'], $data['type'] ?? null, $data['id']]);
    echo json_encode(["message" => "Emploi mis à jour"]);
}
/*
*Méthode  supprime les données d'un 
*@parametre :$id du  à supprimer
*@return : json pour informer si le  a été supprimé
*/
function deleteEmploi($id) {
    global $pdo;
    $stmt = $pdo->prepare("DELETE FROM Emploi WHERE id=?");
    $stmt->execute([$id]);
    echo json_encode(["message" => "Emploi supprimé"]);
}
?>
