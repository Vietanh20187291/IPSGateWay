/*
function getKcv(){

}*/

//ZPK
function generateKcv1(){
    let data1 = document.getElementById(
        "inputZpkComponent1").value;
    if(data1.length===32){
        sendKcvData1(data1);
    }
}
function generateKcv2(){
    let data2 = document.getElementById(
        "inputZpkComponent2").value;
    if(data2.length===32){
        sendKcvData2(data2);
    }
}
function generateKcv3(){
    let data3 = document.getElementById(
        "inputZpkComponent3").value;
    if(data3.length===32){
        sendKcvData3(data3);
    }
}
function generateKcvCombined(){
    let dataCombined = document.getElementById(
        "inputZpkCombined").value;
    if(dataCombined.length===32){
        sendKcvDataCombined(dataCombined);
        console.log(dataCombined);
    }
}
function sendKcvData1(data){
    // Creating a XHR object
    let xhr = new XMLHttpRequest();
    let url = "/kcv";
    // Open a connection
    xhr.open("POST", url, true);
    // Set the request header i.e. which type of content you are sending
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");
    // Create a state change callback
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Print received data from server
            document.getElementById("inputZpkKcv1").setAttribute('value', this.responseText);
        }else {
            document.getElementById("inputZpkKcv1").setAttribute('value', "Error");
        }
    };
    xhr.send(data);
}

function sendKcvData2(data){
    // Creating a XHR object
    let xhr = new XMLHttpRequest();
    let url = "/kcv";
    // Open a connection
    xhr.open("POST", url, true);
    // Set the request header i.e. which type of content you are sending
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");
    // Create a state change callback
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Print received data from server
            document.getElementById("inputZpkKcv2").setAttribute('value', this.responseText);
        }else {
            document.getElementById("inputZpkKcv2").setAttribute('value', "Error");
        }
    };
    xhr.send(data);
}

function sendKcvData3(data){
    // Creating a XHR object
    let xhr = new XMLHttpRequest();
    let url = "/kcv";
    // Open a connection
    xhr.open("POST", url, true);
    // Set the request header i.e. which type of content you are sending
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");
    // Create a state change callback
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Print received data from server
            document.getElementById("inputZpkKcv3").setAttribute('value', this.responseText);
        }else {
            document.getElementById("inputZpkKcv3").setAttribute('value', "Error");
        }
    };
    xhr.send(data);
}
function sendKcvDataCombined(data){
    // Creating a XHR object
    let xhr = new XMLHttpRequest();
    let url = "/kcv";
    // Open a connection
    xhr.open("POST", url, true);
    // Set the request header i.e. which type of content you are sending
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");
    // Create a state change callback
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Print received data from server
            document.getElementById("inputZpkKcvCombined").setAttribute('value', this.responseText);
        }else {
            document.getElementById("inputZpkKcvCombined").setAttribute('value', "Error");
        }
    };
    xhr.send(data);
}
function combineKcvKey(){
    var data1 = document.getElementById(
        "inputZpkComponent1").value;
    var data2 = document.getElementById(
        "inputZpkComponent2").value;
    var data3 = document.getElementById(
        "inputZpkComponent3").value;
    console.log(data1);
    console.log(data2);
    console.log(data3);
    document.getElementById("inputZpkCombined").setAttribute('value',  bitwiseXorHexString(data1,data2,data3));
    generateKcvCombined();
    console.log(bitwiseXorHexString(data1,data2,data3));
}

