document.addEventListener("DOMContentLoaded", function () {
    loadTableData(); // Load stored data when page loads
    updateTotalAmount(); // Update total amount on page load
});

function addPrescription() {
    let srNo = document.getElementById("srNo").value;
    let prescription = document.getElementById("prescription").value;
    let drugName = document.getElementById("drugName1").value;
    let schedule = document.getElementById("price").value;
    let qty = document.getElementById("qty").value;

    if (!srNo || !prescription || !drugName || !schedule || !qty) {
        alert("Please fill all fields before adding.");
        return;
    }

    const total = qty * schedule;
    console.log("total", total);

    let rowData = { srNo, prescription, drugName, schedule, total, qty };

    let storedData = JSON.parse(localStorage.getItem("prescriptions")) || [];
    storedData.push(rowData);
    localStorage.setItem("prescriptions", JSON.stringify(storedData));

    appendRowToTable(rowData);
    updateTotalAmount(); // Update total after adding a row

    // Clear input fields after adding
    document.getElementById("srNo").value = "";
    document.getElementById("prescription").value = "";
    document.getElementById("drugName1").value = "";
    document.getElementById("price").value = "";
    document.getElementById("qty").value = "";
}

function appendRowToTable(rowData) {
    let tableBody = document.getElementById("prescriptionTableBody");
    let newRow = document.createElement("tr");

    newRow.innerHTML = `
        <td>${rowData.srNo}</td>
        <td>${rowData.prescription}</td>
        <td>${rowData.drugName}</td>
        <td>${rowData.qty}</td>
        <td>${rowData.schedule}</td>
        <td class="totalColumn">${rowData.total}
            <i class="fa-solid fa-trash delete-icon" style="color: #fa0000; cursor: pointer; margin-left: 10px;"></i>
        </td>
    `;

    tableBody.appendChild(newRow);

    // Attach event listener to the delete icon
    newRow.querySelector(".delete-icon").addEventListener("click", function() {
        newRow.remove(); // Remove the row from the table
        updateTotalAmount(); // Update total amount after deletion
    });

    updateTotalAmount(); // Update total amount whenever a new row is added
}


function loadTableData() {
    let storedData = JSON.parse(localStorage.getItem("prescriptions")) || [];
    let tableBody = document.getElementById("prescriptionTableBody");
    tableBody.innerHTML = ""; 

    storedData.forEach(rowData => {
        appendRowToTable(rowData);
    });

    updateTotalAmount(); // Update total amount after loading data
}

function updateTotalAmount() {
    let totalAmount = 0;
    document.querySelectorAll(".totalColumn").forEach(td => {
        totalAmount += parseFloat(td.textContent) || 0;
    });
    document.getElementById("totalAmount").textContent = totalAmount.toFixed(2); // Show total with 2 decimal places
}

function clearTableData() {
    localStorage.removeItem("prescriptions"); // Remove stored data
    document.getElementById("prescriptionTableBody").innerHTML = ""; // Clear table
    updateTotalAmount(); // Reset total amount
}

function generatePDF() {
    const element = document.getElementById('pdfContent');
    const drSection = document.querySelector('.drsection'); 
    const precard = document.querySelector('.precard');
    const logo = document.querySelector('.prelogo');
    const deleteIcons = document.querySelectorAll('.delete-icon'); 
    const tableContainer = document.querySelector('.prescriptiontable'); // Get table container

    // Expand table to show all rows
    tableContainer.style.maxHeight = 'none';
    tableContainer.style.overflowY = 'visible';

    // Get all input fields
    const inputs = element.querySelectorAll('input, textarea');
    inputs.forEach(input => {
        const span = document.createElement('span');
        span.textContent = input.value || ''; 
        span.style.fontWeight = 'bold';
        span.style.marginLeft = '10px';
        span.style.textTransform = 'capitalize';
        input.parentNode.insertBefore(span, input);
        input.style.marginLeft = '15px';
        input.style.display = 'none';
    });

    // Hide all delete icons
    deleteIcons.forEach(icon => {
        icon.style.display = 'none';
    });

    drSection.style.fontSize = '0.5rem';
    precard.style.border = 'none';
    element.style.width = '100%';
    element.style.maxWidth = '210mm';
    element.style.marginTop = '10px';
    logo.style.width = '40%';

    html2pdf().from(element).set({
        margin: 0,
        image: { type: 'jpeg', quality: 0.98 },
        html2canvas: { scale: 2, scrollY: 0 },
        jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }
    }).outputPdf('blob').then((pdfBlob) => {
        const pdfUrl = URL.createObjectURL(pdfBlob);
        const newWindow = window.open(pdfUrl);
        if (newWindow) {
            newWindow.onload = function () {
                newWindow.print();
            };
        } else {
            alert("Please allow pop-ups to print the document.");
        }

        // Restore inputs and remove spans after PDF is generated
        inputs.forEach(input => {
            input.style.display = '';
            input.previousSibling.remove();
        });

        // Show delete icons again
        deleteIcons.forEach(icon => {
            icon.style.display = '';
        });

        // Reset table container height
        tableContainer.style.maxHeight = '300px';
        tableContainer.style.overflowY = 'auto';

        // Reset styles
        element.style.width = '';
        element.style.maxWidth = '';
        element.style.minHeight = '';
        element.style.padding = '';
    });
}

$(document).ready(function () {
    $("#drugName1").on("input", function () {
        let searchTerm = $(this).val().trim();
        let suggestionsDiv = $("#suggestions");

        if (searchTerm.length > 0) {
            $.ajax({
                url: "/recption/search-tablets",
                type: "GET",
                data: { term: searchTerm },
                success: function (response) {
                    console.log("Response Data:", response); // Debugging
                    suggestionsDiv.empty(); // Clear previous suggestions

                    if (response.length > 0) {
                        let list = $("<ul class='list-group'></ul>");
                        response.forEach(function (tablet) {
                            let itemContent = `<strong>${tablet.tabletName}</strong>`; // Corrected syntax
                            let item = $("<li class='list-group-item suggestion-item'></li>")
                                .html(itemContent)
                                .on("click", function () {
                                    $("#drugName1").val(tablet.tabletName); // Set input field value
                                    suggestionsDiv.empty().hide(); // Hide suggestions after selection
                                });
                            list.append(item);
                        });

                        suggestionsDiv.append(list).show(); // Show suggestions when data is present
                    } else {
                        suggestionsDiv.hide(); // Hide if no results
                    }
                }
            });
        } else {
            suggestionsDiv.empty().hide(); // Hide when input is empty
        }
    });
});


function validateText() {
            let input = document.getElementById("prescription");
            let errorMessage = document.getElementById("error-message");
            
            // Regular expression to allow only letters and spaces
            let regex = /^[A-Za-z\s]+$/;
            
            if (!regex.test(input.value)) {
				errorMessage.textContent = "Enter text only";
                errorMessage.style.display = "inline";
            } else {
                errorMessage.style.display = "none";
            }
        }
		
		function validateNumber(inputId, errorId) {
		            let input = document.getElementById(inputId);
		            let errorMessage = document.getElementById(errorId);
		            
		            // Regular expression to allow only numbers
		            let regex = /^[0-9]*$/;

		            if (!regex.test(input.value)) {
		                errorMessage.textContent = "Enter number only"; // Set error message dynamically
		                errorMessage.style.display = "inline";
		            } else {
		                errorMessage.style.display = "none";
		            }
		        }