var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#posts").html("");
}

function connect() {
    var socket = new SockJS('/stomp-endpoint');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/posts', function (greeting) {
            showGreeting(JSON.parse(greeting.body));
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    stompClient.send("/app/addPost", {}, JSON.stringify({
        'title': $("#title").val(),
        'postText': $("#postText").val()
    }));
}

function showGreeting(post) {
    console.log(post)
    if(!Array.isArray(post)) {
        $("#posts").append("<tr><td>" + post.id + "</td><td>" + post.title + "</td><td>" + post.postText + "</td></tr>");
    }
    else {
        showGreetings(post);
    }
}

function showGreetings(posts) {
    $("#posts").html("");
    posts.forEach(post => {
        $("#posts").append("<tr><td>" + post.id + "</td><td>" + post.title + "</td><td>" + post.postText + "</td></tr>");
    })
}

function update() {
    stompClient.send("/app/getPosts");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $( "#update" ).click(function () {
        update();
    })
});