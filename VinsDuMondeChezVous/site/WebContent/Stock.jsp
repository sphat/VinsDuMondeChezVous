<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@page import="vin.dal.db.model.Wine, java.util.List;" %>

<jsp:include page="PageBase/Header.jsp" />
<!--
<div class="header" style="FONT-SIZE: x-small; FONT-FAMILY: 'Andale Mono';">
</div>
-->
<div class="body">

	<div class="tableListVins">
		<div class="titleTableListVins">
			<div class="titleArticle">ID</div>
			<div class="titleLivraison">Appellation</div>
			<div class="titleQuantite">Color</div>
			<div class="titleVinQty">Vin Type</div>
			<div class="titleVinQtyColor">Stock</div>
			<div class="titleApprovisionnement">Appro-<br />visionement</div>
		</div>
		<div class="ListVins">
<!-- Begin ListVin --> 	
<form>
<%
	if(request.getAttribute("ListeVins") != null)
	{
	List<Wine> listeVins = (List<Wine>) request.getAttribute("ListeVins"); 
		for (int i = 0; i < listeVins.size(); i++) {  
				Wine wine = listeVins.get(i);
%>		
			<div class="vin1">
				<div class="nomVin"><%=wine.getFBId()%></div>
				<div class="Livraison"><%=wine.getAppellation()%></div>
				<div class="quantite"><%=wine.getColor().toString()%></div>
				<div class="vinqyt"><%=wine.getWineType()%></div>
				<div class="vinqyt vinqutcolor"><%=wine.getBottles().size()%></div>
				<div class="approvisionnement">
					<div class="StockDiminuerQuantite" id="<%=wine.getFBId()%>;m">-</div>
					<div class="StockTextQuantiteVin" id="<%=wine.getFBId()%>qty">0</div>
					<div class="StockAugmenterQuantite" id="<%=wine.getFBId()%>;p">+</div>				
				</div>
			</div>			
<%
		}
	}
%>
	<div class="ValiderVotreCommande">
		<input type="button" name="btValider" id="btCMDStockValider" class="btValider" onclick="javascript:void(0);" value="Demande stock approvisionnement">
	</div>
</form>
<!-- End ListVin -->		
		</div>
	</div>

</div>
<!-- 
<div class="footer"></div>
 -->
<jsp:include page="PageBase/Footer.jsp" />
