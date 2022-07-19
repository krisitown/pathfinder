const routeId = document.getElementById('routeId').value

fetch(`http://localhost:8080/api/${routeId}/comments`)
    .then((res) => {
        let response = res.json()
        for(let comment in response) {
            console.log(comment)
        }
    });

const commentForm = document.getElementById('commentForm')
commentForm.addEventListener("submit", handleCommentSubmit)

const csrfHeaderName = document.head.querySelector('[name="_csrf_header"]').content;
const csrfHeaderValue = document.head.querySelector('[name="_csrf"]').content;

async function handleCommentSubmit(event) {
    event.preventDefault();
    let bodyObj = {message: document.getElementById('message').value}

    fetch(`http://localhost:8080/api/${routeId}/comments`, {method: "POST", headers: {"Accept": "application/json", "Content-type": "application/json", 
        [csrfHeaderName] :  csrfHeaderValue, }, body: JSON.stringify(bodyObj)})
        .then((res) => console.log(res.json()));
}