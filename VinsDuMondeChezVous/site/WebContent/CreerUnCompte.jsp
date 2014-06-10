<jsp:include page="PageBase/Header.jsp" />
	<div class="Wrap">
	<br /><br />
		<div class="titleCreerCompte">
			<h2>Nouveau client VinDuMondeChezVous</h2>
			<a href="/site/VinServlet?action=Login">Je suis d&eacute;j&agrave; client VinDuMondeChezVous</a>
		</div>
		<form id="frmCreerCompte" name="frmCreerCompte" method="post" action="VinServlet?action=CreerUnCompte">
		
		<div class="tableListChamps" id="tableListChamps" name="tableListChamps" >
		
			<div class="unChampTitle">
				<br />Cr&eacute;er vos identifiants<br />
			</div>
			
			<div class="unChamp champLogin">
				<div class="textEmail">Email</div>
				<div class="divEmail"><input type="text" name="tbEmail" id="tbEmail" class="inputEmail"></div>
			</div>
			
			<div class="unChamp">
				<div class="textPass">Votre Nom</div>
				<div class="divPass"><input type="text" name="tbNom" id="tbNom" class="inputPass"></div>
			</div>
			
			<div class="unChamp">
				<div class="textPass">Votre Compte</div>
				<div class="divPass"><input type="text" name="tbCompte" id="tbCompte" class="inputPass"></div>
			</div>
			
			<div class="unChamp">
				<div class="textPass">Password</div>
				<div class="divPass"><input type="password" name="tbPass" id="tbPass" class="inputPass"></div>
			</div>
			
			<div class="unChamp">
				<div class="textPass">Confirm Password</div>
				<div class="divPass"><input type="password" name="tbrePass" id="tbrePass" class="inputPass"></div>	
				<div class="unChamp">
					<div class="divButton"><input type="submit" id="btCreerCompte" name="btCreerCompte" value="creer votre compte"></div>
					<input type="hidden" name="action" value="CreerCompte"/>
				</div>
			</div>
		</div>
		
	</form>
	<br /><br />
	<%
            if (request.getAttribute("resultatCreerCompte")!=null) {
            	String resultat = (String)request.getAttribute("resultatCreerCompte");
            	System.out.println(resultat);
            	if(resultat.equals("reusir")){
      %>
              <script type="text/javascript">
               document.getElementById("tableListChamps").innerHTML = "votre compte a ete bien creer";
               </script>
               
               <%}if(resultat.equals("fail")){ %>
               
			<script type="text/javascript">
               document.getElementById("tableListChamps").innerHTML = "il y a des erreur, veillez ressayer";
               </script>
               
               <%}
               if(resultat.equals("adressExist")){
            
            %>
            <script type="text/javascript">
            document.getElementById("tableListChamps").innerHTML = "l'adresse email existe";
            </script>
            
            <%}}
            %>
	</div>
	
<jsp:include page="PageBase/Footer.jsp" />
