# IMS
Custom memcache implementation for a coding test for Playtech which should record player transactions in-memory. Was ultimately given an offer based off this test.

## Original task description


**Description**

Server offers service for player wallet (balance). Wallet state (balance) should be managed in memory (3rd party solution may not be used). Balance is backed up in database (hsql or sqlite). When balance is not in memory, it is loaded from database and any changes are done in memory. Player record in database is created on demand. There is a periodical background process to write changes from memory to database.

**Constraints on balance changes:**
 - Balance cannot be less than 0. 
 - If transaction exists (is duplicate), then previous response is returned. Check may take into consideration only 1000 latest transactions.
 - If balance change is bigger than configured limit, then change is denied (explained further below). 
 - If player is in blacklist, then change is denied (explained further below).

Configuration (balance change limit and player blacklist) must be taken from external source. This can be file, database, external component etc.

Client itself is a server that offers gameplay logic. Specific gameplay will not be implemented, client can just generate random balance updates. Specific communication protocol between client and server is not specified (custom protocol can be invented). Server must write proper log information, where at least IN/OUT per player must be grep’able.

As an alternative tests inside the server application can act as a client – so no actual client implementation is needed.

**Commands between servers:**
client->server: username, transaction id, balance change
server->client: transaction id, error code, balance version, balance change, balance after change

Database structure:
PLAYER(USERNAME, BALANCE_VERSION, BALANCE)

**Documentation:**
 - Describe shortly the implementation aspects. 
 - If some features are not implemented, point out the reasons

**Building and Packaging:**
 - The solution can be built using any commonly available build framework (e.g. Maven, Gradle, Ant). Providing project-local wrappers for running the build in-place are appreciated.
