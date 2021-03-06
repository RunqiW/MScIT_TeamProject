<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <title>Top Trumps Game</title>
        <style>
         *{
            margin:0;
            padding:0;
            }
        
        .bg {
            background-repeat: no-repeat;
            background-image: linear-gradient(hsl(191, 27%, 71%), #fcfcfd);
            }

        .statistics{
            background-color: white;
            font-size: 20px;
            color:white;
            height: 160px;
            width: 700px;
            margin: 10px auto;
        }

        .button {
                background-color:rgb(82, 149, 189);
                border: none;
                color: white;
                width: 60px;
                height: 20px;
                text-align: center;
                text-decoration: none;
                font-size: 15px;
                border-radius: 3px;
                margin: 10px auto;
            }
        </style>
    </head>

    <body class="bg">
        <div class="header">
            <h1 style="background-color:rgb(66, 119, 155); font-size: 20px; color:white; text-align:center; height: 30px; width: 700px; margin: 0px auto;">Top Trumps Game</h1>
        </div>

        <div class="statistics">
            <div>
                <h1 style="background-color: rgb(146, 198, 228);color: white; margin: 0px; font-size: 20px; height: 25px; width:700px;">Statistics
                </h1>
            </div>
            <div style = "font-size:17px;color:black;margin:10px;">
                <table>
                    <tr>
                        <td>Number of Games:</td>
                        <td>${statistic.overallPlayed}</td>
                    </tr>
                    <tr>
                        <td>Number of Human Wins:</td>
                        <td>${statistic.humanWons}</td>
                    </tr>
                    <tr>
                        <td>Number of AI Wins:</td>
                        <td>${statistic.compWons}</td>
                        </tr>
                    <tr>
                        <td>Average draws per game:</td>
                        <td>${statistic.avgDraws}</td>
                    </tr>
                    <tr>
                        <td>Longest Game:</td>
                        <td>${statistic.maxRounds}</td>
                    </tr>
                </table>
            </div>
            <div>
                <form action="/toptrumps" method="get"><button class="button" type="submit">Back</button></form>
            </div>
        </div>
                      
    </body>

</html>