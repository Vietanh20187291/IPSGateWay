//Set parameter to subfields body
var button_subfields_f045 = `<button class="btn btn-outline-secondary float-right m-1" onclick="openSubFieldsF045()"
                                      type="button">SubFields
                                </button>`

document.getElementById('F045-subfields').innerHTML = button_subfields_f045;

function openSubFieldsF045() {
    var f045_value = document.getElementById('F045').value;
    generateSubfields45Value(f045_value);
    generateSubFieldsModalF45();
    generateSubFieldsModalBodyF45();
    jQuery.noConflict();
    var Modal = new bootstrap.Modal(document.getElementById('subfieldsF45'))
    Modal.show()
}

var f045 = [
    {
        id: 'F045.01',
        name: 'Track 1 Format Code (F045.01) ',
        value:''
    },
    {
        id: 'F045.02',
        name: 'Primary Account Number (F045.02)',
        value:''
    },
    {
        id: 'F045.03',
        name: 'Card Holder Name (F045.03)',
        value:''
    },
    {
        id: 'F045.04',
        name: 'Expiration Date (F045.04)',
        value:''
    }
    ,
    {
        id: 'F045.05',
        name: 'Service Code (F045.05)',
        value:''
    }
    ,
    {
        id: 'F045.06',
        name: 'PKI Number (F045.06)',
        value:''
    }
    ,
    {
        id: 'F045.07',
        name: 'Discretionary Data (F045.07)',
        value:''
    }

]
function generateSubFieldsModalF45(){
    var modal = `                         <div aria-hidden="true" aria-labelledby="exampleModalLabel1" class="modal fade" id="subfieldsF45"
                             role="dialog" tabindex="-1">
                            <div class="modal-dialog" role="document">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel1">Subfields</h5>
                                        <button type="button" class="btn-close border-0" data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body" id="subfields-body-F045">
                                    </div>
                                </div>
                            </div>
                        </div>`
    document.getElementById('subfields-modal-F045').innerHTML = modal;
}
function generateSubFieldsModalBodyF45() {

    var box45 = ''
    f045.map((subfield_info) => {
        box45 = box45 + `<div className="${subfield_info.id}">
    <label htmlFor="${subfield_info.id}">${subfield_info.name} :</label>
    <input class="form-control required-field-45" id="${subfield_info.id}" name="${subfield_info.id}" type="text" value="${subfield_info.value}">
    </div>`
    })

    const subfields_body = box45 + ` <br>
                                    <div class="form-group">
                                                    <button id="submit-f045" type="button" class="btn btn-primary">Submit</button>
                                    </div>`;
    document.getElementById('subfields-body-F045').innerHTML = subfields_body;
    setSubmitF045()
}




function generateSubfields45Value(f045_value){
    if(f045_value.length>25) {
        let count  = 0
        for(var i = 0; i<f045_value.length;i++){
            if(f045_value[i] =='^'){
                count++
            }
        }
        if(count == 2) {

            f045[0].value = f045_value.substring(0, 1);
            f045[1].value = f045_value.substring(1, f045_value.indexOf('^'));
            f045[2].value = f045_value.substring(f045_value.indexOf('^') + 1, f045_value.lastIndexOf('^'));
            f045[3].value = f045_value.substring(f045_value.lastIndexOf('^') + 1, f045_value.lastIndexOf('^') + 5);
            f045[4].value = f045_value.substring(f045_value.lastIndexOf('^') + 5, f045_value.lastIndexOf('^') + 8);
            f045[5].value = f045_value.substring(f045_value.lastIndexOf('^') + 8, f045_value.lastIndexOf('^') + 9);
            f045[6].value = f045_value.substring(f045_value.lastIndexOf('^') + 9, f045_value.length);

        }
    }


}
function setSubmitF045() {
    $("#submit-f045").on("click", function (e) {
        var forms = document.querySelectorAll(".required-field-45")
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
                if (validForm == 7) {
                    var f045_01 = document.getElementById('F045.01').value;
                    var f045_02 = document.getElementById('F045.02').value;
                    var f045_03 = document.getElementById('F045.03').value;
                    var f045_04 = document.getElementById('F045.04').value;
                    var f045_05 = document.getElementById('F045.05').value;
                    var f045_06 = document.getElementById('F045.06').value;
                    var f045_07 = document.getElementById('F045.07').value;
                    var f045 = f045_01 + f045_02 + '^' + f045_03 + '^' + f045_04 + f045_05 + f045_06 + f045_07;
                    document.getElementById("F045").value = f045;
                    var Modal = document.getElementById('subfieldsF45');
                    var modal = bootstrap.Modal.getInstance(Modal)
                    modal.hide();
                }

            })


    });
}