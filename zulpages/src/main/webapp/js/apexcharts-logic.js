console.log("apexcharts-logic.js - FIXED VALUE MODE v2 started");

function initCharts() {
    console.log("initCharts() called");

    // Use ZK widget lookup for safety (handles ID scoping)
    const lineWidget = zk.Widget.$('lineChart');
    const pieWidget = zk.Widget.$('pieChart');

    let lineDiv = null;
    let pieDiv = null;

    if (lineWidget) {
        lineDiv = lineWidget.$n();  // native DOM element
        console.log("lineChart widget found, native div:", lineDiv);
    } else {
        console.log("lineChart widget NOT found via zk.Widget");
        lineDiv = document.getElementById('lineChart');
        console.log("fallback document.getElementById('lineChart'):", lineDiv);
    }

    if (pieWidget) {
        pieDiv = pieWidget.$n();
        console.log("pieChart widget found, native div:", pieDiv);
    } else {
        console.log("pieChart widget NOT found via zk.Widget");
        pieDiv = document.getElementById('pieChart');
        console.log("fallback document.getElementById('pieChart'):", pieDiv);
    }

    if (!lineDiv || !pieDiv) {
        console.error("CRITICAL: Could not find chart containers even with ZK lookup");
        return;
    }

    console.log("Both chart containers FOUND ✓");

    // Fixed values test
    const fixedValues = [100, 200, 150, 300, 250];

    // Line chart
    new ApexCharts(lineDiv, {
        series: [{ name: 'Test Trend', data: fixedValues }],
        chart: { type: 'line', height: 380 },
        stroke: { curve: 'smooth', colors: ['#00C853'] },
        xaxis: { categories: fixedValues.map((_, i) => `Test ${i+1}`) },
        title: { text: 'Fixed Test Line Chart' }
    }).render();
    console.log("Line chart rendered");

    // Pie chart
    new ApexCharts(pieDiv, {
        series: [44, 55, 67, 83],
        chart: { type: 'pie', height: 380 },
        labels: ['Category A', 'B', 'C', 'D'],
        colors: ['#2196F3', '#FF9800', '#4CAF50', '#F44336'],
        title: { text: 'Fixed Test Pie Chart' }
    }).render();
    console.log("Pie chart rendered");
}

// Run with ZK or fallback
if (window.zk && zk.afterMount) {
    zk.afterMount(initCharts);
    console.log("Using zk.afterMount");
} else {
    console.log("zk.afterMount not available - fallback timeout");
    setTimeout(initCharts, 1200);
}