const express = require("express");
const mysql = require("mysql");
const bodyParser = require("body-parser");

const app = express();
const urlencodedParser = bodyParser.urlencoded({extended: false});

const crypto = require("crypto");

const nodemailer = require("nodemailer");
  
const pool = mysql.createPool({ 
	host: "localhost",
	user: "root",
	database: "quiz",
	password: "sip"
});

function md(email, code){
    var string = "" + email + code;
    var hash = crypto.createHash('md5').update(string).digest('hex');
    
    return hash;
}

var transporter = nodemailer.createTransport({
   service: 'gmail',
    auth: {
        user: 'quchallenge@gmail.com',
        pass: 'Challenge1234'
    }
});

app.get('/email', (request, response) => { 
    
    var mailOptions = {
        from: 'quchallenge@gmail.com',
        to: 'vander017@gmail.com',
        subject: 'Sending',
        html: '<h1>Welcome</h1><p>That was easy!</p>'
    };
    
    transporter.sendMail(mailOptions, function(error, info){
       if (error){
           response.send(error);
       } else{
           response.send("Email sent: " + info.response);
       }
    });
});

function sendCode(email, code){
    var mailOptions = {
        from: 'quchallenge@gmail.com',
        to: email,
        subject: 'Sending',
        html: '<h1>CHALLENGE QUIZ!</h1><p>Ваш код: ' + code + '</p>'
    };
    
    transporter.sendMail(mailOptions, function(error, info){
       if (error){
           response.send(error);
       } else{
           response.send("Email sent: " + info.response);
       }
    });
}

function getCode(){
    var randomnumber;
    
    randomnumber = Math.floor(Math.random() * Math.floor(2000)) + 1;

    return randomnumber;
}

app.get('/checkemail', (request, response) => { 
	var code = request.query.code;
    
    pool.query('UPDATE user SET code = 0 WHERE code=?', code, (error, result) => {
    	if(error) throw error;
        
        response.setHeader('Access-Control-Allow-Origin', '*');
        response.setHeader('Access-Control-Allow-Headers', 'origin, content-type, accept');
    		
        response.send(result.affectedRows + "");
  	});
});

app.get('/reg', (request, response) => { 
	var name = request.query.name;
	var surname = request.query.surname;
	var email = request.query.email;
	var password = request.query.password;
    
    var passMD = md(email, password);
    
    var code = getCode();
    
    sendCode(email, code);
	
	pool.query('INSERT INTO user SET name=?, surname=?, email=?, password=?, code=?;', [name, surname, email, passMD, code], (error, result) => {
    	if(error) throw error;
        
        response.setHeader('Access-Control-Allow-Origin', '*');
        response.setHeader('Access-Control-Allow-Headers', 'origin, content-type, accept');
 
        response.send(result);
  	});
});

app.get('/login', (request, response) => { 
	var email = request.query.email;
	var password = request.query.password;
    
    var passMD = md(email, password);
    
    pool.query('SELECT * FROM user WHERE email = ? AND password = ?', [email, passMD], (error, result) => {
    	if(error) throw error;
        
        response.setHeader('Access-Control-Allow-Origin', '*');
        response.setHeader('Access-Control-Allow-Headers', 'origin, content-type, accept');
    		
        response.send(result);
  	});
});

app.get('/coderecovery', (request, response) => { 
	var email = request.query.email;
	var password = request.query.password;
    
    var code = getCode();
    
    sendCode(email, code);
    
    var passMD = md(email, password);
    
    pool.query('UPDATE user SET code = ? WHERE email=? AND password=?', [code, email, passMD], (error, result) => {
    	if(error) throw error;
        
        response.setHeader('Access-Control-Allow-Origin', '*');
        response.setHeader('Access-Control-Allow-Headers', 'origin, content-type, accept');
    		
        response.send(result);
  	});
});

app.get('/loadgame', (request, response) => { 
	var user_id = request.query.user_id;
	pool.query('select ug.id, ug.score, g.user_win, g.questions, g.end, g.name_game from user_game ug inner join game g on g.id = ug.id where ug.user_id = ?;', [user_id], (error, result) => {
        if(error) throw error;
        
        response.setHeader('Access-Control-Allow-Origin', '*');
        response.setHeader('Access-Control-Allow-Headers', 'origin, content-type, accept');
 
    	response.send(result);
  });
});

app.get('/loadgameuppactive', (request, response) => { 
	var user_id = request.query.user_id;
	pool.query('select ug.id, ug.score, g.user_win, g.questions, g.name_game, ug.end_round from user_game ug inner join game g on g.id = ug.id where ug.user_id = ? AND ug.end_round = 0;', [user_id], (error, result) => {
        if(error) throw error;
        
        response.setHeader('Access-Control-Allow-Origin', '*');
        response.setHeader('Access-Control-Allow-Headers', 'origin, content-type, accept');
 
    	response.send(result);
  });
});

app.get('/loadgameuppold', (request, response) => { 
	var user_id = request.query.user_id;
	pool.query('select ug.id, ug.score, g.user_win, g.questions, g.name_game, ug.end_round from user_game ug inner join game g on g.id = ug.id where ug.user_id = ? AND ug.end_round = 1;', [user_id], (error, result) => {
        if(error) throw error;
        
        response.setHeader('Access-Control-Allow-Origin', '*');
        response.setHeader('Access-Control-Allow-Headers', 'origin, content-type, accept');
 
    	response.send(result);
  });
});


