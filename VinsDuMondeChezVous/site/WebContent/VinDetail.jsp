<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@page import="vin.dal.db.model.*, java.util.List, vin.dal.db.intf.*;"%>

<jsp:include page="PageBase/Header.jsp" />

<div class="body">
<%-- <jsp:include page="PageBase/Menu.jsp" /> --%>
			<%
 		if(request.getAttribute("Vin") != null)
 		{
			Wine vin = (Wine) request.getAttribute("Vin");  			
 		%>	
		<div class="tableInfoVin">
			<div class="NomVin">Vin A</div>		
			<div class="InforVin">
				<div class="imageVin"><img alt="image Vin" src="Image/Vins/Bordeaux.png" width="200px" height="200px"/></div>
				<div class="Info">
					<div class="prix">- prix: <%=vin.getPrice()%> </div>
					<div class="type"><p>type : <%=vin.getColor().name()%> <p></div>
					<div class="origin">- origin: <%=vin.getLocation().getCountry()%> </div>
					<div class="region">- region: <%=vin.getLocation().getRegion()%> </div>
				</div>

			</div>
			<div class="AjouterPanier"><input type="button" id="<%=vin.getFBId()%>" name="btAjouterPanier" class="btAjouterPanier" value="Ajouter Au Panier"/></div>
		</div>
							<%}%>
</div>

<jsp:include page="PageBase/Footer.jsp" />