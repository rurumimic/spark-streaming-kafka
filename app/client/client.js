window.onload = function () {
  var btn = document.getElementById("btn");
  if (btn.addEventListener) {
      btn.addEventListener("click", btnClick, false);
  } else if (btn.attachEvent) {
      btn.attachEvent("onclick", btnClick);
  }
};

function btnClick() {
  let line = document.getElementById("read").value;

  fetch('/subscribe', {
    method: 'POST',
    body: JSON.stringify({
      message: line,
    }),
    headers: {
      'content-type': 'application/json'
    }
  });
}