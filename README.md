Add player
Request POST: http://localhost:8082/v1/btg/jokenpo/player
```json
{
	"playerName" : "JOGADOR 1"
}
```
```json
{
    "metadata": {
        "timestamp": "2020-06-05T00:00:00.000+0000"
    },
    "data": {
        "playerName": "JOGADOR 1"
    }
}
```

Delete Jogador
Request DELETE: http://localhost:8082/v1/btg/jokenpo/player/?playerName=JOGADOR%202

```json
{
    "metadata": {
        "timestamp": "2020-06-05T00:00:00.000+0000"
    },
    "data": [
        {
            "playerName": "JOGADOR 1"
        },
        {
            "playerName": "JOGADOR 3"
        },
        {
            "playerName": "JOGADOR 4"
        },
        {
            "playerName": "JOGADOR 5"
        }
    ]
}
```

List of players
Request GET: http://localhost:8082/v1/btg/jokenpo/player

```json
    "metadata": {
        "timestamp": "2020-06-05T00:00:00.000+0000"
    },
    "data": [
        {
            "playerName": "JOGADOR 1"
        },
        {
            "playerName": "JOGADOR 2"
        },
        {
            "playerName": "JOGADOR 3"
        },
        {
            "playerName": "JOGADOR 4"
        },
        {
            "playerName": "JOGADOR 5"
        }
    ]
}
```

Movement
Request POST: http://localhost:8082/v1/btg/jokenpo/move
```json
{
	"playerName" : "JOGADOR 1",
	"movement" : "STONE"
}
```
```json
{
    "metadata": {
        "timestamp": "2020-06-05T00:00:00.000+0000"
    },
    "data": {
        "player": {
            "playerName": "JOGADOR 1"
        },
        "movement": "STONE"
    }
}
```

Delete move
Request Delete: http://localhost:8082/v1/btg/jokenpo/move?playerName=JOGADOR%201
```json
{
    "metadata": {
        "timestamp": "2020-06-05T00:00:00.000+0000"
    },
    "data": [
        {
            "player": {
                "playerName": "JOGADOR 2"
            },
            "movement": "PAPER"
        },
        {
            "player": {
                "playerName": "JOGADOR 3"
            },
            "movement": "STONE"
        },
        {
            "player": {
                "playerName": "JOGADOR 4"
            },
            "movement": "SPOCK"
        }
    ]
}
```

List of Move
Request Get http://localhost:8082/v1/btg/jokenpo/move

```json
{
    "metadata": {
        "timestamp": "2020-06-05T00:00:00.000+0000"
    },
    "data": [
        {
            "player": {
                "playerName": "JOGADOR 1"
            },
            "movement": "STONE"
        },
        {
            "player": {
                "playerName": "JOGADOR 2"
            },
            "movement": "PAPER"
        },
        {
            "player": {
                "playerName": "JOGADOR 3"
            },
            "movement": "STONE"
        },
        {
            "player": {
                "playerName": "JOGADOR 4"
            },
            "movement": "SPOCK"
        }
    ]
}
```

Result Jokenpo
Request Get: http://localhost:8082/v1/btg/jokenpo/play
```json
{
    "metadata": {
        "timestamp": "2020-06-05T00:00:00.000+0000"
    },
    "data": {
        "history": [
            "JOGADOR 1 (STONE)",
            "JOGADOR 2 (PAPER)",
            "JOGADOR 3 (SCISSORS)",
            "JOGADOR 4 (STONE)",
            "JOGADOR 5 (SPOCK)"
        ],
        "result": "NOBODY WON!"
    }
}
```

Clear Jokenpo
Request Delete: http://localhost:8082/v1/btg/jokenpo/play
```json
{
    "meta": {
        "timestamp": "2020-06-05T00:00:00.000+0000"
    },
    "data": []
}
```