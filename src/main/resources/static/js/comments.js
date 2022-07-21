const routeId = document.getElementById('routeId').value
const commentForm = document.findElemntById('commentForm')
commentForm.addEventListener("submit", handleFormSubmission)

async function handleFormSubmission(event) {
    event.preventDefault()

    const messageVal = document.getElementById('message').value

    fetch(`http://localhost:8080/api/${routeId}/comments`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accepts': 'application/json'
        },
        body: JSON.stringify({
            message: messageVal
        })
    }).then(res => res.json())
      .then(data => {
        console.log(data)
      })
}