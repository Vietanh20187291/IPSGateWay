// //Set parameter to subfields body
// var f45 = [
//     {
//         id: 'F45.01',
//         name: 'Track 1 Format Code (F45.01) '
//     },
//     {
//         id: 'F45.02',
//         name: 'Primary Account Number (F45.02)'
//     },
//     {
//         id: 'F45.03',
//         name: 'Card Holder Name (F45.03)'
//     },
//     {
//         id: 'F45.04',
//         name: 'Expiration Date (F45.04)'
//     }
//     ,
//     {
//         id: 'F45.05',
//         name: 'Service Code (F45.05)'
//     }
//     ,
//     {
//         id: 'F45.06',
//         name: 'PKI Number (F45.06)'
//     }
//     ,
//     {
//         id: 'F45.07',
//         name: 'Discretionary Data (F45.07)'
//     }
//
//
// ]
// var box45 = ''
// f45.map((subfield_info) => {
//     box45 = box45 + `<div className="${subfield_info.id}">
//         <label htmlFor="${subfield_info.id}">${subfield_info.name} :</label>
//         <input class="form-control required-field-45" id="${subfield_info.id}" name="${subfield_info.id}" type="text">
//         </div>`
// })
//
// const subfields_body = box45 + ` <br>
//                                         <div class="form-group">
//                                             <button id="submit-f45" aria-label="Close" class="btn btn-primary" data-dismiss="modal" type="button">
//                                                 Submit
//                                             </button>
//                                         </div>`;
//
// document.getElementById('subfields-body').innerHTML = subfields_body;
//
// var button_subfields_f45 = `<button class="btn btn-outline-secondary float-right m-1" onclick="openSubFieldsF45()"
//                                           type="button">SubFields
//                                     </button>`
//
// document.getElementById('F045-subfields').innerHTML = button_subfields_f45;
//
// //Set parameter to subfields body
// function openSubFieldsF45() {
//     jQuery.noConflict();
//     window.$('#subfieldsId').modal('show');
// }
//
// $("#submit-f45").on("click", function (e) {
//     console.log('submit')
//     var forms = document.querySelectorAll(".required-field-45")
//     console.log(forms.length)
//     var validForm = 0
//     // wait until 5 forms are valid to submit data
//     // Loop over them and prevent submission
//     Array.prototype.slice.call(forms)
//         .forEach(function (form) {
//             form.classList.remove('border-danger')
//             if (form.value == '') {
//                 event.preventDefault()
//                 event.stopPropagation()
//                 form.classList.add('border-danger')
//             } else {
//                 validForm++
//             }
//             if (validForm == 7) {
//                 var f45_01 = document.getElementById('F45.01').value;
//                 var f45_02 = document.getElementById('F45.02').value;
//                 var f45_03 = document.getElementById('F45.03').value;
//                 var f45_04 = document.getElementById('F45.04').value;
//                 var f45_05 = document.getElementById('F45.05').value;
//                 var f45_06 = document.getElementById('F45.06').value;
//                 var f45_07 = document.getElementById('F45.07').value;
//                 var f45 = f45_01 + f45_02 + '^' + f45_03 + '^' + f45_04 + f45_05 + f45_06 + f45_07;
//                 document.getElementById("F045").value = f45;
//             }
//
//         })
//
//
// });