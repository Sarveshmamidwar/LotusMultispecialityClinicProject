function addAppointmentReception(event) {
    event?.preventDefault();

    // Get input elements
    let fullnameEl = document.getElementById("fullname");
    let genderEl   = document.getElementById("gender");
    let dateEl     = document.getElementById("date");
    let timeEl     = document.getElementById("time");
    let phoneEl    = document.getElementById("phone");
    let doctorEl   = document.getElementById("doctor");
    let emailEl    = document.getElementById("email");
    let messageEl  = document.getElementById("message");

    // Remove old error styles
    [fullnameEl, genderEl, dateEl, timeEl, phoneEl, doctorEl, emailEl]
        .forEach(el => el.classList.remove("input-error"));

    let fullname = fullnameEl.value.trim();
    let gender   = genderEl.value.trim();
    let date     = dateEl.value.trim();
    let time     = timeEl.value.trim();
    let phone    = phoneEl.value.trim();
    let doctor   = doctorEl.value.trim();
    let email    = emailEl.value.trim();
    let message  = messageEl.value.trim();

    // ✅ Validation
    let hasError = false;

    if (!fullname) { fullnameEl.classList.add("input-error"); hasError = true; }
    if (!gender)   { genderEl.classList.add("input-error"); hasError = true; }
    if (!date)     { dateEl.classList.add("input-error"); hasError = true; }
    if (!time)     { timeEl.classList.add("input-error"); hasError = true; }
    if (!phone)    { phoneEl.classList.add("input-error"); hasError = true; }
    if (!doctor)   { doctorEl.classList.add("input-error"); hasError = true; }
    if (!email)    { emailEl.classList.add("input-error"); hasError = true; }

    if (hasError) {
        showNotification("⚠️ All required fields must be filled!");
        return;
    }

    if (!/^[A-Za-z\s]+$/.test(fullname)) {
        fullnameEl.classList.add("input-error");
        showNotification("⚠️ Full Name should contain alphabets only.");
        return;
    }
    if (!/^[0-9]{10,15}$/.test(phone)) {
        phoneEl.classList.add("input-error");
        showNotification("⚠️ Please enter a valid phone number (10–15 digits).");
        return;
    }
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
        emailEl.classList.add("input-error");
        showNotification("⚠️ Please enter a valid email address.");
        return;
    }

    // ✅ Store locally (optional)
    let appointmentData = { fullname, gender, date, time, phone, doctor, email, message };
    let storedAppointments = JSON.parse(localStorage.getItem("appointments")) || [];
    storedAppointments.push(appointmentData);
    localStorage.setItem("appointments", JSON.stringify(storedAppointments));

    // ✅ If everything is valid → submit form
    document.getElementById("appointmentForm").submit();
}

document.getElementById("AppointmentForm").addEventListener("hidden.bs.modal", function () {
    document.querySelectorAll("#appointmentForm input, #appointmentForm select, #appointmentForm textarea")
        .forEach(el => el.classList.remove("input-error"));
});

function showNotification(message) {
      let notification = document.getElementById("notification");
      notification.innerText = message;

      // Slide in
      notification.style.left = "20px";

      // Auto hide after 3 sec
      setTimeout(() => {
          notification.style.left = "-350px";
      }, 3000);
}

function validateDrugType(inputId, errorId) {
    const inputValue = document.getElementById(inputId).value.trim();
    const errorMsg = document.getElementById(errorId);

    if (inputValue === "") {
        errorMsg.textContent = "";
        return;
    }

    if (!/^[A-Za-z\s]+$/.test(inputValue)) {
        errorMsg.textContent = "⚠️ Please enter alphabets only.";
    } else {
        errorMsg.textContent = "";
    }
}
function validateNumber(inputId,errorId) {
    const drugType = document.getElementById(inputId).value.trim();
    const errorMsg = document.getElementById(errorId);

    if (drugType === "") {
        errorMsg.textContent = "";
        return;
    }

    // Allow only numbers
    if (!/^[0-9]+$/.test(drugType)) {
        errorMsg.textContent = "⚠️ Please enter numbers only.";
    } else {
        errorMsg.textContent = "";
    }
}