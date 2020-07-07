window.onload = function () {
	fetch("https://us-central1-ip-checker-7ecce.cloudfunctions.net/getIP").then(response => response.text())
  	.then(result => document.getElementsByTagName('h2')[0].innerHTML = result)
}