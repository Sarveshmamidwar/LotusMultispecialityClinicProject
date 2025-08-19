function searchPatient() {
	    const name = document.getElementById("searchPatient").value.trim();

	    if (name.length === 0) {
	        location.reload(); // reload full table
	        return;
	    }

	    fetch(`/recption/searchPatient?name=${encodeURIComponent(name)}`)
	        .then(res => {
	            if (!res.ok) throw new Error("No patients found");
	            return res.json();
	        })
	        .then(patients => {
	            const tbody = document.getElementById("patientTableBody");
	            tbody.innerHTML = "";

	            patients.forEach(patient => {
	                tbody.innerHTML += `
	                    <tr>
	                        <td>${patient.id}</td>
	                        <td>${patient.name}</td>
	                        <td>${patient.email}</td>
	                        <td>${patient.number}</td>
	                        <td>${patient.gender}</td>
	                        <td>${patient.address}</td>
	                        <td>${patient.birthdate}</td>
	                    </tr>
	                `;
	            });
	        })
	        .catch(err => {
	            document.getElementById("patientTableBody").innerHTML = `
	                <tr><td colspan="7" class="text-danger text-center">No patients found</td></tr>
	            `;
	        });
	}
	
	function searchDrug() {
	    const searchTerm = document.getElementById("searchDrug").value.trim();
	    const tableBody = document.getElementById("medicinesTableBody");

	    if (searchTerm.length > 0) {
	        fetch(`/recption/search-tablets?term=${encodeURIComponent(searchTerm)}`)
	            .then(res => res.json())
	            .then(response => {
	                tableBody.innerHTML = ""; // Clear table

	                if (response.length > 0) {
	                    response.forEach(c => {
	                        const row = `
	                            <tr>
	                                <th>${c.id}</th>
	                                <td>${c.tabletName}</td>
	                                <td>${c.medicineCompanyName}</td>
	                                <td>${c.medecineType}</td>
	                                <td>
	                                    <div style="width: 100px; display: flex; justify-content: space-around; align-items: center;">
	                                        <img alt="Edit" src="/images/edit.png" width="15%" 
	                                            onclick="editMedicineModal('${c.id}', '${c.tabletName}', '${c.medicineCompanyName}', '${c.medecineType}')"
	                                            data-bs-toggle="modal" data-bs-target="#editmedicinesmodal">
	                                        <img alt="Delete" src="/images/delete.png" width="18%" onclick="deleteMedicine('${c.id}')">
	                                    </div>
	                                </td>
	                            </tr>
	                        `;
	                        tableBody.innerHTML += row;
	                    });
	                } else {
	                    tableBody.innerHTML = `<tr><td colspan="5" class="text-center text-danger">No medicines found</td></tr>`;
	                }
	            })
	            .catch(() => {
	                tableBody.innerHTML = `<tr><td colspan="5" class="text-center text-danger">Error fetching data</td></tr>`;
	            });
	    } else {
	        // If input is empty -> reload all medicines (default list)
	        location.reload();
	    }
	}
	
	function searchDrugDoc() {
		    const searchTerm = document.getElementById("doctorsearchDrug").value.trim();
		    const tableBody = document.getElementById("medicinesTableBodyDoc");

		    if (searchTerm.length > 0) {
		        fetch(`/doctors/search-tablets?term=${encodeURIComponent(searchTerm)}`)
		            .then(res => res.json())
		            .then(response => {
		                tableBody.innerHTML = ""; // Clear table

		                if (response.length > 0) {
		                    response.forEach(c => {
		                        const row = `
		                            <tr>
		                                <th>${c.id}</th>
		                                <td>${c.tabletName}</td>
		                                <td>${c.medicineCompanyName}</td>
		                                <td>${c.medecineType}</td>
		                                <td>
		                                    <div style="width: 100px; display: flex; justify-content: space-around; align-items: center;">
		                                        <img alt="Edit" src="/images/edit.png" width="15%" 
		                                            onclick="editMedicineModal('${c.id}', '${c.tabletName}', '${c.medicineCompanyName}', '${c.medecineType}')"
		                                            data-bs-toggle="modal" data-bs-target="#editmedicinesmodal">
		                                        <img alt="Delete" src="/images/delete.png" width="18%" onclick="deleteMedicine('${c.id}')">
		                                    </div>
		                                </td>
		                            </tr>
		                        `;
		                        tableBody.innerHTML += row;
		                    });
		                } else {
		                    tableBody.innerHTML = `<tr><td colspan="5" class="text-center text-danger">No medicines found</td></tr>`;
		                }
		            })
		            .catch(() => {
		                tableBody.innerHTML = `<tr><td colspan="5" class="text-center text-danger">Error fetching data</td></tr>`;
		            });
		    } else {
		        // If input is empty -> reload all medicines (default list)
		        location.reload();
		    }
		}
		
		function searchPatientDoc() {
			    const name = document.getElementById("searchPatientDoc").value.trim();

			    if (name.length === 0) {
			        location.reload(); // reload full table
			        return;
			    }

			    fetch(`/doctors/searchPatient?name=${encodeURIComponent(name)}`)
			        .then(res => {
			            if (!res.ok) throw new Error("No patients found");
			            return res.json();
			        })
			        .then(patients => {
			            const tbody = document.getElementById("patientTableBodyDoc");
			            tbody.innerHTML = "";

			            patients.forEach(patient => {
			                tbody.innerHTML += `
			                    <tr>
			                        <td>${patient.id}</td>
			                        <td>${patient.name}</td>
			                        <td>${patient.email}</td>
			                        <td>${patient.number}</td>
			                        <td>${patient.gender}</td>
			                        <td>${patient.address}</td>
			                        <td>${patient.birthdate}</td>
			                    </tr>
			                `;
			            });
			        })
			        .catch(err => {
			            document.getElementById("patientTableBodyDoc").innerHTML = `
			                <tr><td colspan="7" class="text-danger text-center">No patients found</td></tr>
			            `;
			        });
			}
			
			function searchAppointmentDoc(apiUrl, inputId, tableBodyId) {
				const name = document.getElementById(inputId).value.trim();
				const tbody = document.getElementById(tableBodyId);

			    if (name.length === 0) {
			        location.reload(); // reload full table if empty search
			        return;
			    }
///doctors/searchAppointment?name=${encodeURIComponent(name)}
			    fetch(`${apiUrl}?name=${encodeURIComponent(name)}`)
			        .then(res => res.json())
			        .then(appointments => {
			            tbody.innerHTML = ""; // clear table

			            if (appointments.length > 0) {
			                appointments.forEach(app => {
			                    tbody.innerHTML += `
			                        <tr>
			                            <td>${app.id}</td>
			                            <td>${app.name}</td>
			                            <td>${app.appointmentDate}</td>
			                            <td>${app.appointmentTime}</td>
			                            <td>${app.doctor}</td>
			                            <td>${app.gender}</td>
			                            <td>
			                                <div class="col-auto appointment-status-container p-0 m-0">
			                                    <button class="btn btn-secondary dropdown-toggle ${app.appointmentStatus === 'Done' ? 'btn-success' : (app.appointmentStatus === 'Cancel' ? 'btn-danger' : (app.appointmentStatus === 'Pending' ? 'btn-warning' : ''))}" 
			                                        onclick="toggleDropdown(${app.id}, event)">
			                                        ${app.appointmentStatus}
			                                    </button>
			                                </div>
			                                <div class="dropdown-menu dropdown p-2" style="display:none;">
			                                    <button class="p-2 btn btn-warning" onclick="updateStatus('Pending', ${app.id}, event)">Pending</button>
			                                    <button class="mt-1 p-2 btn btn-danger cancelbtn" onclick="updateStatus('Cancel', ${app.id}, event)">Cancel</button>
			                                    <button class="mt-1 p-2 btn btn-success donebtn" onclick="updateStatus('Done', ${app.id}, event)">Done</button>
			                                </div>
			                            </td>
			                            <td>
			                                <div class="btn-group">
			                                    <button id="action-btn-${app.id}" type="button"
			                                        class="btn btn-primary dropdown-toggle"
			                                        data-bs-toggle="dropdown" aria-expanded="false">
			                                        <span>${app.paymentStatus != null ? app.paymentStatus : 'pending'}</span>
			                                    </button>
			                                    <ul class="dropdown-menu">
			                                        <li class="p-1"><button type="button" class="btn btn-secondary w-100"
			                                            onclick="updatePaymentStatusdoctor('CASH', ${app.id})">CASH</button></li>
			                                        <li class="p-1"><button type="button" class="btn btn-secondary w-100"
			                                            onclick="updatePaymentStatusdoctor('CARD', ${app.id})">CARD</button></li>
			                                        <li class="p-1"><button type="button" class="btn btn-secondary w-100"
			                                            onclick="updatePaymentStatusdoctor('UPI', ${app.id})">UPI</button></li>
			                                    </ul>
			                                </div>
			                            </td>
			                            <td style="width: 10%">
			                                <a href="/doctors/Prescription/${app.name}/${app.gender}">
			                                    <img src="/images/prescription.png" alt="Prescription" style="width: 30%">
			                                </a>
			                            </td>
			                        </tr>
			                    `;
			                });
			            } else {
			                tbody.innerHTML = `
			                    <tr><td colspan="9" class="text-danger text-center">No appointments found</td></tr>
			                `;
			            }
			        })
			        .catch(() => {
			            tbody.innerHTML = `
			                <tr><td colspan="9" class="text-danger text-center">Error fetching appointments</td></tr>
			            `;
			        });
			}
			
			function searchAllAppointmentDoc(apiUrl, inputId, tableBodyId) {
			    const name = document.getElementById(inputId).value.trim();
			    const tbody = document.getElementById(tableBodyId);

			    if (name.length === 0) {
			        location.reload(); // reload full table if empty search
			        return;
			    }

			    fetch(`${apiUrl}?name=${encodeURIComponent(name)}`)
			        .then(res => res.json())
			        .then(appointments => {
			            tbody.innerHTML = ""; // clear table

			            if (appointments.length > 0) {
			                appointments.forEach(app => {
			                    tbody.innerHTML += `
			                        <tr>
			                            <td>${app.id}</td>
			                            <td>${app.name}</td>
			                            <td>${app.appointmentDate}</td>
			                            <td>${app.appointmentTime}</td>
			                            <td>${app.doctor}</td>
			                            <td>${app.gender}</td>
			                        </tr>
			                    `;
			                });
			            } else {
			                tbody.innerHTML = `
			                    <tr><td colspan="6" class="text-danger text-center">No appointments found</td></tr>
			                `;
			            }
			        })
			        .catch(() => {
			            tbody.innerHTML = `
			                <tr><td colspan="6" class="text-danger text-center">Error fetching appointments</td></tr>
			            `;
			        });
			}


						function searchCancleAppointmentDoc(apiUrl, inputId, tableBodyId) {
						    const name = document.getElementById(inputId).value.trim();
						    const tbody = document.getElementById(tableBodyId);

						    if (name.length === 0) {
						        location.reload(); // reload full table if empty search
						        return;
						    }

						    fetch(`${apiUrl}?name=${encodeURIComponent(name)}`)
						        .then(res => res.json())
						        .then(appointments => {
						            tbody.innerHTML = ""; // clear table

						            if (appointments.length > 0) {
						                appointments.forEach(app => {
						                    tbody.innerHTML += `
						                        <tr>
						                            <td>${app.id}</td>
						                            <td>${app.name}</td>
						                            <td>${app.appointmentDate}</td>
						                            <td>${app.appointmentTime}</td>
						                            <td>${app.doctor}</td>
						                            <td>${app.gender}</td>
						                        </tr>
						                    `;
						                });
						            } else {
						                tbody.innerHTML = `
						                    <tr><td colspan="6" class="text-danger text-center">No appointments found</td></tr>
						                `;
						            }
						        })
						        .catch(() => {
						            tbody.innerHTML = `
						                <tr><td colspan="6" class="text-danger text-center">Error fetching appointments</td></tr>
						            `;
						        });
						}

												
												function searchTommAppointmentDoc(apiUrl, inputId, tableBodyId) {
												    const name = document.getElementById(inputId).value.trim();
												    const tbody = document.getElementById(tableBodyId);

												    if (name.length === 0) {
												        location.reload(); // reload full table if empty search
												        return;
												    }

												    fetch(`${apiUrl}?name=${encodeURIComponent(name)}`)
												        .then(res => res.json())
												        .then(appointments => {
												            tbody.innerHTML = ""; // clear table

												            if (appointments.length > 0) {
												                appointments.forEach(app => {
												                    tbody.innerHTML += `
												                        <tr>
												                            <td>${app.id}</td>
												                            <td>${app.name}</td>
												                            <td>${app.appointmentDate}</td>
												                            <td>${app.appointmentTime}</td>
												                            <td>${app.doctor}</td>
												                            <td>${app.gender}</td>
												                        </tr>
												                    `;
												                });
												            } else {
												                tbody.innerHTML = `
												                    <tr><td colspan="6" class="text-danger text-center">No appointments found</td></tr>
												                `;
												            }
												        })
												        .catch(() => {
												            tbody.innerHTML = `
												                <tr><td colspan="6" class="text-danger text-center">Error fetching appointments</td></tr>
												            `;
												        });
												}
