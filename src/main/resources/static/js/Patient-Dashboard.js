document.addEventListener("DOMContentLoaded", () => {
    const rows = document.querySelectorAll("#appointment-body tr");
    rows.forEach((row, index) => {
        row.classList.add("stagger-row");
        row.style.animationDelay = `${index * 0.15}s`; // Delay each row by 0.15s
    });
});


document.getElementById("appointmentForm").addEventListener("submit", function(event) {
    event.preventDefault(); // stop default form submit

    let form = event.target;
    let isValid = true;

    // Select ALL fields
    let inputs = form.querySelectorAll("input, select, textarea");

    inputs.forEach(input => {
        let errorSpan = input.parentElement.querySelector(".error-msg");

        if (!input.value.trim()) {
            isValid = false;
            if (errorSpan) {
                let label = input.previousElementSibling ? input.previousElementSibling.innerText : "This field";
                errorSpan.textContent = "Please enter " + label;
            }
        } else {
            if (errorSpan) {
                errorSpan.textContent = "";
            }
        }
    });

    if (isValid) {
        form.submit(); // only submit if no errors
    }
});


// ✅ Clear validation messages when modal is closed
document.getElementById("exampleModal").addEventListener("hidden.bs.modal", function () {
    let form = document.getElementById("appointmentForm");

    // Clear all error messages
    form.querySelectorAll(".error-msg").forEach(span => {
        span.textContent = "";
    });

    // Optionally clear form inputs if needed
    // form.reset();
});