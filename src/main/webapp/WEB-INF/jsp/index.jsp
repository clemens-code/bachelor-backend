<%@ page contentType="text/html; ISO-8859-1; charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home</title>
</head>
<body>
    Welcome to my first JSP Site
    <div id="content-main">
        <div class="lead">
            <strong>Name of the Event:</strong>
            <span>${eventName}</span>
        </div>
        <div id="contents">
            <ul>
                <li style="list-style-type:none" v-for="player in players">
                    <player-card
                            v-bind:player="player" v-bind:key="player.id">
                    </player-card>
                </li>
            </ul>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/babel-standalone/6.21.1/babel.min.js"></script>

    <script type="text/babel">
        Vue.component('player-card', {
            props: ['player'],
            template: `<div class="card">
                    <div class="card-body">
                        <h6 class="card-title">
                            {{ player.name }}
                        </h6>
                        <p class="card-text">
                            <div>
                            {{ player.description }}
                            </div>
                        </p>
                    </div>
                </div>`
        });

        var app = new Vue({
            el: '#contents',
            data: {
                players: [
                    {id: "1", name: "Lionel Messi", description: "Argentina's superstar"},
                    {id: "2", name: "Christiano Ronaldo", description: "Portugal top-ranked player"}
                ]
            }
        });
    </script>
</body>
</html>