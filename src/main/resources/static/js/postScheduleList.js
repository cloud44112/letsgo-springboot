let searchPostSchedule = document.querySelector("#searchPostSchedule");
if (searchPostSchedule)
    searchPostSchedule.addEventListener("click", fetchSchedules);
let sortSelect = document.querySelector("[name='sortOrder']");
if (sortSelect)
    sortSelect.addEventListener("change", fetchSchedules);

function fetchSchedules(){
    const searchTitle = document.querySelector("[name='searchTitle']").value;
    const sortOrder = document.querySelector("[name='sortOrder']").value;
    fetch(`/postschedule/api/list?sortOrder=${sortOrder}&keyword=${searchTitle}`, {
        method: "get", //헤더에 쿠키안쓰고 뭍히기 가능?
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

function renderPostSchedules(postScheduleList) {
    const container = document.querySelector("#scheduleListContainer");
    if (!container) return;
    container.innerHTML = postScheduleList.map(postSchedule => `
                <figure class="figure" >
                    <div >
                        ${postSchedule.title}
                    </div>
                    
                    <a href="#" class="box-placeholder">
                        <img src="${postSchedule.firstImage}" alt="일정 이미지" class="box-placeholder"/>
                    </a>

                    <figcaption class="figure-caption">
                        ${postSchedule.placeTitle}
                    </figcaption>

                    <div>
                        <button type="button" class="like-btn" data-postId="${postSchedule.postId}">
                            <h1>
                                ❤️
                            </h1>
                        </button>
                        
                        <span class="like-Count" >좋아요 : ${postSchedule.likeCount} </span>
                    </div>
                    
                    <div>
                        <span>조회수 ${postSchedule.viewCount} </span>
                    </div>

                    <div>
                	    <span>
                            📍 ${postSchedule.addr1.substring(0, 10)}
                        </span>
                    </div>
                    
                    <div>
                        👤 ${postSchedule.isAnonymous == 1 ? '익명' : postSchedule.userName}
                    </div>
                </figure>
        `).join("");
}