app.get('/loadusergame', (request, response) => { 
	var game_id = request.query.game_id;
	pool.query('select ug.id, ug.user_id, u.name, u.surname, u.email, ug.score, ug.round from user_game ug inner join user u on u.id=ug.user_id where ug.id= ?;', [game_id], (error, result) => {
        if(error) throw error;
        
        response.setHeader('Access-Control-Allow-Origin', '*');
        response.setHeader('Access-Control-Allow-Headers', 'origin, content-type, accept');
        
    	response.send(result);
  });
});


app.get('/loadusergameupp', (request, response) => { 
	var game_id = request.query.game_id;
	pool.query('select ug.id, ug.user_id, u.name, u.surname, u.email, ug.score, ug.round, ug.end_round from user_game ug inner join user u on u.id=ug.user_id where ug.id= ?;', [game_id], (error, result) => {
        if(error) throw error;
        
        response.setHeader('Access-Control-Allow-Origin', '*');
        response.setHeader('Access-Control-Allow-Headers', 'origin, content-type, accept');
        
    	response.send(result);
  });
});

function getRandomInt(min, max){
    var arr = [];
    var randomnumber;
    
    while(arr.length < 5){
        randomnumber = Math.floor(Math.random() * Math.floor(max)) + 1;
        
        if (arr.indexOf(randomnumber) == -1){
            arr.push(randomnumber);
        }
    }
    
    return arr;
}

app.get('/md', (request, response) => {
    var string = "Andrey Gorbachev";
    var hash = crypto.createHash('md5').update(string).digest('hex');
    
    response.send(hash);
});


app.get('/newgame', (request, response) => {
  var user_id = request.query.user_id;
  var questions = request.query.ques;
  var name_game = request.query.name_game;
    
    var quest = "" + getRandomInt(0,7);
    	
	pool.query('INSERT INTO game SET user_win = 0, questions = ?, end = 0, name_game = ?;', [quest, name_game], (error, result) => {
   		if(error) throw error;
 
		var game_id = result.insertId;
		   
		pool.query('INSERT INTO user_game SET id = ?, user_id = ?, score = 0, round = 1;', [game_id, user_id], (error, result) => {
    		if(error) throw error;
            
            pool.query('select id from game where id = ?;', [game_id], (error, result) => {
                if(error) throw error;
                
                response.setHeader('Access-Control-Allow-Origin', '*');
                response.setHeader('Access-Control-Allow-Headers', 'origin, content-type, accept');
                
    	       response.send("" + game_id);
            });
  		});		   		
  	});
});

app.get('/newuser', (request, response) => {
  var email = request.query.email;
  var game_id = request.query.game_id;
 
    pool.query("SELECT id FROM user WHERE email = ?;", email, (error, result) => {
   		if(error) throw error; 
        
        var user = result[0].id;
        
        pool.query('INSERT INTO user_game SET id = ?, user_id = ?, score = 0, end_round=0;', [game_id, user], (error, result) => {
   		   if(error) throw error;
            
            response.setHeader('Access-Control-Allow-Origin', '*');
            response.setHeader('Access-Control-Allow-Headers', 'origin, content-type, accept');
        
            response.send(result);   		
  	   });
  	});
});

app.get('/loadques', (request, response) => {
  var ques = request.query.ques;
    
    var array_ques = ques.split(",");

    pool.query("SELECT * FROM question WHERE id=? OR id=? OR id=? OR id=? OR id=?;", [array_ques[0], array_ques[1], array_ques[2], array_ques[3], array_ques[4], array_ques[5]], (error, result) => {
   		if(error) throw error; 
        
        response.setHeader('Access-Control-Allow-Origin', '*');
        response.setHeader('Access-Control-Allow-Headers', 'origin, content-type, accept');
        
        response.send(result); 
        
  	});
});

app.get('/updatescore', (request, response) => {
  var score = request.query.score;
  var game_id = request.query.game_id;
  var user_id = request.query.user_id;
  var round = request.query.round;
 
    pool.query("UPDATE user_game SET score=?, round=?, end_round=1 WHERE id=? AND user_id=?;", [score, round, game_id, user_id], (error, result) => {
   		if(error) throw error; 
        
        response.setHeader('Access-Control-Allow-Origin', '*');
        response.setHeader('Access-Control-Allow-Headers', 'origin, content-type, accept');
        
        response.send(result); 
  	});
});

app.get('/nextround', (request, response) => {
  var game_id = request.query.game_id;
  var user_id = request.query.user_id;
 
    pool.query("UPDATE user_game SET end_round=0 WHERE id=? AND user_id=?;", [game_id, user_id], (error, result) => {
   		if(error) throw error; 
        
        response.setHeader('Access-Control-Allow-Origin', '*');
        response.setHeader('Access-Control-Allow-Headers', 'origin, content-type, accept');
        
        response.send(result); 
  	});
});

app.get('/updatesetting', (request, response) => {
    var user_id = request.query.user_id;
    var name = request.query.name;
    var surname = request.query.surname;
    var email = request.query.email;
    var password = request.query.password;
    
    var passMD = md(email, password);
 
    pool.query("UPDATE user SET name=?, surname=?, email=?, password=? WHERE id=?;", [name, surname, email, passMD, user_id], (error, result) => {
   		if(error) throw error; 
        
        response.setHeader('Access-Control-Allow-Origin', '*');
        response.setHeader('Access-Control-Allow-Headers', 'origin, content-type, accept');
        
        response.send(result); 
  	});
});

app.listen(8009);
