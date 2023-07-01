var historyList = []
var recentUsernameHolder = document.getElementById("recentUser");
var recentItemNameHolder = document.getElementById("recentItem");
var recentDateHolder = document.getElementById("recentDate");
var recentActionsHolder = document.getElementById("recentAction");

var ownRecentUsernameHolder = document.getElementById("ownRecentUser");
var ownRecentItemNameHolder = document.getElementById("ownRecentItem");
var ownRecentDateHolder = document.getElementById("ownRecentDate");
var ownRecentActionsHolder = document.getElementById("ownRecentAction");

var logoutButton = document.getElementById("logout_btn");

logoutButton.addEventListener("click", function() {
    document.cookie = "user_token=";
    location.href = "index.html";
});

async function getUNAME(userId) {
    try {
        const response = await axios.get('https://stord.tech/api/user/' + userId);
        return response.data.username;
    } catch (error) {
        console.error(error);
    }
}

async function getItemList(userId) {
    data = [];
    returnData = [];
    try {
        const response = await axios.get('https://stord.tech/api/allitems/' + document.cookie.split("=")[1]);
        for (i in response.data.length){
            data = await getHistoryData(response.data[i]["id"]);
            if (data.length > 0){
                returnData = returnData.concat(data);
            }
        }
        return returnData;
    } catch (error) {
        console.error(error);
    }
}

async function getHistoryData(itemId) {
    try {
        const response = await axios.get('https://stord.tech/api/history/item/' + itemId);
        return response.data;
    } catch (error) {
        console.error(error);
    }
}



async function createMyRecentActions(historyList, recentUsernameHolder, recentItemNameHolder, recentDateHolder, recentActionsHolder, ownRecentUsernameHolder, ownRecentItemNameHolder, ownRecentDateHolder, ownRecentActionsHolder){
    historyList = await getItemList(document.cookie.split("=")[1]);
    var username = await getUNAME(document.cookie.split("=")[1]);
    addNewInfo(historyList, recentUsernameHolder, "username", false, username);
    addNewInfo(historyList, recentItemNameHolder, "name", false, username);
    addNewInfo(historyList, recentDateHolder, "date", false, username);
    addNewInfo(historyList, recentActionsHolder, "action", false, username);

    addNewInfo(historyList, ownRecentUsernameHolder, "username", true, username);
    addNewInfo(historyList, ownRecentItemNameHolder, "name", true, username);
    addNewInfo(historyList, ownRecentDateHolder, "date", true, username);
    addNewInfo(historyList, ownRecentActionsHolder, "action", true, username);
}


function addNewInfo(historyList, Holder, info, own, username){
    for (i in historyList){
        var li = document.createElement("li");
        var liText = historyList[i][info]
        if (info == "date"){
            liText = liText.split("T")[0];
        }
        if (own || historyList[i]["username"] == username){
            li.appendChild(document.createTextNode(liText));
        }
        Holder.appendChild(li);
    }
}

createMyRecentActions(historyList, recentUsernameHolder, recentItemNameHolder, recentDateHolder, recentActionsHolder, ownRecentUsernameHolder, ownRecentItemNameHolder, ownRecentDateHolder, ownRecentActionsHolder);