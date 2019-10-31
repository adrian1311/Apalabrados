function UserViewModel() {
	var self = this;
	this.email = ko.observable();
	this.userName = ko.observable();
	this.pwd = ko.observable();
	
	this.message = ko.observable();
	
	this.getUsers = function(){
		$.get("listaUsuarios", mostrarListado);
	}
	
	function mostrarListado(respuesta){
		self.message(JSON.stringify(respuesta));
	}
	
	this.login = function() {
		var info = {
			userName : this.userName(),
			pwd : this.pwd(),
			withEmail : ($("#loginUserName").val().indexOf("@")!=-1)
		};
		
		var data = {
			data : info,
			url : "login",
			type : "post",
			success : loginOk,
			error : error
		};
		$.ajax(data);
	}
	
	this.register = function() {
		var info = {
			email : $("#inputEmail").val(),
			userName : $("#inputUserName").val(),
			pwd1 : $("#inputPwd1").val(),
			pwd2 : $("#inputPwd2").val()
		};
		var data = {
			data : info,
			url : "register",
			type : "post",
			success : registerOk,
			error : error
		};
		$.ajax(data);		
	}
	
	function registerOk() {
		self.userName($("#inputUserName").val());
		self.email($("#inputEmail").val());
		self.pwd($("#inputPwd1").val());
		$("#message").attr("style", "color:blue");
		self.message("Register OK");
	}
	
	function loginOk() {
		$("#message").attr("style", "color:blue");
		self.message("Login OK");
	}
	
	function error(response) {
		$("#message").attr("style", "color:red");
		self.message(response.responseText);
	}
}

var user = new UserViewModel();
ko.applyBindings(user);