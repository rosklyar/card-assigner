Service that assigns a card to the user.

System has configuration that consists of album, sets and cards. Album contains sets. Sets contain cards.

Example:

Album "Animals"

- Set "Birds"
 - Card "Stork"
 - Card "Tit"
 - Card "Eagle"
- Set "Fish"
 - Card "Perch"
 - Card "Pike"
 - Card "Marlin"

Service has to support a functionality that adds card to the user.

While Service assigns cards it generates following events:

- User has completed a set of cards (user has collected all cards from a set)
- User has completed an album (user has collected all sets from an album)

Requests for adding cards can be called within multithreading environment. State of users can be stored in the memory.

Following scenario can be used as a test for the Service:
- Randomly generate cards for a defined list of users from "n" threads until all users complete an album.