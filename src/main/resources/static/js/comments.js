const routeId = document.getElementById('routeId').value
const commentForm = document.getElementById('commentForm')
commentForm.addEventListener("submit", handleFormSubmission)

const csrfHeaderName = document.head.querySelector('[name=_csrf_header]').content
const csrfHeaderValue = document.head.querySelector('[name=_csrf]').content

const commentContaier = document.getElementById('commentCtnr')

let allComments = []
const maxComments = 2

async function handleFormSubmission(event) {
    event.preventDefault()

    const messageVal = document.getElementById('message').value

    fetch(`http://localhost:8080/api/${routeId}/comments`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json',
            [csrfHeaderName]: csrfHeaderValue
        },
        body: JSON.stringify({
            message: messageVal
        })
    }).then(res => res.json())
      .then(data => {
        allComments.push(data)
        document.getElementById('message').value = ""
        // commentContaier.innerHTML += comementAsHtml(data)
        displayComments()
      })
}

function comementAsHtml(comment, visible, num) {
    let commentHtml = `<div ${visible ? "" : "style='display: none'"} id="comment${num}">\n`
    commentHtml += `<h4>${comment.authorName}</h4>\n`
    commentHtml += `<p>${comment.message}</p>\n`
    commentHtml += '</div>\n'

    return commentHtml
}

fetch(`http://localhost:8080/api/${routeId}/comments`, {
    headers: {
        "Accept": "application/json"
    }
}).then(res => res.json())
    .then(data => {
        for(let comment of data) {
            allComments.push(comment)
            // commentContaier.innerHTML += comementAsHtml(comment)
            displayComments()
        }
    })

function displayComments() {
    commentContaier.innerHTML = ""

    for(let i = 0; i < allComments.length; i++) {
        if(i < maxComments) {
            commentContaier.innerHTML += comementAsHtml(allComments[i], true, i)
        } else {
            commentContaier.innerHtml += comementAsHtml(allComments[i], false, i)
        }
    }

    if(allComments.length > maxComments) {
        commentContaier.innerHTML += '<button id="showmore_btn" onclick="showmore()"> Show more </button>'
    }

    commentContaier.innerHTML += '<button hidden id="showless_btn" onclick="showless()"> Show less </button>'
}

function showmore() {
    for(let i = maxComments - 1; i < allComments.length; i++) {
        show(document.getElementById('comment' + i))
    }

    hide(document.getElementById('showmore_btn'))
    show(document.getElementById('showless_btn'))
}

function showless() {
    for(let i = maxComments - 1; i < allComments.length; i++) {
        show(document.getElementById('comment' + i))
    }

    show(document.getElementById('showmore_btn'))
    hide(document.getElementById('showless_btn'))
}

// Show an element
var show = function (elem) {
    if(elem.style == null) {
        elem.style = {
            display: 'block'
        }
    } else {
        elem.style.display = 'block';
    }
};

// Hide an element
var hide = function (elem) {
    if(elem.style == null) {
        elem.style = {
            display: 'none'
        }
    } else {
        elem.style.display = 'none';
    }
};