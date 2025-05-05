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
        if (isset($_GET['emailC']) && isset($_GET['mdpC'])){ //empty
            getPostulant($_GET['idenfiantC'], $_GET['mdpC']);
        } else {
        // if (isset($_GET['id'])) {
        //     getPostulant($_GET['id']);
        // } else {
            getPostulants();
        }
        break;


    case 'POST':
        addPostulant();
        break;


    case 'PUT':
        parse_str(file_get_contents("php://input"), $_PUT);
        updatePostulant($_PUT);
        break;


    case 'DELETE':
        parse_str(file_get_contents("php://input"), $_DELETE);
        deletePostulant($_DELETE['id']);
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
function getPostulants() {
    global $pdo;
    $stmt = $pdo->query("SELECT * FROM postulant");
    echo json_encode($stmt->fetchAll(PDO::FETCH_ASSOC));
}
/*
*Méthode get, récupère les données d'un' table  et les
*transforme en format json
*@parametre : $id, l'id du 
*@return : json
*/
function getPostulant(/*$id*/$email, $mdpC) {
    // global $pdo;
    // $stmt = $pdo->prepare("SELECT * FROM Postulant WHERE id = ?");
    // $stmt->execute([$id]);
    // echo json_encode($stmt->fetch(PDO::FETCH_ASSOC));

    global $pdo;
    $motDePasseSaisi = $mdpC;

    $hashEnregistre = $pdo->prepare("SELECT mdp_utilisateur FROM Utilisateur WHERE email = ? LIMIT 1");
    $hashEnregistre->execute([$email])->fetch(PDO::FETCH_ASSOC);
    $hashEnregistre = $row['mdp_utilisateur']; // Le hash stocké en BDD

    if (password_verify($motDePasseSaisi, $hashEnregistre)) {
        echo "Mot de passe correct !";
    } else {
        echo "Mot de passe incorrect.";
    }

    $stmt = $pdo->prepare("SELECT id FROM Utilisateur WHERE email = ? AND mdp_utilisateur = ? LIMIT 1");
    $stmt->execute([$email, $mdpC]);
    if ($stmt->rowCount() > 0) {
        $row = $stmt->fetch(PDO::FETCH_ASSOC);
        $id = $row['id'];

        $stmt = $pdo->prepare("SELECT * FROM Postulant WHERE id = ?");
        $stmt->execute([$id]);

        $postulant = $stmt->fetch(PDO::FETCH_ASSOC);
        echo json_encode($postulant);
    } else {
        echo json_encode(['error' => 'Utilisateur non trouvé']);
    }
}
/*
*Méthode , ajoute un  dans la table 
*@parametre :void
*@return : json pour informer si le  a été ajouté ou non
*/
function addPostulant() {
    global $pdo;
    $data = json_decode(file_get_contents("php://input"), true);
    if (!isset(/*$data['nom_utilisateur'], $data['mdp_utilisateur'], $data['email'], $data['telephone'], */$data['prenom'], $data['date_naissance'])) {
        echo json_encode(["message" => "Données invalides"]);
        return;
    }
    // $stmt = $pdo->prepare("INSERT INTO utilisateur (nom_utilisateur, mdp_utilisateur, email, telephone) VALUES (?, ?, ?, ?)");
    // $stmt->execute([$data['nom_utilisateur'], $data['mdp_utilisateur'], $data['email'], $data['telephone'] ?? null]);
    $stmt = $pdo->prepare("INSERT INTO postulant (prenom, date_naissance) VALUES (?, ?)");
    $stmt->execute([$data['prenom'], $data['date_naissance'] ?? null]);
    echo json_encode(["message" => "Postulant ajouté"]);
}
/*
*Méthode modifie les données d'un 
*@parametre :$data données du  à modifier
*@return : json pour informer si le  a été modifié
*/
function updatePostulant($data) {
    global $pdo;
    $data = json_decode(file_get_contents("php://input"), true);
    if (!isset($data['prenom'], $data['date_naissance'])) {
        echo json_encode(["message" => "Données invalides"]);
        return;
    }
    $stmt = $pdo->prepare("UPDATE postulant SET prenom=?, date_naissance=? WHERE id=?");
    $stmt->execute([$data['prenom'], $data['date_naissance'] ?? null, $data['id']]);
    echo json_encode(["message" => "Postulant mis à jour"]);
}
/*
*Méthode  supprime les données d'un 
*@parametre :$id du  à supprimer
*@return : json pour informer si le  a été supprimé
*/
function deletePostulant($id) {
    global $pdo;
    $stmt = $pdo->prepare("DELETE FROM postulant WHERE id=?");
    $stmt->execute([$id]);
    echo json_encode(["message" => "Postulant supprimé"]);
}
?>
