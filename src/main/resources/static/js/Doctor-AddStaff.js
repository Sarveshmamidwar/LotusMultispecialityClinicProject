function addEmployee(event) {
    event?.preventDefault();

    // Get form
    const form = document.getElementById("employeeForm");

    // Get elements
    let nameEl     = document.getElementById("name");
    let emailEl    = document.getElementById("email");
    let passwordEl = document.getElementById("password");
    let mobileEl   = document.getElementById("mobileNumber");
    let addressEl  = document.getElementById("address");
    let genderEl   = form.querySelector("input[name='Gender']:checked");

    // Reset old errors
    [nameEl, emailEl, passwordEl, mobileEl, addressEl]
        .forEach(el => el.classList.remove("input-error"));

    document.querySelectorAll("#employeeForm small").forEach(el => el.classList.add("d-none"));

    // Values
    let name     = nameEl.value.trim();
    let email    = emailEl.value.trim();
    let password = passwordEl.value.trim();
    let mobile   = mobileEl.value.trim();
    let address  = addressEl.value.trim();
    let gender   = genderEl ? genderEl.value : "";

    let hasError = false;

    // ✅ Validations
    if (!/^[A-Za-z\s]+$/.test(name)) {
        nameEl.classList.add("input-error");
        document.getElementById("nameError").classList.remove("d-none");
        hasError = true;
    }

    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
        emailEl.classList.add("input-error");
        document.getElementById("emailError").classList.remove("d-none");
        hasError = true;
    }

    if (!password) {
        passwordEl.classList.add("input-error");
        document.getElementById("passwordError").classList.remove("d-none");
        hasError = true;
    }

    if (!/^[0-9]{10,15}$/.test(mobile)) {
        mobileEl.classList.add("input-error");
        document.getElementById("mobileError").classList.remove("d-none");
        hasError = true;
    }

    if (!address) {
        addressEl.classList.add("input-error");
        document.getElementById("addressError").classList.remove("d-none");
        hasError = true;
    }

    if (!gender) {
        document.getElementById("genderError").classList.remove("d-none");
        hasError = true;
    }

    // ❌ Stop if errors exist
    if (hasError) {
        showNotification("⚠️ Please fix the highlighted errors!");
        return;
    }

    // ✅ Submit if valid
    form.submit();
}

// 🟢 Reset borders & errors when modal closes
document.getElementById("exampleModal").addEventListener("hidden.bs.modal", function () {
    document.querySelectorAll("#employeeForm input, #employeeForm textarea").forEach(el => {
        el.classList.remove("input-error");
    });
    document.querySelectorAll("#employeeForm small").forEach(el => el.classList.add("d-none"));
});

document.querySelectorAll("#employeeForm input, #employeeForm textarea").forEach(el => {
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
  
  // ✅ Name: only letters & spaces
  

