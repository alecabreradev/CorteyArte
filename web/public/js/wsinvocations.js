function getMovie(movieId) {
    let myUrl = "/movies/" + movieId;
    $.ajax({
        type: "GET",
        dataType: "json",
        url: myUrl,
        success: function(data) {
			$("#resPelicula").html(JSON.stringify(data));
        },
        error: function(res) {
            let mensaje = JSON.parse(res.responseText);
			alert("ERROR: " + mensaje.msg);
        }
    });
}

function postMovie() {
    $.ajax({
        type: "POST",
        url: "/movies",
        contentType: "application/json",
        dataType: "text",
        data: JSON.stringify({// los datos que completan los campos han de ser recibidos por el postMovie en nuestro trabajo
            "title": "Dunkirk",
            "director": "Christopher Nolan",
            "year": 2017
        }),
        success: function(data) {
            data = JSON.parse(data); //
            $("#resPelicula").html(data.msg);
        },
        error: function(res) {
            alert("ERROR: " + res.statusText);
        }
    });
}

function getAllMovies() {
    let myUrl = "/movies";
    $.ajax({
        type: "GET",
        dataType: "json",
        url: myUrl,
        success: function(data) {
	    	$("#resPelicula").html(JSON.stringify(data));
        },
        error: function(res) {
            console.error("ERROR:", res.status, res.statusText);
        }
    });
}

function deleteMovie(movieId) {
    let myUrl = "/movies/" + movieId;
    $.ajax({
        type: "DELETE",
        url: myUrl,
        success: function(data) {
            $("#resPelicula").html(data.msg);
        },
        error: function(res) {
            let mensaje = JSON.parse(res.responseText);
            alert("ERROR: " + mensaje.msg);
        }
    });
}

function putMovie(movieId) {
    $.ajax({
        type: "PUT",
        url: "/movies/" + movieId,
        contentType: "application/json",
        dataType: "text",
        data: JSON.stringify({// los datos que completan los campos han de ser recibidos por el postMovie en nuestro trabajo
            "title": "El Rey Leon", 
            "director": "Christopher Nolan",
            "year": 2017
        }),
        success: function(data) {
            data = JSON.parse(data); 
            $("#resPelicula").html(data.msg);
        },
        error: function(res) {
            let mensaje = JSON.parse(res.responseText);
            alert("ERROR: " + mensaje.msg);
        }
    });
}

function getGato(id){
    let myUrl = "https://api.thecatapi.com/v1/images/" + id;
    $.ajax({
        type: "GET",
        url: myUrl,
        success: function(data) {
            $("#resPelicula").html(data.breeds[0].name);
        },
    });
}