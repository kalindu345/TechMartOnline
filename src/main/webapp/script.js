let metricsChart;
let responseChart;

async function loadDashboard() {
        const response = await fetch('api/dashboard');
        const data = await response.json();

        document.getElementById("totalOrders").innerText = data.totalOrders;
        document.getElementById("messagesProcessed").innerText = data.messagesProcessed;
        document.getElementById("emailsSent").innerText = data.emailsSent;
        document.getElementById("productsAvailable").innerText = data.productsAvailable;
        document.getElementById("averageResponseTime").innerText = data.averageResponseTime + " ms";
        document.getElementById("serverStatus").innerText = data.serverStatus;

        drawCharts(data);
}

function drawCharts(data) {
        const metricsCtx = document.getElementById('metricsChart');

        if (metricsChart) {
                metricsChart.destroy();
        }

        metricsChart = new Chart(metricsCtx, {
                type: 'bar',
                data: {
                        labels: ['Orders', 'Messages', 'Emails', 'Products'],
                        datasets: [{
                                label: 'System Activity Metrics',
                                data: [
                                        data.totalOrders,
                                        data.messagesProcessed,
                                        data.emailsSent,
                                        data.productsAvailable
                                ]
                        }]
                }
        });


}

loadDashboard();

setInterval(loadDashboard, 5000);