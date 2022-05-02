# Minesweeperweb

Minesweeper web game, built using spring boot and maven, targets java 18.

Can be started with command 'mvn spring-boot:run', or by running the jar from releases,
server will then start running on localhost port 8080.
To login register new user and login, or login as guest with username 'guest' and empty password.

At start game will create h2 database in users directory, in folder '~/db/h2/'.

In game location can be opened with left mouse click, and marked as mine with right click.
Left clicking an already opened location will open all surrounding locations that aren't marked as a mine.