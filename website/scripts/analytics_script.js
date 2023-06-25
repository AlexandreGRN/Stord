async function getVariationData(itemId) {
    try {
        const response = await axios.get('https://stord.tech/api/variation/' + itemId);
        return response.data;
    } catch (error) {
        console.error(error);
    }
}


const barCanvasTotalAdded = document.getElementById("barCanvas_total_added").getContext('2d');
const barCanvasVariation = document.getElementById("lineCanvas_variation").getContext('2d');

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

const makeBarChartTotalAdded = async () => {
    const a = await getVariationData(4);
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

const makeBarChartVariation = async () => {
    const a = await getVariationData(4);
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

makeBarChartTotalAdded();
makeBarChartVariation();
