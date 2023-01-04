function roleCheck(){
    var role = document.getElementById("role").value;
    if(role=="true"){
        removeHost();
    }else{
        removeIps();
    }
    console.log(role);
}
function validateForm(){
    if(role=="true"){
        validateIpsForm();
    }else{
        validateHostForm();
    }
}

function removeIps() {
    document.getElementById("ipsInput").style.display='none';
    document.getElementById("hostInput").style.display='';
    document.getElementById("clientNum").style.display='none';
}

function removeHost(){
    document.getElementById("ipsInput").style.display='';
    document.getElementById("hostInput").style.display='none';
    document.getElementById("clientNum").style.display='';
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

