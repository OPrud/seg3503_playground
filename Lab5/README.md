# Lab 5 – Mocks et Stubs

## Projet Grades

### Avant les modifications
Démarré l'application et soumis le formulaire de calcul. Erreur obtenue, telle que décrite dans les diapositives du laboratoire :
<img width="1467" height="312" alt="Screenshot 2026-07-02 at 2 03 32 PM" src="https://github.com/user-attachments/assets/2fe420f6-6a8f-47fb-b78c-764368f6cf72" />

Cette erreur survient parce que le module `Grades.Calculator` n'existe pas dans le projet original — `page_live.ex` l'appelle, mais rien ne l'implémente.
Avant même d'arriver à cette étape, j'ai dû corriger un problème de dépendance : la version de `cowlib` utilisée par le projet ne compile pas avec les versions plus récentes d'Elixir/Erlang (un vrai bug dans le spec de type de `cow_sse.erl`, sans lien avec le laboratoire). Corrigé en forçant une version plus récente de `cowlib` dans `mix.exs`.

### Avec la valeur stubbed
Créé `lib/grades/calculator.ex` comme stub retournant des valeurs codées en dur (`"B"`, `75.0`, `7.0`) peu importe les données entrées. L'application ne plante plus, mais retourne toujours le même résultat.
<img width="1465" height="846" alt="Screenshot 2026-07-05 at 2 08 50 PM" src="https://github.com/user-attachments/assets/42044ef4-6e59-45db-84d1-731cc1aa20d0" />

le code stub
<img width="511" height="166" alt="Screenshot 2026-07-05 at 2 17 47 PM" src="https://github.com/user-attachments/assets/190b9659-e0ac-4fdb-90e9-f3b8623d9075" />


### Avec l'implémentation réelle
Remplacé le stub par un vrai calcul de moyenne pondérée (20% devoirs, 20% labs, 25% mi-session, 35% final), converti en note alphabétique et en note numérique sur 10. Vérifié avec de vraies valeurs (89.7% → A → 9.0).
<img width="1467" height="801" alt="Screenshot 2026-07-05 at 2 20 48 PM" src="https://github.com/user-attachments/assets/e602b91e-db99-49cd-a73d-3f42bbac6259" />

l'implémentation réelle
<img width="708" height="925" alt="Screenshot 2026-07-05 at 2 18 27 PM" src="https://github.com/user-attachments/assets/9dcce625-9e8b-448e-8fb2-f9f06d94eeb4" />

## Projet Twitter
