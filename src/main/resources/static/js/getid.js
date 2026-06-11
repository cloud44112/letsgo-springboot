document.getElementById("getIdForm").onsubmit = function (event) {
    event.preventDefault();

    var name = document.getElementById("nameInput").value;
    var email = document.getElementById("emailInput").value;
    var message = document.getElementById("getIdMessage");
    var found = document.getElementById("foundIdMessage");

    message.style.display = "none";
    found.style.display = "none";
    message.innerText = "";
    found.innerText = "";

    if (name.trim() == "" || email.trim() == "") {
        message.style.display = "block";
        message.innerText = "이름과 이메일을 입력해주세요.";
        return;
    }

    fetch("/user/api/getIdAjax", {
        method: "POST",
        headers: { "Content-Type": "application/x-www-form-urlencoded" },
        body: "name=" + encodeURIComponent(name)
            + "&email=" + encodeURIComponent(email)
    }).then(response => response.json())
        .then(data => {
            if (data.result == "success") {
                found.style.display = "block";
                found.innerText = "회원님의 아이디는 \"" + data.userId + "\" 입니다.";
            } else {
                message.style.display = "block";
                message.innerText = data.message;
            }
        }).catch(error => {
        console.error("getid error", error);
        message.style.display = "block";
        message.innerText = "아이디 찾기 오류.";
    });
};