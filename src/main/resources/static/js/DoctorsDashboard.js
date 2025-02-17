document.addEventListener("DOMContentLoaded", function () { 
    try {
        fetch('/doctors/appointmentalert')
            .then(response => response.json())
            .then(data => {
                if (data.length > 0) {
                    showAlert(data);
                    console.log("Appointments data:", data);

                    // Simulate button click to enable speech
                    setTimeout(() => {
                        document.getElementById("enableSpeechButton").click();
                    }, 1000);
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

    alertBox.style.display = "none";
    alertBox.innerHTML = `📅 ${data.length} appointments today!`;
    alertBox.style.display = "block";

    setTimeout(() => {
        alertBox.style.display = "none";
    }, 5000);
}

function speakAlert() {
    if ('speechSynthesis' in window) {
        let message = "Check today's appointments ";
        let speech = new SpeechSynthesisUtterance(message);
        speech.lang = "en-US";
        speech.rate = 1;
        speech.pitch = 1;
        speech.volume = 1;

        // Fetch available voices and select a female one
        let voices = window.speechSynthesis.getVoices();
        let femaleVoice = voices.find(voice => voice.name.includes("Female") || voice.name.includes("Samantha") || voice.name.includes("Google UK English Female"));

        if (femaleVoice) {
            speech.voice = femaleVoice;
        }

        // Stop any ongoing speech before speaking
        window.speechSynthesis.cancel();
        window.speechSynthesis.speak(speech);
    } else {
        console.warn("Speech synthesis not supported in this browser.");
    }
}

// Ensure voices are loaded before executing the function
window.speechSynthesis.onvoiceschanged = speakAlert;




		
		
		let chart = bb.generate({
		      data: {
		          columns: [
		              ["Blue", 2],
		              ["orange", 4],
		              ["green", 3],
		          ],
		          type: "donut",
		          onclick: function (d, i) {
		              console.log("onclick", d, i);
		          },
		          onover: function (d, i) {
		              console.log("onover", d, i);
		          },
		          onout: function (d, i) {
		              console.log("onout", d, i);
		          },
		      },
		      donut: {
		          title: "70",
		      },
		      bindto: "#donut-chart",
		  });