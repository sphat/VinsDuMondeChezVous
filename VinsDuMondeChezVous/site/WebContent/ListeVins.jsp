<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@page import="vin.dal.db.model.Wine, java.util.List;"%>

<!-- Télécharger la liste de vins -->
<% 
	if (request.getAttribute("ListeVins") == null && request.getAttribute("ListeVins_Reponse") != "1") {  
 		request.getRequestDispatcher("VinServlet?action=GetListeVins") 
				.forward(request, response); 
	}  
 %>

<jsp:include page="PageBase/Header.jsp" />

<!-- <div class="Wrap"> -->
	<div class="body">
	<br /><br /><br />
			<!-- Begin VinBlock -->
 		<%
 		if(request.getAttribute("ListeVins") != null)
 		{
			List<Wine> listeVins = (List<Wine>) request.getAttribute("ListeVins"); 
 			for (int i = 0; i < listeVins.size(); i++) {  
	 				Wine wine = listeVins.get(i);
 		%>

		<div class="VinBlock">

			<div class="VinImage">
				<img alt="Ajouter" id="1" src="Image/Vins/Bordeaux.png" />
			</div>

			<div class="VinInfo">
 				 				<div class="VinTitre"><%=wine.getAppellation()%></div>  
				<div class="VinContent">
					<div class="attribut">Price : <%=wine.getPrice()%></div>
				</div>
				<div class="VinButton">
					<div style="width: 110px;">
						<img alt="moins" class="change_qty" id="<%=wine.getFBId()%>;m" src="Image/Icon/ArrowLeft.png" 
							style="float: left; height: 20px; width: 20px;" /> 
							<input type="text" value="1" class="change_qty_val" id="<%=wine.getFBId()%>qty" />
						<img alt="plus" class="change_qty" id="<%=wine.getFBId()%>;p" src="Image/Icon/ArrowRight.png" 
							style="float: Right; height: 20px; width: 20px;" />
					</div>
					<div id="<%=wine.getFBId()%>" class="MyButton">
						<div>
							<img alt="Ajouter" id="1" src="Image/Icon/Pannier.png"
								onclick="1" />
						</div>
						<div class="text">Ajouter</div>
					</div>
					<div class="vin_button_detail">
					<a href="VinServlet?action=GetVinDetail&VinID=<%=wine.getFBId()%>">
						<div>
							<img alt="Detail" src="Image/Icon/Detail.png" onclick="1" />
						</div>
						<div class="text">Détail</div>
						</a>
					</div>
				</div>
			</div>
		</div>
		<!-- Begin VinBlock -->
		<%
				}
			}
		%>
		<!-- End VinBlock -->
	
	</div>
<!-- </div> -->
<jsp:include page="PageBase/Footer.jsp" />