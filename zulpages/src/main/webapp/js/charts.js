zk.afterMount(function () {

    const lineCtx = document.getElementById('lineChart').getContext('2d');
    const pieCtx = document.getElementById('pieChart').getContext('2d');

    window.values = [120, 180, 95, 220];

    window.lineChart = new Chart(lineCtx, {
        type: 'line',
        data: {
            labels: values.map((_, i) => `Entry ${i + 1}`),
            datasets: [{
                label: 'Value Trend',
                data: values,
                borderColor: '#4CAF50',
                tension: 0.4,
                fill: false
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });

    window.pieChart = new Chart(pieCtx, {
        type: 'pie',
        data: {
            labels: ['Previous Entries', 'Latest Entry'],
            datasets: [{
                data: [615, 0],
                backgroundColor: ['#2196F3', '#FF9800']
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false
        }
    });

});
function addValue() {
    // Use ZK widget lookup – safer in ZUL pages
    const widget = zk.Widget.$('$valueInput');  // $ prefix is important for ZK ID lookup

    if (!widget) {
        console.error("Value input widget not found – check id='valueInput' in ZUL");
        alert("Input field not found – page may have loading issue");
        return;
    }

    const inputElement = widget.$n();  // native DOM element
    if (!inputElement) {
        console.error("Native input node not found");
        return;
    }

    const input = inputElement.value.trim();

    if (!input || isNaN(input) || Number(input) <= 0) {
        alert("Please enter a positive number");
        return;
    }

    const value = parseFloat(input);

    // Rest of your chart update code...
    values.push(value);

    lineChart.data.labels.push(`Entry ${values.length}`);
    lineChart.data.datasets[0].data.push(value);
    lineChart.update();

    const previousSum = values.slice(0, -1).reduce((a, b) => a + b, 0);
    pieChart.data.datasets[0].data = [previousSum, value];
    pieChart.update();

    // Clear input using ZK method
    widget.setValue('');
}