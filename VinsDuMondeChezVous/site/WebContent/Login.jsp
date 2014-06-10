<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@page import="vin.dal.db.dao.*"%>
<jsp:include page="PageBase/Header.jsp" />
	<div class="body">
		<br /><br />
		<div class="textLogin">
			<h2>Je suis d&eacute;j&agrave; client VinDuMondeChezVous</h2>
			<a href="/site/VinServlet?action=CreerUnCompte">Nouveau client VinDuMondeChezVous</a>
		</div>
		<form id="frmLogin" name="frmLogin" method="post" action="VinServlet?action=Login">
		<div class="tableLogin" id="tableLogin">
		<br />
			<div class="unChamp champLogin">
				<div class="textEmail">Email</div>
				<div class="divEmail"><input type="text" name="tbEmail" id="tbEmail" class="inputEmail"></div>
			</div>
			
			<div class="unChamp">
				<div class="textPass">Password</div>
				<div class="divPass"><input type="password" name="tbPassword" id="tbPassword" class="inputPass"></div>
			</div>
			
			<div class="unChamp">
				<div class="divButton"><input type="submit" name="btLogin" id="btLogin" value="Login"></div>
				<input type="hidden" name="action" value="Login"/>
			</div>
			
		</div>
		</form>
		
		 <%
            if (request.getAttribute("resultatLogin") != null) {
               String annonce = (String) request.getAttribute("resultatLogin");
               if(annonce.equals("reusir")){
            	   
      %>
               <script>
                  document.getElementById("tableLogin").innerHTML = "authentification reusi";
               </script>
            <%}else{
            %>
            <p style="color:red">
                "login ou mot de pass pas bien"
               </p>
               <%}} %>
		
	</div>
<jsp:include page="PageBase/Footer.jsp" />