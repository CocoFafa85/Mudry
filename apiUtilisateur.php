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
            getUtilisateur($_GET['id']);
        } else {
            getUtilisateurs();
        }
        break;


    case 'POST':
        addUtilisateur();
        break;


    case 'PUT':
        parse_str(file_get_contents("php://input"), $_PUT);
        updateUtilisateur($_PUT);
        break;


    case 'DELETE':
        parse_str(file_get_contents("php://input"), $_DELETE);
        deleteUtilisateur($_DELETE['id']);
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
function getUtilisateurs() {
    global $pdo;
    $stmt = $pdo->query("SELECT * FROM utilisateur");
    echo json_encode($stmt->fetchAll(PDO::FETCH_ASSOC));
}
/*
*Méthode get, récupère les données d'un' table  et les
*transforme en format json
*@parametre : $id, l'id du 
*@return : json
*/
function getUtilisateur($id) {
    global $pdo;
    $stmt = $pdo->prepare("SELECT * FROM Utilisateur WHERE id = ?");
    $stmt->execute([$id]);
    echo json_encode($stmt->fetch(PDO::FETCH_ASSOC));
}
/*
*Méthode , ajoute un  dans la table 
*@parametre :void
*@return : json pour informer si le  a été ajouté ou non
*/
function addUtilisateur() {
    global $pdo;
    $data = json_decode(file_get_contents("php://input"), true);
    if (!isset($data['nom_utilisateur'], $data['mdp_utilisateur'], $data['email'], $data['telephone'])) {
        echo json_encode(["message" => "Données invalides"]);
        return;
    }
    $stmt = $pdo->prepare("INSERT INTO utilisateur (nom_utilisateur, mdp_utilisateur, email, telephone) VALUES (?, ?, ?, ?)");
    $stmt->execute([$data['nom_utilisateur'], $data['mdp_utilisateur'], $data['email'], $data['telephone'] ?? null]);
    echo json_encode(["message" => "Utilisateur ajouté"]);
}
/*
*Méthode modifie les données d'un 
*@parametre :$data données du  à modifier
*@return : json pour informer si le  a été modifié
*/
function updateUtilisateur($data) {
    global $pdo;
    $data = json_decode(file_get_contents("php://input"), true);
    if (!isset($data['nom_utilisateur'], $data['mdp_utilisateur'], $data['email'], $data['telephone'])) {
        echo json_encode(["message" => "Données invalides"]);
        return;
    }
    $stmt = $pdo->prepare("UPDATE utilisateur SET nom_utilisateur=?, mdp_utilisateur=?, email=?, telephone=? WHERE id=?");
    $stmt->execute([$data['nom_utilisateur'], $data['mdp_utilisateur'], $data['email'], $data['telephone'] ?? null, $data['id']]);
    echo json_encode(["message" => "Utilisateur mis à jour"]);
}
/*
*Méthode  supprime les données d'un 
*@parametre :$id du  à supprimer
*@return : json pour informer si le  a été supprimé
*/
function deleteUtilisateur($id) {
    global $pdo;
    $stmt = $pdo->prepare("DELETE FROM utilisateur WHERE id=?");
    $stmt->execute([$id]);
    echo json_encode(["message" => "Utilisateur supprimé"]);
}
?>
