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

async function getItemList(userId) {
    data = [];
    returnData = [];
    try {
        const response = await axios.get('https://stord.tech/api/allitems/' + document.cookie.split("=")[1]);
        for (i in response.data){
            data = await getHistoryData(i);
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
    historyList = await getItemList(1);
    addNewInfo(historyList, recentUsernameHolder, "username", false);
    addNewInfo(historyList, recentItemNameHolder, "name", false);
    addNewInfo(historyList, recentDateHolder, "date", false);
    addNewInfo(historyList, recentActionsHolder, "action", false);

    addNewInfo(historyList, ownRecentUsernameHolder, "username", true);
    addNewInfo(historyList, ownRecentItemNameHolder, "name", true);
    addNewInfo(historyList, ownRecentDateHolder, "date", true);
    addNewInfo(historyList, ownRecentActionsHolder, "action", true);
}


function addNewInfo(historyList, Holder, info, own){
    for (i in historyList){
        var li = document.createElement("li");
        var liText = historyList[i][info]
        if (info == "date"){
            liText = liText.split("T")[0];
        }
        if (!own || historyList[i]["username"] == "1"){
            li.appendChild(document.createTextNode(liText));
        }
        Holder.appendChild(li);
    }
}

createMyRecentActions(historyList, recentUsernameHolder, recentItemNameHolder, recentDateHolder, recentActionsHolder, ownRecentUsernameHolder, ownRecentItemNameHolder, ownRecentDateHolder, ownRecentActionsHolder);