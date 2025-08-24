document.addEventListener("DOMContentLoaded", function () {
       loadTableData(); // Load stored data when page loads
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

   function addPrescription() {
       // Get input values
       let srNo = document.getElementById("srNo").value.trim();
       let prescription = document.getElementById("prescription").value.trim();
       let drugName = document.getElementById("drugName1").value.trim();
       let schedule = document.getElementById("schedule").value.trim();
       let duration = document.getElementById("duration").value.trim();
       let qty = document.getElementById("qty").value.trim();

       // ✅ Validation
       if (!srNo || !prescription || !drugName || !schedule || !duration || !qty) {
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

       // Duration must be only numbers
       if (!/^[0-9]+$/.test(duration)) {
           showNotification("⚠️ Duration should contain numbers only.");
           return;
       }

       // ✅ Create a row object
       let rowData = { srNo, prescription, drugName, schedule, duration, qty };

       // Get existing data from localStorage
       let storedData = JSON.parse(localStorage.getItem("prescriptions")) || [];

       // Add new row to stored data
       storedData.push(rowData);

       // Save updated data to localStorage
       localStorage.setItem("prescriptions", JSON.stringify(storedData));

       // Append row to table
       appendRowToTable(rowData);

       // Clear input fields
       document.getElementById("srNo").value = "";
       document.getElementById("prescription").value = "";
       document.getElementById("drugName1").value = "";
       document.getElementById("schedule").value = "";
       document.getElementById("duration").value = "";
       document.getElementById("qty").value = "";
   }



   function appendRowToTable(rowData) {
       let tableBody = document.getElementById("prescriptionTableBody");
       let newRow = document.createElement("tr");

       // Generate stepper for table row
       let stepperHtml = `
           <div class="stepper">
             <div class="step ${rowData.schedule >= 1 ? "active" : ""}"></div>
             <div class="line ${rowData.schedule >= 2 ? "active" : ""}"></div>
             <div class="step ${rowData.schedule >= 2 ? "active" : ""}"></div>
             <div class="line ${rowData.schedule >= 3 ? "active" : ""}"></div>
             <div class="step ${rowData.schedule >= 3 ? "active" : ""}"></div>
           </div>
       `;

       newRow.innerHTML = `
           <td>${rowData.srNo}</td>
           <td>${rowData.prescription}</td>
           <td>${rowData.drugName}</td>
           <td>${stepperHtml}</td>
           <td>${rowData.duration} Days</td>
           <td>${rowData.qty}</td>
           <td>
             <i class="fa-solid fa-trash delete-icon" style="color: #fa0000; cursor: pointer;"></i>
           </td>
       `;

       tableBody.appendChild(newRow);

       // ✅ Add delete functionality with localStorage update
       newRow.querySelector(".delete-icon").addEventListener("click", function () {
           // 1. Remove row from DOM
           newRow.remove();

           // 2. Remove row from localStorage
           let storedData = JSON.parse(localStorage.getItem("prescriptions")) || [];
           let updatedData = storedData.filter(item => item.srNo !== rowData.srNo);
           localStorage.setItem("prescriptions", JSON.stringify(updatedData));
       });
   }



   function loadTableData() {
       let storedData = JSON.parse(localStorage.getItem("prescriptions")) || [];
       let tableBody = document.getElementById("prescriptionTableBody");
       tableBody.innerHTML = ""; // Clear table before adding stored rows

       storedData.forEach(rowData => {
           appendRowToTable(rowData);
       });
   }

   document.getElementById("clearTable").addEventListener("click", function () {
       localStorage.removeItem("prescriptions"); // Remove data from storage
       document.getElementById("prescriptionTableBody").innerHTML = ""; // Clear table
   });
   
   function clearTableData() {
       localStorage.removeItem("prescriptions"); // Remove stored data
       document.getElementById("prescriptionTableBody").innerHTML = ""; // Clear table
       updateTotalAmount(); // Reset total amount
   }

   
   
   function generatePdf(reportName = "prescription") {
       const name = document.getElementById('patientName').value.trim();
       const age = document.getElementById('patientAge').value.trim();
       const dob = document.getElementById('patientDob').value.trim();
       const gender = document.getElementById('patientGender').value.trim();
       const weight = document.getElementById('patientWeight').value.trim();
       const address = document.getElementById('patientAddress').value.trim();
       const tableRows = document.querySelectorAll('#prescriptionTableBody tr');
       const patientId = document.getElementById("patientId").innerText.trim();

       // ✅ Required field validation
       if (!name) { showNotification("⚠️ Patient Name is required"); return; }
       if (!age) { showNotification("⚠️ Age is required"); return; }
       if (!dob) { showNotification("⚠️ Date of Birth is required"); return; }
       if (!gender) { showNotification("⚠️ Gender is required"); return; }
       if (!weight) { showNotification("⚠️ Weight is required"); return; }
       if (!address) { showNotification("⚠️ Address is required"); return; }
       if (tableRows.length === 0) { showNotification("⚠️ Add at least one prescription row"); return; }

       // ✅ Regex validation
       if (!/^[A-Za-z\s]+$/.test(name)) {
           showNotification("⚠️ Patient Name should contain alphabets only.");
           return;
       }
       if (!/^[0-9]+$/.test(age)) {
           showNotification("⚠️ Age should contain numbers only.");
           return;
       }
       if (!/^[A-Za-z\s]+$/.test(gender)) {
           showNotification("⚠️ Gender should contain alphabets only.");
           return;
       }
       if (!/^[0-9]+$/.test(weight)) {
           showNotification("⚠️ Weight should contain numbers only.");
           return;
       }

       // 🔽 Original code unchanged from here 🔽
       const element = document.getElementById('pdfContent');
       const drSection = document.querySelector('.drsection');
       const precard = document.querySelector('.precard');

       // Convert inputs → spans (for PDF only)
       const inputs = element.querySelectorAll('input, textarea');
       const originalStates = [];
       inputs.forEach(input => {
           const span = document.createElement("span");
           span.textContent = input.value || input.placeholder || "";
           span.style.padding = "2px 4px";
           originalStates.push({ input, span });
           input.replaceWith(span);
       });

       // PDF styles
       drSection.style.fontSize = '0.5rem';
       precard.style.border = 'none';
       element.style.width = '100%';
       element.style.maxWidth = '210mm';
       element.style.marginTop = '10px';

       // Generate PDF
       html2pdf().from(element).set({
           margin: 0,
           image: { type: 'jpeg', quality: 0.98 },
           html2canvas: { scale: 2, scrollY: 0 },
           jsPDF: { unit: 'mm', format: 'a4', orientation: 'portrait' }
       }).outputPdf('blob')
       .then((pdfBlob) => {
           // ✅ Open PDF in new tab for printing
           const pdfUrl = URL.createObjectURL(pdfBlob);
           window.open(pdfUrl); // Opens PDF in new tab

           // ✅ Upload PDF to server
           const pdfFile = new File([pdfBlob], reportName + ".pdf", { type: "application/pdf" });
           const formData = new FormData();
           formData.append("report", pdfFile);
           formData.append("reportName", reportName);

           fetch(`/doctors/addReports/${patientId}`, {
               method: "POST",
               body: formData
           })
           .then(res => {
               if (res.ok) {
                   showNotification(`✅ ${reportName} saved successfully!`);
               } else {
                   showNotification("❌ Failed to upload report.");
               }
           })
           .catch(err => {
               console.error("❌ Upload error:", err);
               showNotification("❌ Error while uploading report.");
           })
           .finally(() => {
               // Restore inputs
               originalStates.forEach(({ input, span }) => {
                   span.replaceWith(input);
               });

               setTimeout(() => {
                   clearTableData();
                   window.location.reload();
               }, 500);
           });
       });
   }





   
   $(document).ready(function () {
       $("#drugName1").on("input", function () {
           let searchTerm = $(this).val().trim();
           let suggestionsDiv = $("#suggestions");

           if (searchTerm.length > 0) {
               $.ajax({
                   url: "/doctors/search-tablets",
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

   document.querySelectorAll(".step").forEach((dot, index, dots) => {
   	  dot.addEventListener("click", () => {
   	    // reset all dots and lines
   	    dots.forEach((d, i) => {
   	      d.classList.remove("active");
   	      if (i < dots.length - 1) {
   	        document.querySelectorAll(".line")[i].classList.remove("active");
   	      }
   	    });

   	    // activate up to clicked dot
   	    for (let i = 0; i <= index; i++) {
   	      dots[i].classList.add("active");
   	      if (i < index) {
   	        document.querySelectorAll(".line")[i].classList.add("active");
   	      }
   	    }

   	    // set hidden input value
   	    document.getElementById("schedule").value = dot.dataset.step;
   	  });
   	});
	
	
	const steps = document.querySelectorAll(".step");
	 const scheduleInput = document.getElementById("schedule");
	 const durationInput = document.getElementById("duration");
	 const qtyInput = document.getElementById("qty");

	 // Step click handler
	 

	 // Duration input handler
	 durationInput.addEventListener("input", calculateQty);

	 function calculateQty() {
	   let schedule = parseInt(scheduleInput.value) || 0;
	   let duration = parseInt(durationInput.value) || 0;
	   qtyInput.value = schedule * duration;
	 }
	 
	 function validateSrNo() {
	     const srNo = document.getElementById("srNo").value.trim();
	     const errorMsg = document.getElementById("srNoError");

	     if (srNo === "") {
	         errorMsg.textContent = "";
	         return;
	     }

	     if (!/^\d+$/.test(srNo)) {
	         errorMsg.textContent = "⚠️ Please enter numbers only.";
	     } else {
	         errorMsg.textContent = "";
	     }
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

