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
        getRevisions();
        break;

    case 'POST':
        if (isset($_GET['id'])) {
            getRevision($_GET['id']);
        } else {
            addRevision();
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
function getRevisions() {
    global $pdo;
    $stmt = $pdo->query("SELECT * FROM revision");
    echo json_encode($stmt->fetchAll(PDO::FETCH_ASSOC));
}
/*
*Méthode get, récupère les données d'un' table  et les
*transforme en format json
*@parametre : $id, l'id du 
*@return : json
*/
function getRevision($id) {
    global $pdo;
    $stmt = $pdo->prepare("SELECT * FROM revision WHERE id = ?");
    $stmt->execute([$id]);
    echo json_encode($stmt->fetch(PDO::FETCH_ASSOC));
}
/*
*Méthode , ajoute un  dans la table 
*@parametre :void
*@return : json pour informer si le  a été ajouté ou non
*/
function addRevision() {
    global $pdo;
    $data = json_decode(file_get_contents("php://input"), true);
    if (!isset($data['dateR'], $data['libelle']) {
        echo json_encode(["message" => "Données invalides"]);
        return;
    }
    $stmt = $pdo->prepare("INSERT INTO revision (dateR, libelle) VALUES (?, ?)");
    $stmt->execute([$data['dateR'], $data['libelle'] ?? null]);
    echo json_encode(["message" => "revision ajoutée"]);
}
/*
*Méthode modifie les données d'un 
*@parametre :$data données du  à modifier
*@return : json pour informer si le  a été modifié
*/
function updateRevision($data) {
    global $pdo;
    $data = json_decode(file_get_contents("php://input"), true);
    if (!isset($data['dateR'], $data['libelle']) {
        echo json_encode(["message" => "Données invalides"]);
        return;
    }
    $stmt = $pdo->prepare("UPDATE revision SET titre=?, salaire=?, niveauEtudes=?, description=?, image=?, type=? WHERE id=?");
    $stmt->execute([$data['dateR'], $data['libelle']);
    echo json_encode(["message" => "Revision mis à jour"]);
}
/*
*Méthode  supprime les données d'un 
*@parametre :$id du  à supprimer
*@return : json pour informer si le  a été supprimé
*/
function deleteRevision($id) {
    global $pdo;
    $stmt = $pdo->prepare("DELETE FROM revision WHERE id=?");
    $stmt->execute([$id]);
    echo json_encode(["message" => "Revision supprimée"]);
}
?>
