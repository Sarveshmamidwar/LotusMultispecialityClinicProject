document.addEventListener("DOMContentLoaded", function () { 
    try {
        fetch('/doctors/appointmentalert')
            .then(response => response.json())
            .then(data => {
                if (data.length > 0) {
                    showAlert(data);
                    setTimeout(() => document.getElementById("enableSpeechButton").click(), 1000);
                }
            })
            .catch(error => console.error('Error fetching appointments:', error));
    } catch (error) {
        console.error("Unexpected error:", error);
    }
});

function showAlert(data) {
    let alertBox = document.getElementById("appointmentAlert");
    if (!alertBox) return;
    alertBox.innerHTML = `📅 ${data.length} appointments today!`;
    alertBox.style.display = "block";
    setTimeout(() => { alertBox.style.display = "none"; }, 5000);
}

function speakAlert() {
    if ('speechSynthesis' in window) {
        let speech = new SpeechSynthesisUtterance("Check today's appointments");
        speech.lang = "en-US";
        window.speechSynthesis.cancel();
        window.speechSynthesis.speak(speech);
    }
}

// Donut chart
let chart = bb.generate({
    data: {
        columns: [
            ["Completed", 2],
            ["Pending", 4],
            ["Cancelled", 3],
        ],
        type: "donut",
    },
    donut: { title: "Appointments" },
    bindto: "#donut-chart",
});