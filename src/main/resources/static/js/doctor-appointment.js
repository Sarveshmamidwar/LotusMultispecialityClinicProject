function appoIDPrecreption(name, age, address, gender) {
    document.getElementById("patientName").value = name;
    document.getElementById("patientAge").value = age;
    document.getElementById("patientAddress").value = address;
    document.getElementById("patientGender").value = gender;

    // Auto-fill today's date
    let today = new Date().toISOString().split('T')[0];
    document.getElementById("patientDate").value = today;
}
