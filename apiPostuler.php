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
        getPostuler($_GET['id'], $_GET['admis']);
        break;


    case 'POST':
        addPostuler();
        break;

            
    case 'PUT':
        
        break;


    case 'DELETE':

        break;


    default:
        echo json_encode(["message" => "Méthode non supportée"]);
        break;
}

/*
*Méthode get, récupère les données d'un' table  et les
*transforme en format json
*@parametre : $id, l'id du 
*@return : json
*/
function getPostuler($id, $admis) {
    global $pdo;
    $stmt = $pdo->prepare("SELECT emploi.id, entreprise_id, titre, salaire, niveau_etudes, description, image, type FROM emploi 
                        INNER JOIN postuler ON postuler.emploi_id = emploi.id 
                        INNER JOIN postulant ON postuler.postulant_id = postulant.id 
                        WHERE postulant.id = ? AND admis = ?;");
    $stmt->execute([$id, $admis]);

    // Si aucune donnée n'est trouvée, renvoyer un message d'erreur
    $emplois = $stmt->fetchAll(PDO::FETCH_ASSOC);
    echo json_encode($emplois);
}

/*
*Méthode , ajoute un  dans la table 
*@parametre :void
*@return : json pour informer si le  a été ajouté ou non
*/
function addPostuler() {
    global $pdo;
    $data = json_decode(file_get_contents("php://input"), true);
    if (!isset($data['postulant_id'], $data['emploi_id'])) {
        echo json_encode(["message" => "Données invalides"]);
        return;
    }
    $stmt = $pdo->prepare("INSERT INTO Postuler (postulant_id, emploi_id, admis) VALUES (?, ?, ?)");
    $stmt->execute([$data['postulant_id'], $data['emploi_id'], 0 ?? null]);
    echo json_encode(["message" => "Candidature ajoutée"]);
}

