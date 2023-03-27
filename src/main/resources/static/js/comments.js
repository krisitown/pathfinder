const backendLocation = 'http://localhost:8080'

let routeId = document.getElementById("routeId").getAttribute("value")
let commentSection = document.getElementById("commentCtnr")

fetch(`${backendLocation}/api/${routeId}/comments`)
    .then((response) => response.json())
    .then((body) => {
        for(comment of body) {
            addCommentAsHtml(comment)
        }
    })

function addCommentAsHtml(comment) {
    let commentHtml = `<div class="comments" id="comment${comment.id}">\n`
    commentHtml += '<div hidden>' + comment.id + '</div>'
    commentHtml += '<h4>' + comment.authorName + '</h4>\n'
    commentHtml += '<p>' + comment.text + '</p>\n'
    commentHtml += '<span>' + comment.dateOfCreation + '</span>\n'

    if(comment.canEdit) {
        commentHtml += `<button class="btn btn-danger" onclick="deleteComment(${comment.id})">Delete</button>\n`
    }
    
    commentHtml += '</div>\n'

    commentSection.innerHTML += commentHtml
}

function deleteComment(commentId) {
    fetch(`${backendLocation}/api/${routeId}/comments/${commentId}`, {
        method: 'DELETE',
        headers: {
            [csrfHeaderName]: csrfHeaderValue
        }
    }).then(res => {
        console.log(res)
        document.getElementById("comment" + commentId).remove()
    })
}

const csrfHeaderName = document.getElementById('csrf').getAttribute('name')
const csrfHeaderValue = document.getElementById('csrf').getAttribute('value')

let commentForm = document.getElementById("commentForm")
commentForm.addEventListener("submit", (event) => {
    event.preventDefault()
    
    let text = document.getElementById("message").value

    fetch(`${backendLocation}/api/${routeId}/comments`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            [csrfHeaderName]: csrfHeaderValue
        },
        body: JSON.stringify({
            text: text   
        })
    }).then((res) => {
        document.getElementById("message").value = ""
        let location = res.headers.get("Location")
        fetch(`${backendLocation}${location}`)
            .then(res => res.json())
            .then(body => addCommentAsHtml(body))
    })
})