const barCanvasTotalAdded = document.getElementById("barCanvas_total_added").getContext('2d');
const barCanvasVariation = document.getElementById("lineCanvas_variation").getContext('2d');

const barChartTotalAdded = new Chart(barCanvasTotalAdded, {
    type: 'bar',
    data: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
        datasets: [{
            data: [12, 19, -3, -15, 2, 3, 0, 0, 0, 0, 0, 0],
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

const barChart = new Chart(barCanvasVariation, {
    type: 'line',
    data: {
        labels: ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'],
        datasets: [{
            label: 'Dataset 1',
            data: [12, 19, -3, -15, 2, 3, 0, 0, 0, 0, 0, 0],
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