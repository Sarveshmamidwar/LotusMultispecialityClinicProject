document.addEventListener("DOMContentLoaded", function () {
    loadTableData(); // Load stored data when page loads
    updateTotalAmount(); // Update total amount on page load
});

function addPrescription() {
    // Get input values
    let srNo = document.getElementById("srNo").value.trim();
    let prescription = document.getElementById("prescription").value.trim();
    let drugName = document.getElementById("drugName1").value.trim();
    let schedule = document.getElementById("price").value.trim();
    let qty = document.getElementById("qty").value.trim();

    // ✅ Validation
    if (!srNo || !prescription || !drugName || !schedule || !qty) {
        showNotification("⚠️ All Prescription fields are required!");
        return;
    }

    // Sr No must be only numbers
    if (!/^[0-9]+$/.test(srNo)) {
        showNotification("⚠️ Sr No should contain numbers only.");
        return;
    }

    // Drug Type must be only alphabets
    if (!/^[A-Za-z\s]+$/.test(prescription)) {
        showNotification("⚠️ Drug Type should contain alphabets only.");
        return;
    }

    // Price must be only numbers (or decimal)
    if (!/^[0-9]+(\.[0-9]+)?$/.test(schedule)) {
        showNotification("⚠️ Schedule should be a valid number.");
        return;
    }

    // Qty must be only numbers
    if (!/^[0-9]+$/.test(qty)) {
        showNotification("⚠️ Quantity should contain numbers only.");
        return;
    }

    // ✅ Calculate total
    const total = qty * schedule;

    // ✅ Create row object
    let rowData = { srNo, prescription, drugName, schedule, qty, total };

    // Get existing data from localStorage
    let storedData = JSON.parse(localStorage.getItem("Invoiceprescriptions")) || [];

    // Add new row
    storedData.push(rowData);

    // Save updated data
    localStorage.setItem("Invoiceprescriptions", JSON.stringify(storedData));

    // Append row to table
    appendRowToTable(rowData);
    updateTotalAmount(); // Update total after adding row

    // Clear fields
    document.getElementById("srNo").value = "";
    document.getElementById("prescription").value = "";
    document.getElementById("drugName1").value = "";
    document.getElementById("price").value = "";
    document.getElementById("qty").value = "";
}


function appendRowToTable(rowData) {
    let tableBody = document.getElementById("InvoiceprescriptionTableBody");
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
    let storedData = JSON.parse(localStorage.getItem("Invoiceprescriptions")) || [];
    let tableBody = document.getElementById("InvoiceprescriptionTableBody");
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
    document.getElementById("totalAmount").value = totalAmount.toFixed(2); // Show total with 2 decimal places
}

function clearTableData() {
    localStorage.removeItem("Invoiceprescriptions"); // Remove stored data
    document.getElementById("InvoiceprescriptionTableBody").innerHTML = ""; // Clear table
    updateTotalAmount(); // Reset total amount
}

function showNotification(message) {
    let notify = document.getElementById("errorNotify");
    document.getElementById("errorMessage").innerText = message;
    notify.classList.add("show");

    // Auto hide after 4 sec
    setTimeout(() => {
        notify.classList.remove("show");
    }, 4000);
}

function closeNotification() {
    document.getElementById("errorNotify").classList.remove("show");
}


function generatePDF() {
    const patientName = document.getElementById("billname").value.trim();
    const patientAge = document.getElementById("patage").value.trim();
    const patientAddress = document.getElementById("pataddress").value.trim();
    const tableRows = document.querySelectorAll("#InvoiceprescriptionTableBody tr");

	let errors = [];

	    if (!patientName) errors.push("Patient Name is required.");
	    if (!patientAge) errors.push("Age is required.");
	    if (!patientAddress) errors.push("Address is required.");
	    if (tableRows.length === 0) errors.push("At least one prescription is required.");

	    if (errors.length > 0) {
	        showNotification(errors.join("\n")); // ✅ show red popup
	        return; // ❌ stop execution
	    }

    // ✅ Continue PDF generation only if validation passes
    const element = document.getElementById('InvoicepdfContent');
    const drSection = document.querySelector('.drsection'); 
    const precard = document.querySelector('.precard');
    const logo = document.querySelector('.prelogo');
    const deleteIcons = document.querySelectorAll('.delete-icon'); 
    const tableContainer = document.querySelector('.prescriptiontable');

    tableContainer.style.maxHeight = 'none';
    tableContainer.style.overflowY = 'visible';

    const inputs = element.querySelectorAll('input, textarea');
    inputs.forEach(input => {
        const span = document.createElement('span');
        span.textContent = input.value || ''; 
        span.style.fontWeight = 'bold';
        span.style.marginLeft = '10px';
        span.style.textTransform = 'capitalize';
        input.parentNode.insertBefore(span, input);
        input.style.display = 'none';
    });

    deleteIcons.forEach(icon => icon.style.display = 'none');
    drSection.style.fontSize = '0.5rem';
    precard.style.border = 'none';
    element.style.width = '100%';
    element.style.maxWidth = '210mm';
    logo.style.width = '40%';

	html2pdf().from(element).set({
	    margin: 0,
	    image: { type: 'jpeg', quality: 0.98 },
	    html2canvas: { scale: 2, scrollY: 0 },
	    jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }
	}).save('invoice.pdf')
	.then(() => {
	    // ✅ Loop through prescription table rows
	    const rows = document.querySelectorAll("#InvoiceprescriptionTableBody tr");
	    rows.forEach(row => {
	        const drugName = row.cells[2].innerText.trim();  // Drug name column
	        const qty = parseInt(row.cells[3].innerText.trim()); // Qty column

	        if (drugName && qty) {
	            fetch(`/recption/update-quantity?drugName=${encodeURIComponent(drugName)}&qty=${qty}`, {
	                method: "PUT"
	            })
	            .then(res => res.text())
	            .then(msg => console.log("✅ Stock updated:", msg))
	            .catch(err => console.error("❌ Error updating stock:", err));
	        }
	    });

		
	    // After updating stock, clear & reload
	    setTimeout(() => {
			document.getElementById("formsubmit").click();
	        clearTableData();
	       
	    }, 800);
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
                        let list = $("<div></div>");
                        response.forEach(function (tablet) {
                            let itemContent = `
                                <div class="icon">💊</div>
                                <div>
                                    <strong>${tablet.tabletName}</strong>
                                    <small>ID: ${tablet.id || 'N/A'} | Price: ${tablet.price || '0'}</small>
                                </div>
                            `;
                            let item = $("<div class='suggestion-item'></div>")
                                .html(itemContent)
                                .on("click", function () {
                                    // Fill drug name
                                    $("#drugName1").val(tablet.tabletName);
                                    // Fill price
                                    $("#price").val(tablet.price || 0);
                                    // Hide suggestions
                                    suggestionsDiv.empty().hide();
                                });
                            list.append(item);
                        });

                        suggestionsDiv.html(list).show();
                    } else {
                        suggestionsDiv.hide();
                    }
                }
            });
        } else {
            suggestionsDiv.empty().hide();
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
				
				
				
				document.addEventListener("DOMContentLoaded", function () {
				    let today = new Date();

				    let year = today.getFullYear();
				    let month = String(today.getMonth() + 1).padStart(2, "0");
				    let day = String(today.getDate()).padStart(2, "0");

				    let formattedDate = `${year}-${month}-${day}`; // Format: YYYY-MM-DD
				    document.getElementById("todate").value = formattedDate;
				});
		