document.addEventListener("DOMContentLoaded", function () {
				    let today = new Date();

				    let year = today.getFullYear();
				    let month = String(today.getMonth() + 1).padStart(2, "0");
				    let day = String(today.getDate()).padStart(2, "0");

				    let formattedDate = `${year}-${month}-${day}`; // Format: YYYY-MM-DD
				    document.getElementById("todate").value = formattedDate;
				});


function addStockMedice() {
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

    let storedData = JSON.parse(localStorage.getItem("Invoiceprescriptions")) || [];
    storedData.push(rowData);
    localStorage.setItem("Invoiceprescriptions", JSON.stringify(storedData));

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
    document.getElementById("totalAmount").value = totalAmount.toFixed(2);
	document.getElementById("StockFullAmount").value = totalAmount.toFixed(2);
	 // Show total with 2 decimal places
}

function clearStockTableData() {
    localStorage.removeItem("Invoiceprescriptions"); // Remove stored data
    document.getElementById("InvoiceprescriptionTableBody").innerHTML = ""; // Clear table
    updateTotalAmount(); // Reset total amount
}

/*Doctor*/
function generateStockInvoicePDF(reportName = "invoice") {
    const employeeName = document.getElementById("billname").value.trim();
	const employeeid = document.getElementById("employeeid").value.trim();
    const stockDate = document.getElementById("todate").value.trim();
    const totalAmount = parseFloat(document.getElementById("StockFullAmount").value.trim()) || 0;
    const tableRows = document.querySelectorAll("#InvoiceprescriptionTableBody tr");

    // ✅ Validation
    let errors = [];
    if (!employeeName) errors.push("⚠️ Employee Name is required.");
    if (!stockDate) errors.push("⚠️ Date is required.");
    if (tableRows.length === 0) errors.push("⚠️ At least one prescription is required.");
    if (errors.length > 0) {
        showNotification(errors.join("\n"));
        return;
    }

    // ✅ Continue PDF generation only if validation passes
    const element = document.getElementById('InvoicepdfContent');
    const drSection = document.querySelector('.drsection'); 
    const precard = document.querySelector('.precard');
    const logo = document.querySelector('.prelogo');
    const deleteIcons = document.querySelectorAll('.delete-icon'); 
    const tableContainer = document.querySelector('.prescriptiontable');

    // Make table scroll-free for PDF
    tableContainer.style.maxHeight = 'none';
    tableContainer.style.overflowY = 'visible';

    // Replace inputs → spans
	// Replace only visible inputs → spans
	const inputs = element.querySelectorAll('input:not([type="hidden"]), textarea');
	const originalStates = [];

	inputs.forEach(input => {
	    const span = document.createElement("span");
	    span.textContent = input.value || input.placeholder || "";
	    span.style.padding = "2px 4px";
	    originalStates.push({ input, span });
	    input.replaceWith(span);
	});


    // PDF styling
    deleteIcons.forEach(icon => icon.style.display = 'none');
    drSection.style.fontSize = '0.5rem';
    precard.style.border = 'none';
    element.style.width = '100%';
    element.style.maxWidth = '210mm';
    logo.style.width = '40%';

    // ✅ Generate PDF blob
    html2pdf().from(element).set({
        margin: 0,
        image: { type: 'jpeg', quality: 0.98 },
        html2canvas: { scale: 2, scrollY: 0 },
        jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }
    }).outputPdf('blob')
    .then((pdfBlob) => {
        // ❌ Removed: window.open(pdfUrl);

        // ✅ Upload PDF + invoice data to server
        const pdfFile = new File([pdfBlob], reportName + ".pdf", { type: "application/pdf" });
        const formData = new FormData();
        formData.append("file", pdfFile);

        // ✅ Append StockInvoice form fields
        formData.append("EmployeeName", employeeName);
        formData.append("StockUpdateDate", stockDate);
        formData.append("stockAmount", Math.round(totalAmount).toString());
		formData.append("employeeid", employeeid);

		fetch("/doctors/genrateStockInvoice", {
		    method: "POST",
		    body: formData
		})
		.then(res => {
		    if (res.ok) {
		        updateStockFromTable();

		        let notify = document.getElementById("errorNotify");
		        notify.classList.add("show");
		        document.querySelector("#errorNotify h4").innerText = "✅ Success";
		        document.getElementById("errorMessage").innerText = "Stock Invoice PDF generated successfully!";

		        // ✅ Clear table + reload after success
		        setTimeout(() => {
		            notify.classList.remove("show");
		            clearTableData();
		            window.location.reload();
		        }, 4000);
		    } else {
		        showNotification("❌ Failed to save invoice.");
		    }
		})
		.catch(err => {
		    console.error("❌ Upload error:", err);
		    showNotification("❌ Error while saving invoice.");
		})
		.finally(() => {
		    // ✅ Only restore inputs here
		    originalStates.forEach(({ input, span }) => {
		        span.replaceWith(input);
		    });
		});

    });
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

