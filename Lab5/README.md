# Lab 5 – Mocks et Stubs

## Projet Grades

### Avant les modifications
Démarré l'application et soumis le formulaire de calcul. Erreur obtenue, telle que décrite dans les diapositives du laboratoire :
<img width="1467" height="312" alt="Screenshot 2026-07-02 at 2 03 32 PM" src="https://github.com/user-attachments/assets/2fe420f6-6a8f-47fb-b78c-764368f6cf72" />

Cette erreur survient parce que le module `Grades.Calculator` n'existe pas dans le projet original. `page_live.ex` l'appelle, mais rien ne l'implémente.
Avant même d'arriver à cette étape, j'ai dû corriger un problème de dépendance : la version de `cowlib` utilisée par le projet ne compile pas avec les versions plus récentes d'Elixir/Erlang (un vrai bug dans le spec de type de `cow_sse.erl`, sans lien avec le laboratoire). Corrigé en forçant une version plus récente de `cowlib` dans `mix.exs`.

### Avec la valeur stubbed
Créé `lib/grades/calculator.ex` comme stub retournant des valeurs codées en dur (`"B"`, `75.0`, `7.0`) peu importe les données entrées. L'application ne plante plus, mais retourne toujours le même résultat.
<img width="1465" height="846" alt="Screenshot 2026-07-05 at 2 08 50 PM" src="https://github.com/user-attachments/assets/42044ef4-6e59-45db-84d1-731cc1aa20d0" />

#### le code stub
<img width="511" height="166" alt="Screenshot 2026-07-05 at 2 17 47 PM" src="https://github.com/user-attachments/assets/190b9659-e0ac-4fdb-90e9-f3b8623d9075" />
Extrait du code du stub


### Avec l'implémentation réelle
Remplacé le stub par un vrai calcul de moyenne pondérée (20% devoirs, 20% labs, 25% mi-session, 35% final), converti en note alphabétique et en note numérique sur 10. Vérifié avec de vraies valeurs (89.7% → A → 9.0).
<img width="1467" height="801" alt="Screenshot 2026-07-05 at 2 20 48 PM" src="https://github.com/user-attachments/assets/e602b91e-db99-49cd-a73d-3f42bbac6259" />

#### l'implémentation réelle
<img width="708" height="925" alt="Screenshot 2026-07-05 at 2 18 27 PM" src="https://github.com/user-attachments/assets/9dcce625-9e8b-448e-8fb2-f9f06d94eeb4" />
Extrait du code de l'implémentation réelle

## Projet Twitter
### Avant les modifications
Exécuté `./bin/test` sur le code original. Le test `mock_full_object()` et `mock_partial_object()` plantent avec une `InaccessibleObjectException` provenant d'EasyMock (via cglib) :
<img width="1464" height="734" alt="Screenshot 2026-07-05 at 3 43 21 PM" src="https://github.com/user-attachments/assets/8f45721e-bc5e-4ff5-a401-2833faf59699" />

Ceci correspond exactement à l'erreur montrée à la diapositive 17 du laboratoire. La cause : les versions récentes de Java (9+) restreignent par défaut l'accès réflexif que EasyMock utilise pour générer des objets simulés (mocks) de classes concrètes.

**Correction :** ajout du flag `--add-opens java.base/java.lang=ALL-UNNAMED` à la commande `java` dans `bin/test`. Après cette modification, `mock_full_object()` et `mock_partial_object()` passent.
<img width="1116" height="587" alt="Screenshot 2026-07-05 at 3 47 56 PM" src="https://github.com/user-attachments/assets/53ad75ee-6265-4ee4-a37b-51c9bb48f260" />

### Implémentation des 4 tests manquants

Les 4 tests étaient commentés dans `TwitterTest.java`. On les a implémentés avec `partialMockBuilder`, qui simule seulement `loadTweet()` tout en gardant le vrai comportement de `isMentionned()` , ceci permet de contrôler exactement quel tweet est retourné, plutôt que de dépendre du hasard de `Math.random()`.

