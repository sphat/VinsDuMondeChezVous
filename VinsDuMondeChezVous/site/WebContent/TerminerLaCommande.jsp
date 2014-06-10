<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  <%@page import="vin.dal.db.dao.*, vin.site.commande_api.SiteParame, vin.site.commande_api.CommandeInfos"%>
<jsp:include page="PageBase/Header.jsp" />

	<div class="body">
<%
	CommandeInfos cmdInfo = (CommandeInfos) request.getAttribute("cmdInfos");
	if(cmdInfo != null){
%>	
	<form id="frmTerminerLaCommande" name="frmTerminerLaCommande" method="post" action="<%=SiteParame.RAPIDE_BANQUE_RUL+"?cmdid="+cmdInfo.getCmdId()+"&valeur="+cmdInfo.getPrix()%>">
		<div class="table">
			<div class="commandeId">Commande ID: <%=cmdInfo.getCmdId()%></div>
			<div class="motantTotalAPayer">Motant Total a Paie: <%=cmdInfo.getPrix()%>&euro;</div>
			<input type="hidden" name="CommandeId" value="123456"/>
			<input type="hidden" name="MotantAPayer" value="120"/>
			<div class="Payer"><input type="submit" class="btPayer" name="btPayer" id="btPayer" value="Je paie ma commande"/> </div>
		</div>
	</form>
<%
	}
    if (request.getAttribute("annonce") != null) {
		String annonce = (String) request.getAttribute("annonce");
%>
               <center>
                 le controller a recu les infos suivants:  <%=annonce%>
<%}%>
	</div>
<jsp:include page="PageBase/Footer.jsp" />