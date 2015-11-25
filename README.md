Service for assigning some sort of cards to user

System has configuration of album with cards. Album contains sets. Sets contain cards.

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

Service has to support an API for adding some card to user.

During assigning cards Service generates next events:
- User finished some set(user has all cards in some set)
- User finished the album(user has all cards in the album)

Requests for adding cards can be called in multithreading environment. State of users service can store in memory.
We can use next scenario as test for the system:
- Generate cards randomly for defined list of users from n threads until all users finish album.