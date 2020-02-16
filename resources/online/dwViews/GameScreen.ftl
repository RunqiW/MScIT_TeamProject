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

            .text{
                font-size:15px;
                color:white; 
                text-align: left;
                background-color:rgb(146, 198, 228);
                height: 30px;
                width: 800px;
                margin: 0 auto;
            }
            
            .content{
                margin: 0 auto;
                width: 800px;
            }

            .status{
                width: 155px;
                height: 130px;
                margin: 10px;
                float: left;
                font-size: 15px;
            }

            .deck{
                /* display: inline-block; */
                margin: 0 ;
                width: 620px;
                float: right;
            }

            .card{
                
                color: black;
                border: 1px solid white;
                width: 180px;
                height: 300px;
                /* display: inline-block; */
                margin: 10px;
                background-color: white;
            	float: left;
            }

            .name{
            	border: 0px solid white;
                width: 180px;
                height: 40px;
                display: inline-block;
                margin: 0px;
                
            }
            .picturename{
            	width: 50px;
            	height: 20px;
            	/*display: inline-block; */
                margin: 10px;
                float: left;
            }
            
            .cardleft{
           		width: 20px;
            	height: 20px;
            	/*display: inline-block; */
                margin: 10px;
                float: right;
            	background-color:yellowgreen;
                border-radius: 10px;
                text-align: center;
            }
            
            .picture{
            	border: 0px;
                width: 180px;
                height: 80px;
                display: inline-block;
            }

            .property{
            	border: 0px ;
                width: 180px;
                height: 160px;
                margin: 10px;
            }

            .button{
                background-color:rgb(18, 156, 110);
                border: none;
                color: white;
                width: 155px;
                height: 20px;
                text-align: center;
                text-decoration: none;
                font-size: 15px;
                border-radius: 3px;
                margin: 0 auto;
            }

            /* drop-down button */
            .dropbtn {
                background-color: rgb(18, 156, 110);
                border: none;
                color: white;
                width: 155px;
                height: 20px;
                text-align: center;
                text-decoration: none;
                font-size: 15px;
                border-radius: 3px;
                margin: 0 auto;
                cursor: pointer;
            }

            /* container <div> - drop-down content*/
            .dropdown {
                position: relative;
                display: inline-block;
            }

            /* Drop-down content (hidden by default) */
            .dropdown-content {
                display: none;
                position: absolute;
                background-color: rgb(18, 156, 110);
                min-width: 155px;
                box-shadow: 0px 8px 16px 0px rgba(0,0,0,0.2);
            }

            /* link to the drop-down menu */
            .dropdown-content a {
                color: black;
                padding: 12px 16px;
                text-decoration: none;
                display: block;
            }

            /* After moving the mouse to it change its color */
            .dropdown-content a:hover {
                background-color: rgb(16, 139, 98);
            }

            /* After moving the mouse to it display the drop-down menu */
            .dropdown:hover .dropdown-content {
                display: block;
            }

            /* Modify the background color of the drop-down button after the drop-down content is displayed */
            .dropdown:hover .dropbtn {
                background-color: rgb(16, 139, 98);
            }

            .drpodownbtn {
                background-color:rgb(18, 156, 110);
                width: 155px;
                height: 20px;
                text-align: center;
            }
            <#if btnDisplay[3]>
                #next{
                    display:block;
                }
                #status{
                    display:none;
                }
            <#else>
                 #next{
                    display:none;
                 }
                 #status{
                    display:block;
                 }
            </#if>

             #showWinner{
                <#if btnDisplay[2]>
                    display:block;
                <#else>
                    display:none;
                </#if>
            }
             #displayAISelection{
                <#if btnDisplay[0]>
                    display:block;
                <#else>
                    display:none;
                </#if>
            }
            #select{
                <#if btnDisplay[1]>
                    display:block;
                <#else>
                    display:none;
                </#if>
            }
            #gameOver{
            <#if btnDisplay[4]>
                display:block;
            <#else>
                display:none;
            </#if>
            }
        </style>
        
    </head>

    <body class="bg">
        <!--header div-->
        <div class="header">
            <h1 style="background-color:rgb(66, 119, 155); font-size: 20px; color:white; text-align:center; height: 30px; width: 800px; margin: 0px auto;">Top Trumps Game</h1>
            <!--This paragraph shows the progress and results of the game-->
            <p class="text">${roundProgress}</p>
        </div>

        <!--content div-->
        <div class="content" id="content">
           
            <div class="deck">
                <#list  players>
                    <#items as player>
                        <div class="card" id="card">
                             <div class="name">
                                <h1 style="font-size:15px;background-color:rgb(75, 177, 114); font-size: 20px; color:white; text-align:center; height: 40px; width: 180px;">
                                ${player.playerName}</h1>
                            </div>
                            <div class="picturename">
                                <p>${player.hand.title}</p>
                            </div><div class="cardleft">
                                <p>${player.deck.size}</p>
                            </div>

                            <div class="picture">
                                <img src="http://dcs.gla.ac.uk/~richardm/TopTrumps/${player.hand.title}.jpg" width= "180px" height= "80px"/>
                            </div>
                            <div class="property">
                                <table>
                                    <tr>
                                        <td>${player.deck.category[0]}:</td>
                                        <td>${player.hand.attribute[0]}</td>
                                    </tr>
                                    <tr>
                                        <td>${player.deck.category[1]}:</td>
                                        <td>${player.hand.attribute[1]}</td>
                                    </tr>
                                    <tr>
                                        <td>${player.deck.category[2]}:</td>
                                        <td>${player.hand.attribute[2]}</td>
                                        </tr>
                                    <tr>
                                        <td>${player.deck.category[3]}:</td>
                                        <td>${player.hand.attribute[3]}</td>
                                    </tr>
                                    <tr>
                                        <td>${player.deck.category[4]}:</td>
                                        <td>${player.hand.attribute[4]}</td>
                                    </tr>
                                </table>

                            </div>
                        </div>
                    </#items>
                </#list>
            </div>

            <div class="status" id="status">
                <!--This paragraph shows the active player-->
                <p style="background-color: rgb(146, 198, 228);color: white; margin: 0px; font-size: 17px; height: 50px; width:155px;">
                    ${currentPlayer}</p>
                    <div class="dropdown" id="select">
                        <button class="dropbtn">Select Category</button>
                        <div class="dropdown-content">
                        <form action="/toptrumps/toSelectCategory" method="get">
                            <button class="drpodownbtn" type="submit" value="0" name="dropBtn">
                            Select: ${dropBtn[0]}</button></form>
                        <form action="/toptrumps/toSelectCategory" method="get">
                            <button class="drpodownbtn" type="submit" value="1" name="dropBtn">
                            Select: ${dropBtn[1]}</button></form>
                        <form action="/toptrumps/toSelectCategory" method="get">
                            <button class="drpodownbtn" type="submit" value="2" name="dropBtn">
                            Select: ${dropBtn[2]}</button></form>
                        <form action="/toptrumps/toSelectCategory" method="get">
                            <button class="drpodownbtn" type="submit" value="3" name="dropBtn">
                            Select: ${dropBtn[3]}</button></form>
                        <form action="/toptrumps/toSelectCategory" method="get">
                            <button class="drpodownbtn" type="submit" value="4" name="dropBtn">
                            Select: ${dropBtn[4]}</button></form>
                        
                    </div>
            </div>
                <div>
                    <p style="font-size:15px; height:auto; width:155px; background-color: white; text-margin:5px;">${categorySelection}</p>
                    <form action="/toptrumps/showWinner" method= "get"><button class="button" id="showWinner">Show Winner</button>
                    </form>
                </div>

                <div id="displayAISelection">
                    <form action="/toptrumps/displayAISelection" method= "get"><button class="button" id="displayAISelection">Next</button>
                    </form>
                </div>
                <div id="gameOver">
                    <form action="/toptrumps" method="get"><button class="button" type="submit">Back</button>
                    </form>
                </div>
        </div>

        
        <div style="margin:10px;float:left;">
            <form action="/toptrumps/nextRound" method= "get" ><button class="button" id = "next">Next Round</button>
            </form>
        </div>
    </div>

    </body>
</html>