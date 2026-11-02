# Bank Management System

Mini-projet Spring Boot (DS1 – Développement Web) : API REST de gestion bancaire.

## Contexte métier

Une banque souhaite gérer ses agences, ses employés, ses clients, leurs comptes,
les cartes bancaires, les transactions (dépôt, retrait, virement) et les prêts.

## Technologies

- Java 17, Spring Boot, Spring Data JPA, Bean Validation
- MySQL 8
- Maven
- Postman (tests)

## Entités (7) et relations

| Entité | Attributs principaux | Relations |
|---|---|---|
| Agence | nom, adresse, téléphone | 1-N Employe, 1-N Compte |
| Employe | nom, email, poste | N-1 Agence, 1-N Pret |
| Client | nom, prénom, email | 1-N Compte, 1-N Pret |
| Compte | numéro, solde, type, statut | N-1 Client, N-1 Agence, 1-N CarteBancaire, 1-N Transaction |
| CarteBancaire | numéro, expiration, statut | N-1 Compte |
| Transaction | montant, type, date | N-1 Compte (source), N-1 Compte (destination) |
| Pret | montant, durée, taux, statut | N-1 Client, N-1 Employe |

## Architecture

```
com.bank
├── model        (entités JPA + validations)
├── repository   (JpaRepository + requêtes @Query)
├── service      (logique métier, @Transactional)
├── controller   (API REST)
├── exception    (exceptions + GlobalExceptionHandler)
└── BankManagementSystemApplication.java
```

## Requêtes personnalisées (3)

1. Comptes dont le solde dépasse un montant : `GET /api/comptes/solde-superieur?montant=700`
2. Transactions d'un compte : `GET /api/comptes/{id}/transactions`
3. Prêts en attente de validation : `GET /api/prets/en-attente`

## Endpoints

CRUD complet (GET liste, GET par id, POST, PUT, DELETE) sur :

`/api/agences`, `/api/employes`, `/api/clients`, `/api/comptes`,
`/api/cartes`, `/api/transactions`, `/api/prets`

Opérations métier :

| Méthode | URL | Description |
|---|---|---|
| POST | `/api/comptes/{id}/depot?montant=` | Dépôt |
| POST | `/api/comptes/{id}/retrait?montant=` | Retrait (refus si solde insuffisant) |
| POST | `/api/comptes/virement?source=&destination=&montant=` | Virement |
| PUT | `/api/prets/{id}/valider` | Valider un prêt EN_ATTENTE |
| PUT | `/api/prets/{id}/refuser` | Refuser un prêt EN_ATTENTE |

## Validation et gestion des erreurs

- Validation : `@NotBlank`, `@Email`, `@DecimalMin`, `@Pattern`, `@Future`, `@Valid`
- `404 Not Found` : ressource introuvable
- `400 Bad Request` : donnée invalide ou solde insuffisant
- `201 Created` à la création, `204 No Content` à la suppression

## Exécution

1. Installer Java 17 et MySQL 8.
2. Créer la base : `CREATE DATABASE bank_management;`
3. Adapter `src/main/resources/application.properties` (URL, port MySQL, utilisateur, mot de passe).
4. Lancer : `mvn spring-boot:run` (ou Run As > Spring Boot App dans STS).
5. L'API est disponible sur `http://localhost:8080`.

## Tests Postman

La collection exportée est dans le fichier `BankManagement.postman_collection.json`,
et les captures d'écran des tests sont dans le dossier `postman/screenshots/`.

## Auteur

Mohamed Amin Bchaini – Business Information Systems
