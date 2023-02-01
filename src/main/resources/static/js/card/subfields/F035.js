//Set parameter to subfields body
var button_subfields_f035 = `<button class="btn btn-outline-secondary float-right m-1" onclick="openSubFieldsF035()"
                                      type="button">SubFields
                                </button>`

document.getElementById('F035-subfields').innerHTML = button_subfields_f035;


function openSubFieldsF035() {
    var f035_value = document.getElementById('F035').value;
    generateSubfields35Value(f035_value);
    generateSubFieldsModalF35()
    generateSubFieldsModalBody()
    jQuery.noConflict()
    var Modal = new bootstrap.Modal(document.getElementById('subfieldsF35'))
    Modal.show()
}

var f035 = [
    {
        id: 'F035.01',
        name: 'Primary Account Number (F035.01)',
        value:''
    },
    {
        id: 'F035.02',
        name: 'Expiration Date (F035.02)',
        value:''
    },
    {
        id: 'F035.03',
        name: 'Service Code (F035.03)',
        value:''
    },
    {
        id: 'F035.04',
        name: 'PKI Number (F035.04)',
        value:''
    }
    ,
    {
        id: 'F035.05',
        name: 'Discretionary Data (F035.05)',
        value:''
    }

]
function generateSubFieldsModalF35(){
    var modal = `                         
                         <div aria-hidden="true" aria-labelledby="exampleModalLabel2" class="modal fade" id="subfieldsF35"
                             role="dialog" tabindex="-1">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel2">Subfields</h5>
                                        <button type="button" class="btn-close border-0" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body" id="subfields-body-F035">
                                    </div>
                                </div>
                            </div>
                        </div>`
    document.getElementById('subfields-modal-F035').innerHTML = modal;
}

function generateSubFieldsModalBody() {


    var box35 = ''
    f035.map((subfield_info) => {
        box35 = box35 + `<div className="${subfield_info.id}">
    <label htmlFor="${subfield_info.id}">${subfield_info.name} :</label>
    <input class="form-control required-field-35" id="${subfield_info.id}" name="${subfield_info.id}" type="text" value="${subfield_info.value}">
    </div>`
    })

    const subfields_body = box35 + ` <br>
                                    <div class="form-group">
                                                    <button id="submit-f035" type="button" class="btn btn-primary">Submit</button>
                                    </div>`;

    document.getElementById('subfields-body-F035').innerHTML = subfields_body;
    setSubmitF035()
}




function generateSubfields35Value(f035_value){
    if(f035_value.length>21) {
        let count  = 0
        for(var i = 0; i<f035_value.length;i++){
            if(f035_value[i] =='='){
                count++
            }
        }
        if(count == 1) {

            f035[0].value = f035_value.substring(0, f035_value.indexOf('='));
            f035[1].value = f035_value.substring(f035_value.indexOf('=') + 1, f035_value.indexOf('=') + 5);
            f035[2].value = f035_value.substring(f035_value.indexOf('=') + 5, f035_value.indexOf('=') + 8);
            f035[3].value = f035_value.substring(f035_value.indexOf('=') + 8, f035_value.indexOf('=') + 9);
            f035[4].value = f035_value.substring(f035_value.indexOf('=') + 9, f035_value.length);

        }
    }


}
function setSubmitF035() {
    $("#submit-f035").on("click", function (e) {
        var forms = document.querySelectorAll(".required-field-35")
        var validForm = 0
        // wait until 5 forms are valid to submit data
        // Loop over them and prevent submission
        Array.prototype.slice.call(forms)
            .forEach(function (form) {
                form.classList.remove('border-danger')
                if (form.value == '') {
                    event.preventDefault()
                    event.stopPropagation()
                    form.classList.add('border-danger')
                } else {
                    validForm++
                }
                if (validForm == 5) {
                    var f035_01 = document.getElementById('F035.01').value;
                    var f035_02 = document.getElementById('F035.02').value;
                    var f035_03 = document.getElementById('F035.03').value;
                    var f035_04 = document.getElementById('F035.04').value;
                    var f035_05 = document.getElementById('F035.05').value;
                    var f035 = f035_01 + '=' + f035_02 + f035_03 + f035_04 + f035_05;
                    document.getElementById("F035").value = f035;
                    var Modal = document.getElementById('subfieldsF35');
                    var modal = bootstrap.Modal.getInstance(Modal)
                    modal.hide();
                }

            })


    });
}