---

**Test 1 : `isMentionned_lookForAtSymbol`**

Vérifie que `isMentionned("me")` retourne `true` pour le tweet `"hello @me"`, et que `isMentionned("you")` retourne `false` pour le même tweet.
<img width="807" height="375" alt="Screenshot 2026-07-05 at 3 53 17 PM" src="https://github.com/user-attachments/assets/fbd8f372-67b1-4ed0-a413-90557f463f5c" />

---

**Test 2 : `isMentionned_superStringNotFound`**

Vérifie que pour le tweet `"hello @me"`, `isMentionned("me")` retourne `true` et `isMentionned("meat")` retourne `false`.

<img width="975" height="626" alt="Screenshot 2026-07-05 at 3 58 13 PM" src="https://github.com/user-attachments/assets/2e3ea32e-4d9c-433e-9e80-f1ba19883b70" />

---

**Test 3 : `isMentionned_dontReturnSubstringMatches`**

Vérifie que pour le tweet `"hello @meat"`, `isMentionned("me")` retourne `false` et `isMentionned("meat")` retourne `true`.

<img width="947" height="611" alt="Screenshot 2026-07-05 at 3 55 07 PM" src="https://github.com/user-attachments/assets/0bf22434-17c8-4b40-856e-303bb2a5fcb4" />

Ce test échoue : `.contains()` fait une recherche de sous-chaîne, donc `"@meat"` contient `"@me"`, ce qui donne un faux positif.

<img width="656" height="409" alt="Screenshot 2026-07-05 at 4 03 00 PM" src="https://github.com/user-attachments/assets/5616001e-6f42-4f53-9134-aba53c150a1e" />

Après avoir remplacé `.contains()` par une comparaison exacte mot par mot, ce test passe.

---

**Test 4 : `isMentionned_handleNull`**

Vérifie que si `loadTweet()` retourne `null`, `isMentionned("me")` et `isMentionned("meat")` retournent toutes les deux `false` plutôt que de planter.

<img width="1204" height="831" alt="Screenshot 2026-07-05 at 4 00 05 PM" src="https://github.com/user-attachments/assets/a29f8c7c-8f0a-40f8-a3a5-b4770e9f1a88" />

Ce test échoue avec un `NullPointerException`, car le code original appelle `.contains()` sur `tweet` sans vérifier s'il est `null`.

Après avoir ajouté une vérification `null` au début de `isMentionned()`, ce test passe.

<img width="656" height="409" alt="Screenshot 2026-07-05 at 4 03 00 PM" src="https://github.com/user-attachments/assets/4570c670-6077-4f23-bbd8-e4f5a5ffc2a2" />

### Analyse du code de isMentionned()

Le code original :
```java
public boolean isMentionned(String name) {
  String tweet = loadTweet();
  return tweet.contains("@" + name);
}
```

contenait deux bugs, révélés par les tests ci-dessus : une correspondance par sous-chaîne au lieu d'une correspondance exacte, et l'absence de vérification `null`.

**Correction appliquée :**
```java
public boolean isMentionned(String name) {
  String tweet = loadTweet();
  if (tweet == null) {
    return false;
  }
  for (String word : tweet.split("\\s+")) {
    if (word.equals("@" + name)) {
      return true;
    }
  }
  return false;
}
```

### Observation sur actual_call()

Le test `actual_call()` (qui n'utilise aucun mock) est intrinsèquement instable : il appelle le vrai `loadTweet()`, qui retourne un tweet aléatoire (45% `@me`, 45% `@you`, 10% `null`). Ce test a échoué de deux façons différentes pendant le développement: une fois par une assertion fausse, une fois par un `NullPointerException`, simplement selon le hasard. Ceci illustre l'intérêt des mocks : ils rendent les tests déterministes, contrairement au code réel non contrôlé. Après la correction du bug, `actual_call()` passe de façon fiable puisque `isMentionned` ne plante plus sur `null`.




