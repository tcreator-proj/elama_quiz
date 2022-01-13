(async function() {
    let body = {
        name: "Vladislav",
        counter: 1
    }
    let bodies = document.body;
    bodies.addEventListener("click", async (event) => {
        try {
            let json = await fetch("http://localhost:8080", {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json;charset=utf-8'
                },
                body: JSON.stringify(body)
            })
            let returned = await json.json()
            body.counter = returned.counter;

            console.log(body.counter)
        } catch (e) {
            console.log(e)
        }

    })



})()