const navBtns = document.querySelectorAll(".nav-btn");

navBtns.forEach(btn => {
    btn.addEventListener("click", () => {
        navBtns.forEach(b => b.classList.remove("active"));
        btn.classList.add("active");

        currentFilter = btn.dataset.filter;
        fetchSchedules();
    });
});


function showPanel(panel) {
    const template = document.querySelector(`#tpl-${panel}`);
    contentRight.replaceChildren(template.content.cloneNode(true));

    if (panel === "route") renderRoute();
    if (panel === "budget") renderBudget();
    if (panel === "todo") renderTodo();
}

function fetchRoute(){
    // const userId = document.querySelector("#userId").value;
    const sortOrder = document.querySelector("#sortOrder").value;
    const keyword = document.querySelector("#keyword").value;

    const url = currentFilter === "user"
        ? `/postschedule/api/mylist?userId=${userId}&sortOrder=${sortOrder}&keyword=${keyword}`
        : `/postschedule/api/list?sortOrder=${sortOrder}&keyword=${keyword}`;
    console.log(url);
    fetch(url, {
        method: "get",
        headers: {
            "Content-Type": "application/json"
        },
    }).then((result)=> {
        return result.json();
    }).then((data) => {
        renderPostSchedules(data);
    }).catch(error=>{
        console.error("실패 " + error);
    });
}

function fetchBudget(){
    // const userId = document.querySelector("#userId").value;
    const sortOrder = document.querySelector("#sortOrder").value;
    const keyword = document.querySelector("#keyword").value;

    const url = currentFilter === "user"
        ? `/postschedule/api/mylist?userId=${userId}&sortOrder=${sortOrder}&keyword=${keyword}`
        : `/postschedule/api/list?sortOrder=${sortOrder}&keyword=${keyword}`;
    console.log(url);
    fetch(url, {
        method: "get",
        headers: {
            "Content-Type": "application/json"
        },
    }).then((result)=> {
        return result.json();
    }).then((data) => {
        renderPostSchedules(data);
    }).catch(error=>{
        console.error("실패 " + error);
    });
}

function fetchToDo(){
    // const userId = document.querySelector("#userId").value;
    const sortOrder = document.querySelector("#sortOrder").value;
    const keyword = document.querySelector("#keyword").value;

    const url = currentFilter === "user"
        ? `/postschedule/api/mylist?userId=${userId}&sortOrder=${sortOrder}&keyword=${keyword}`
        : `/postschedule/api/list?sortOrder=${sortOrder}&keyword=${keyword}`;
    console.log(url);
    fetch(url, {
        method: "get",
        headers: {
            "Content-Type": "application/json"
        },
    }).then((result)=> {
        return result.json();
    }).then((data) => {
        renderPostSchedules(data);
    }).catch(error=>{
        console.error("실패 " + error);
    });
}