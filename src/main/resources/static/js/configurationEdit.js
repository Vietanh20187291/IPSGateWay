function roleCheck(){
    var role = document.getElementById("role").value;

    console.log(role);
}
function validateForm(){
    if(role=="true"){
        validateIpsForm();
    }else{
        validateHostForm();
    }
}

function validateIpsForm() {
    var ipsPort = document.getElementById("inputIpsPort").value;
    if (ipsPort == null) {
        alert("Please Fill IPS Port");
        return false;
    }
}
function validateHostForm() {
    var hostPort = document.getElementById("inputHostPort").value;
    var hostIp = document.getElementById("inputHostIp").value;
    if (hostPort == null) {
        alert("Please Fill Host Port");
        return false;
    }
    if (hostIp == null) {
        alert("Please Fill Host IP");
        return false;
    }
}