function generateStockInvoicePDFs(reportName = "invoice") {
    const employeeName = document.getElementById("billname").value.trim();
	const employeeid = document.getElementById("employeeid").value.trim();
    const stockDate = document.getElementById("todate").value.trim();
    const totalAmount = parseFloat(document.getElementById("StockFullAmount").value.trim()) || 0;
    const tableRows = document.querySelectorAll("#InvoiceprescriptionTableBody tr");

    // ✅ Validation
    let errors = [];
    if (!employeeName) errors.push("⚠️ Employee Name is required.");
    if (!stockDate) errors.push("⚠️ Date is required.");
    if (tableRows.length === 0) errors.push("⚠️ At least one prescription is required.");
    if (errors.length > 0) {
        showNotification(errors.join("\n"));
        return;
    }

    // ✅ Continue PDF generation only if validation passes
    const element = document.getElementById('InvoicepdfContent');
    const drSection = document.querySelector('.drsection'); 
    const precard = document.querySelector('.precard');
    const logo = document.querySelector('.prelogo');
    const deleteIcons = document.querySelectorAll('.delete-icon'); 
    const tableContainer = document.querySelector('.prescriptiontable');

    // Make table scroll-free for PDF
    tableContainer.style.maxHeight = 'none';
    tableContainer.style.overflowY = 'visible';

    // Replace inputs → spans
	// Replace only visible inputs → spans
	const inputs = element.querySelectorAll('input:not([type="hidden"]), textarea');
	const originalStates = [];

	inputs.forEach(input => {
	    const span = document.createElement("span");
	    span.textContent = input.value || input.placeholder || "";
	    span.style.padding = "2px 4px";
	    originalStates.push({ input, span });
	    input.replaceWith(span);
	});


    // PDF styling
    deleteIcons.forEach(icon => icon.style.display = 'none');
    drSection.style.fontSize = '0.5rem';
    precard.style.border = 'none';
    element.style.width = '100%';
    element.style.maxWidth = '210mm';
    logo.style.width = '40%';

    // ✅ Generate PDF blob
    html2pdf().from(element).set({
        margin: 0,
        image: { type: 'jpeg', quality: 0.98 },
        html2canvas: { scale: 2, scrollY: 0 },
        jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }
    }).outputPdf('blob')
    .then((pdfBlob) => {
        // ❌ Removed: window.open(pdfUrl);

        // ✅ Upload PDF + invoice data to server
        const pdfFile = new File([pdfBlob], reportName + ".pdf", { type: "application/pdf" });
        const formData = new FormData();
        formData.append("file", pdfFile);

        // ✅ Append StockInvoice form fields
        formData.append("EmployeeName", employeeName);
        formData.append("StockUpdateDate", stockDate);
        formData.append("stockAmount", Math.round(totalAmount).toString());
		formData.append("employeeid", employeeid);

        fetch("/recption/genrateStockInvoice", {
            method: "POST",
            body: formData
        })
        .then(res => {
        	if (res.ok) {
				
				updateStockFromTablerep();
				
        	    let notify = document.getElementById("errorNotify");
        	    notify.classList.add("show");
        	    document.querySelector("#errorNotify h4").innerText = "✅ Success";
        	    document.getElementById("errorMessage").innerText = "Stock Invoice PDF generated successfully!";

        	    // Auto hide success after 4 sec
        	    setTimeout(() => {
        	        notify.classList.remove("show");
        	        clearTableData();
        	        window.location.reload();
        	    }, 4000);
        	} else {
        	    showNotification("❌ Failed to save invoice.");
        	}

        })
        .catch(err => {
            console.error("❌ Upload error:", err);
            showNotification("❌ Error while saving invoice.");
        })
        .finally(() => {
            // Restore inputs
            originalStates.forEach(({ input, span }) => {
                span.replaceWith(input);
            });

            setTimeout(() => {
                clearTableData();
                window.location.reload();
            }, 4000);
        });
    });
}

function updateStockFromTable() {
    let storedData = JSON.parse(localStorage.getItem("Invoiceprescriptions")) || [];

    // ✅ Group by drugName and sum qty
    let qtyMap = {};
    storedData.forEach(rowData => {
        if (!qtyMap[rowData.drugName]) {
            qtyMap[rowData.drugName] = 0;
        }
        qtyMap[rowData.drugName] += parseInt(rowData.qty) || 0;
    });

    // ✅ Send only one request per drug
    Object.entries(qtyMap).forEach(([drugName, totalQty]) => {
        fetch(`/doctors/increase-quantity?drugName=${encodeURIComponent(drugName)}&qty=${totalQty}`, {
            method: "PUT"
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to update stock for " + drugName);
            }
            return response.text();
        })
        .then(data => {
            console.log("✅ Success:", data);
        })
        .catch(error => {
            console.error("❌ Error:", error);
        });
    });

    // ✅ Clear localStorage after update (prevents double addition)
    localStorage.removeItem("Invoiceprescriptions");
}

function updateStockFromTablerep() {
    let storedData = JSON.parse(localStorage.getItem("Invoiceprescriptions")) || [];

    // ✅ Group by drugName and sum qty
    let qtyMap = {};
    storedData.forEach(rowData => {
        if (!qtyMap[rowData.drugName]) {
            qtyMap[rowData.drugName] = 0;
        }
        qtyMap[rowData.drugName] += parseInt(rowData.qty) || 0;
    });

    // ✅ Send only one request per drug
    Object.entries(qtyMap).forEach(([drugName, totalQty]) => {
        fetch(`/recption/increase-quantity?drugName=${encodeURIComponent(drugName)}&qty=${totalQty}`, {
            method: "PUT"
        })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to update stock for " + drugName);
            }
            return response.text();
        })
        .then(data => {
            console.log("✅ Success:", data);
        })
        .catch(error => {
            console.error("❌ Error:", error);
        });
    });

    // ✅ Clear localStorage after update (prevents double addition)
    localStorage.removeItem("Invoiceprescriptions");
}

