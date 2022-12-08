
function roleCheck(){
    var role = document.getElementById("role").value;
    if(role=="true"){
        removeHost();
    }else{
        removeVts();
    }
    console.log(role);
}

function removeVts() {
    document.getElementById("vtsInput").style.display='none';
    document.getElementById("hostInput").style.display='';
    document.getElementById("clientNum").style.display='none';
}

function removeHost(){
    document.getElementById("vtsInput").style.display='';
    document.getElementById("hostInput").style.display='none';
    document.getElementById("clientNum").style.display='';
}

