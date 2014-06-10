<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="vin.dal.db.model.CommandeArticle, vin.dal.db.model.Wine, java.util.List, java.text.DateFormat, java.text.SimpleDateFormat, java.math.BigDecimal;" %>

<jsp:include page="PageBase/Header.jsp" />
<!--
<div class="header" style="FONT-SIZE: x-small; FONT-FAMILY: 'Andale Mono';">
</div>
-->
<div class="body">
<%
if(session.getAttribute("panier_session")!=null){
%>
	<div class="tableListVins">
		<div class="titleTableListVins">
			<div class="titleArticle">Article</div>
			<div class="titleLivraison">Livraison</div>
			<div class="titleQuantite">Quantite</div>
			<div class="titlePrix">Prix</div>
			<div class="titleSupprimer">Supprimer</div>
		</div>
		<div class="ListVins"> 	
		<%
		//out.println("panier_session" + " : wine_id = " + vin_obj.getFBId() + " , wine_price = " + vin_obj.getPrice() + " , wine_qty = " + cmdart.getQuantity() + "<br />");
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        double totalPrice = 0;
        int totalProduit = 0;

		for(CommandeArticle cmdart : (List<CommandeArticle>) session.getAttribute("panier_session")){
			Wine vin_obj = cmdart.getWine();
			totalPrice += (vin_obj.getPrice()*cmdart.getQuantity());
			totalProduit += cmdart.getQuantity();
		%>			
			<div class="vin1" id="<%=vin_obj.getFBId()+"element"%>">
				<div class="nomVin"><%=vin_obj.getAppellation()%></div>
				<div class="Livraison" id="<%=vin_obj.getFBId()+"lvr"%>">Exp&eacute;di&eacute; le <%=dateFormat.format(cmdart.getEstimateLvrDate())%></div>
				<div class="quantite">
					<div class="diminuerQuantite" id="<%=vin_obj.getFBId()%>;m">-</div>
					<div class="textQuantiteVin" id="<%=vin_obj.getFBId()%>qty"><%=cmdart.getQuantity()%></div>
					<div class="augmenterQuantite" id="<%=vin_obj.getFBId()%>;p">+</div>
				</div>
				<div class="prix" id="<%=vin_obj.getFBId()+"prx"%>"><%=vin_obj.getPrice()*cmdart.getQuantity()%>&euro;</div>
				<div class="supprimerCeVin prix" id="<%=vin_obj.getFBId()%>;del">X</div>
			</div>			
		<%
		}
        BigDecimal totalPriceR = new BigDecimal(totalPrice);
        BigDecimal totalPriceROff = totalPriceR.setScale(2, BigDecimal.ROUND_HALF_EVEN);
		%>			
		</div>
	</div>
	<div class="MotantDeCommande">
		<div class="TextMotantTotal">Motant total de cette commande: </div>
		<div class="MotantTotal"><%=totalPriceROff%>&euro;</div>
	</div>
	<form id="frmPanier" name="frmPanier" method="post" action="VinServlet?action=TerminerLaCommande">
	<div class="ValiderVotreCommande">
		<input type="submit" name="btValider" id="btValider" class="btValider" onclick="cliquerSurButtonValider()" value="ValiderVotreCommande">
	</div>
	</form>
<% } %>
	<!-- Panier est Vide -->
	<div class="process_table" style="display: none;" id="prox_test">
        <br><br>        
            <div id="panier-vide">Votre panier est vide</div>   
    </div>	
</div>
<!-- 
<div class="footer"></div>
 -->
<jsp:include page="PageBase/Footer.jsp" />
