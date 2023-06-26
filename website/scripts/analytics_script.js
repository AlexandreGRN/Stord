// Elements
const barCanvasTotalAdded = document.getElementById("barCanvas_total_added").getContext('2d');
const barCanvasVariation = document.getElementById("lineCanvas_variation").getContext('2d');
const logoutButton = document.getElementById("logout_btn");
const select1 = document.getElementById("total_added_item-select");
const select2 = document.getElementById("variation_item-select");

// API requests
async function getItemList(userId) {
    try{
        const response = await axios.get("https://stord.tech/api/allitems/" + userId);
        return response.data;
    } catch (error) {
        console.log(error);
    }
}

async function getVariationData(itemId) {
    try {
        const response = await axios.get('https://stord.tech/api/variation/' + itemId);
        return response.data;
    } catch (error) {
        console.error(error);
    }
}


// Functions for graphs
function createDataBarChartTotalAdded(variationData) {
    const data = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];
    for (j in variationData) {
        if (variationData[j]["date"].split("-")[0] == 2020) {
            data[variationData[j]["date"].split("-")[1]-1] += variationData[j]["quantity"];
        }
    }
    return data;
};

function createAdvancedDataBarChartTotalAdded(variationData) {
    data = createDataBarChartTotalAdded(variationData);
    for (let i = data.length - 1; i > 0; i--){
        data[i] = data[i] - data[i-1];
    }
    return data;
};

const makeBarChartTotalAdded = async (id) => {
    const a = await getVariationData(id);
    const barChartTotalAdded = new Chart(barCanvasTotalAdded, {
        type: 'bar',
        data: {
            labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
            datasets: [{
                data: createAdvancedDataBarChartTotalAdded(a),
                backgroundColor: [
                    "#f38b4a",
                    "#56d798",
                    "#ff8397",
                    "#6970d5"
                ]
            }],
        },
        options: {
            plugins: {
                legend: {
                    display: false,
                }
            }
        }
    });
};

const makeBarChartVariation = async (id) => {
    const a = await getVariationData(id);
    const barChartVariation = new Chart(barCanvasVariation, {
        type: 'line',
        data: {
            labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
            datasets: [{
                label: 'Dataset 1',
                data: createDataBarChartTotalAdded(a),
            }],
        },
        options: {
            plugins: {
                legend: {
                    display: false,
                }
            }
        }
    });
};

// Create elements
logoutButton.addEventListener("click", function() {
    document.cookie = "user_token=";
    location.href = "index.html";
});

async function createSelectsOptions() {
    itemList = await getItemList(document.cookie.split("=")[1]);
    for (i in itemList) {
        var option = document.createElement("option");
        option.text = itemList[i]["name"];
        option.value = itemList[i]["id"];
        select1.add(option);
        select2.add(option.cloneNode(true));
    }

}

select1.onchange = async function() {
    makeBarChartTotalAdded(select1.value);
}
select2.onchange = async function() {
    makeBarChartVariation(select2.value);
}
createSelectsOptions();