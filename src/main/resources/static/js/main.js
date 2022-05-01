//@ts-check
var gameTimer;

for (let row = 0; row < 100; row++) {
    for (let col = 0; col < 100; col++) {
        let location = document.getElementById("location_" + row + "_" + col);

        if (location === null) continue;

        location.addEventListener("mousedown", async event => {
            if (event.button === 0) await applyLeftClick(row.toString(), col.toString());
            else if (event.button === 2) applyRightClick(row.toString(), col.toString());
        });

        location.addEventListener("contextmenu", e => { e.preventDefault(); return false; } );
    }
}

/**
 * @param {string} row
 * @param {string} col
 */
async function applyLeftClick(row, col) {
    const gameTimerElement = document.getElementById("gameTimer");
    if (isGameOver(gameTimerElement)) return;

    const id = "location_" + row + "_" + col;
    const element = document.getElementById(id);
    if (element === null) return;

    if (element.classList.contains("location-opened")) {
        await openNeighbours(row, col);
    }

    if (!element.classList.contains("location-closed")) return;
    
    const gameBoardIdElement = document.getElementById("gameBoardId");
    const gameBoardId = gameBoardIdElement.classList[0];
    const apiEndpoint = window.location.protocol + "//" + window.location.hostname + ":8080/api/open/" + gameBoardId;
    
    const urlParams = new URLSearchParams({x: col, y: row});

    const result = await fetch(apiEndpoint + "?" + urlParams).then(response => response.json());

    if (result.firstMove) {
        gameTimer = window.setInterval(function() {
            gameTimerElement.innerHTML = "" + (parseInt(gameTimerElement.innerHTML) + 1);
        }, 1000)
    }

    if (result.mine) {
        window.clearInterval(gameTimer);
        gameTimerElement.classList.add("game-defeat");
        const gameResultEl = document.getElementById("result");
        gameResultEl.classList.add("game-defeat");
        gameResultEl.innerHTML = "<h1>Game over</h1>";
        element.classList.replace("location-closed", "location-mine")
        element.innerHTML = "<h1>X</h1>";
        window.scrollTo(0, 0);
        return;
    }

    element.classList.replace("location-closed", "location-opened");
    if (result.numNeighbouringMines === 0) {
        await openNeighbours(row, col);
    } else {
        element.innerHTML = "<h1>" + result.numNeighbouringMines.toString() + "</h1>";
    }

    if (result.gameOver) {
        window.clearInterval(gameTimer);
        gameTimerElement.classList.add("game-victory");
        const gameResultEl = document.getElementById("result");
        gameResultEl.classList.add("game-victory");
        gameResultEl.innerHTML = "<h1>Congratulations!</h1>";
        window.scrollTo(0, 0);
    }
}

async function openNeighbours(row, col) {
    for (let i = -1; i <= 1; ++i) {
        for (let j = -1; j <= 1; ++j) {
            let newRow = (parseInt(row) + i).toString();
            let newCol = (parseInt(col) + j).toString();

            let neighbour = document.getElementById("location_" + newRow + "_" + newCol);

            if (neighbour === null || !neighbour.classList.contains("location-closed")) continue;

            await applyLeftClick(newRow, newCol);
        }
    }
}

/**
 * @param {string} row
 * @param {string} col
 */
function applyRightClick(row, col) {
    const gameTimerElement = document.getElementById("gameTimer");
    if (isGameOver(gameTimerElement)) return;

    const id = "location_" + row + "_" + col;
    const element = document.getElementById(id);

    if (element.classList.contains("location-closed")) {
        element.classList.replace("location-closed", "location-flagged");
    } 
    
    else if (element.classList.contains("location-flagged")) {
        element.classList.replace("location-flagged", "location-closed");
    }
}

/**
 * @param {HTMLElement} gameTimerElement
 */
function isGameOver(gameTimerElement) {
    return gameTimerElement.classList.contains("game-defeat") ||
        gameTimerElement.classList.contains("game-victory");
}