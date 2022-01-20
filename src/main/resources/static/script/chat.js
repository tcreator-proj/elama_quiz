

const chat = document.querySelector(".chat-messages");
const jsonArray = request();


async function request() {
    try {
        let json = await fetch("http://localhost:9999/chat", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },

        })
        return json.json();
    } catch (e) {
        console.log(e)
    }
}
jsonArray.then(data => {
    sessionStorage.clear();
    sessionStorage.setItem("quizArray", data);
    sessionStorage.setItem("answers", JSON.stringify({}));
    // chat.insertAdjacentHTML("beforeend", createSenderMessage(data[0]))
})






function createSenderMessage(dataQuiz) {
   const {question, group, next, id, prev, describe, additional, delay, final} = dataQuiz;
   return `<div class="message-box-holder" data-id="${group}" data-next="${next}">
       <div class="message-sender">Elama quiz master</div>
           <div class="message-box message-partner">${question}</div>
        </div>`
}

function createMyMessage(dataQuiz) {
    const {question, group, next, id, prev, describe, additional, delay, final} = dataQuiz;
    return `<div class="message-box-holder" data-id="${group}" data-next="${next}">
        <div class="message-box">${question}</div>
    </div>`
}


//
//
//    className = "message-box-holder" >
//        < div
//    className = "message-sender" >
//        Elama
//    quiz
//    master
//    < /div>

//