function bitwiseXorHexString(pinBlock1, pinBlock2, pinBlock3) {
    var result = ''
    for (let index = 0; index < 32; index++) {
        const temp = (parseInt(pinBlock1.charAt(index), 16) ^ parseInt(pinBlock2.charAt(index), 32) ^ parseInt(pinBlock3.charAt(index), 32)).toString(32).toUpperCase()
        result += temp
    }
    return result
}
//TCMK
function generateTcmk1(){
    let data1 = document.getElementById(
        "inputTcmkComponent1").value;
    if(data1.length===32){
        sendTcmkData1(data1);
    }
}
function generateTcmk2(){
    let data2 = document.getElementById(
        "inputTcmkComponent2").value;
    if(data2.length===32){
        sendTcmkData2(data2);
    }
}
function generateTcmk3(){
    let data3 = document.getElementById(
        "inputTcmkComponent3").value;
    if(data3.length===32){
        sendTcmkData3(data3);
    }
}
function generateTcmkCombined(){
    let dataCombined = document.getElementById(
        "inputTcmkCombined").value;
    if(dataCombined.length===32){
        sendTcmkDataCombined(dataCombined);
        console.log(dataCombined);
    }
}
function sendTcmkData1(data){
    // Creating a XHR object
    let xhr = new XMLHttpRequest();
    let url = "/kcv";
    // Open a connection
    xhr.open("POST", url, true);
    // Set the request header i.e. which type of content you are sending
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");
    // Create a state change callback
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Print received data from server
            document.getElementById("inputTcmkKcv1").setAttribute('value', this.responseText);
        }else {
            document.getElementById("inputTcmkKcv1").setAttribute('value', "Error");
        }
    };
    xhr.send(data);
}

function sendTcmkData2(data){
    // Creating a XHR object
    let xhr = new XMLHttpRequest();
    let url = "/kcv";
    // Open a connection
    xhr.open("POST", url, true);
    // Set the request header i.e. which type of content you are sending
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");
    // Create a state change callback
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Print received data from server
            document.getElementById("inputTcmkKcv2").setAttribute('value', this.responseText);
        }else {
            document.getElementById("inputTcmkKcv2").setAttribute('value', "Error");
        }
    };
    xhr.send(data);
}

function sendTcmkData3(data){
    // Creating a XHR object
    let xhr = new XMLHttpRequest();
    let url = "/kcv";
    // Open a connection
    xhr.open("POST", url, true);
    // Set the request header i.e. which type of content you are sending
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");
    // Create a state change callback
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Print received data from server
            document.getElementById("inputTcmkKcv3").setAttribute('value', this.responseText);
        }else {
            document.getElementById("inputTcmkKcv3").setAttribute('value', "Error");
        }
    };
    xhr.send(data);
}
function sendTcmkDataCombined(data){
    // Creating a XHR object
    let xhr = new XMLHttpRequest();
    let url = "/kcv";
    // Open a connection
    xhr.open("POST", url, true);
    // Set the request header i.e. which type of content you are sending
    xhr.setRequestHeader("Accept", "application/json");
    xhr.setRequestHeader("Content-Type", "application/json");
    // Create a state change callback
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            // Print received data from server
            document.getElementById("inputTcmkKcvCombined").setAttribute('value', this.responseText);
        }else {
            document.getElementById("inputTcmkKcvCombined").setAttribute('value', "Error");
        }
    };
    xhr.send(data);
}

function combineTcmkKey(){
    var data1 = document.getElementById(
        "inputTcmkComponent1").value;
    var data2 = document.getElementById(
        "inputTcmkComponent2").value;
    var data3 = document.getElementById(
        "inputTcmkComponent3").value;
    console.log(data1);
    console.log(data2);
    console.log(data3);
    document.getElementById("inputTcmkCombined").setAttribute('value',  bitwiseXorHexString(data1,data2,data3));
    generateTcmkCombined();
    console.log(bitwiseXorHexString(data1,data2,data3));
}
/*
function receiveData(){
    let url = "/kcv";
    // Call `fetch()`, passing in the URL.
    fetch(url,{method: 'POST'})
        // fetch() returns a promise. When we have received a response from the server,
        // the promise's `then()` handler is called with the response.
        .then((response) => {
            // Our handler throws an error if the request did not succeed.
            if (!response.ok) {
                throw new Error(`HTTP error: ${response.status}`);
            }
            // Otherwise (if the response succeeded), our handler fetches the response
            // as text by calling response.text(), and immediately returns the promise
            // returned by `response.text()`.
            return response.text();
        })
        // When response.text() has succeeded, the `then()` handler is called with
        // the text, and we copy it into the `poemDisplay` box.
        .then((data) =>  document.getElementById("inputKcv1").setAttribute('value', data))
        // Catch any errors that might happen, and display a message
        // in the `poemDisplay` box.
        .catch((error) =>  document.getElementById("inputKcv1").setAttribute('value', `Could not fetch verse: ${error}`) );
}*/
