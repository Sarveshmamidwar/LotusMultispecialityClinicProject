function addreportmodel(patientid){
	 document.getElementById("patientid").value = patientid;
}
document.getElementById("reportForm").addEventListener("submit", function(event) {
	    let patientId = document.getElementById("patientid").value;
	    if (patientId) {
	      this.action = "/doctors/addReports/" + patientId;  // overwrite action dynamically
	    }
	  });
	  
function patientAppointmentBook(patientId,patientName,patientGender,patientEmail,patientMnumber) {
    // Set the hidden field value inside modal form
    document.getElementById("patientId").value = patientId;
    document.getElementById("fullname").value = patientName;
    document.getElementById("gender").value = patientGender;
    document.getElementById("phone").value = patientMnumber;
    document.getElementById("email").value = patientEmail;
    
}

function addPatient(event) {
    event?.preventDefault();

    // Get input elements
    let fullnameEl = document.getElementById("patientName");
    let emailEl    = document.getElementById("patientEmail");
    let phoneEl    = document.getElementById("patientNumber");
    let maleEl     = document.getElementById("malePatient");
    let femaleEl   = document.getElementById("femalePatient");
    let birthEl    = document.getElementById("birthDate");
    let addressEl  = document.getElementById("patientAddress");

    // Remove old error styles
    [fullnameEl, emailEl, phoneEl, birthEl, addressEl]
        .forEach(el => el.classList.remove("input-error"));

    let fullname = fullnameEl.value.trim();
    let email    = emailEl.value.trim();
    let phone    = phoneEl.value.trim();
    let gender   = maleEl.checked ? "MALE" : (femaleEl.checked ? "FEMALE" : "");
    let birth    = birthEl.value.trim();
    let address  = addressEl.value.trim();

    // ✅ Validation
    let hasError = false;

    if (!fullname) { fullnameEl.classList.add("input-error"); hasError = true; }
    if (!email)    { emailEl.classList.add("input-error"); hasError = true; }
    if (!phone)    { phoneEl.classList.add("input-error"); hasError = true; }
    if (!gender)   { hasError = true; }
    if (!birth)    { birthEl.classList.add("input-error"); hasError = true; }
    if (!address)  { addressEl.classList.add("input-error"); hasError = true; }

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

    // ✅ Store locally (optional, like appointment)
    let patientData = { fullname, email, phone, gender, birth, address };
    let storedPatients = JSON.parse(localStorage.getItem("patients")) || [];
    storedPatients.push(patientData);
    localStorage.setItem("patients", JSON.stringify(storedPatients));

    // ✅ If everything is valid → submit form
    document.querySelector("#exampleModal form").submit();
}

// Clear errors when modal closes
document.getElementById("exampleModal").addEventListener("hidden.bs.modal", function () {
    document.querySelectorAll("#exampleModal input, #exampleModal select, #exampleModal textarea")
        .forEach(el => el.classList.remove("input-error"));
});
document.querySelectorAll("#exampleModal input, #exampleModal textarea").forEach(el => {
    el.addEventListener("input", () => {
        el.classList.remove("input-error"); // remove red border
        let errorEl = el.parentElement.querySelector("small");
        if (errorEl) errorEl.classList.add("d-none"); // hide error message
    });
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