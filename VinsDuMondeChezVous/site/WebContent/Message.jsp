<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@page import="javax.persistence.EntityManager,javax.persistence.Persistence,vin.dal.db.dao.JPAMessageDAO,vin.dal.db.intf.IMessage, java.util.ArrayList;"%>
<%ArrayList<IMessage> messages = (ArrayList<IMessage>)request.getAttribute("ListMessage");
%>


<script type="text/javascript">
var idMessageBg = -1;

function Message(titre, date, contenu){
	this.titre = titre;
	this.date = date;
	this.contenu = contenu;
	this.getTitre = function() {
        return this.titre;
    };

    this.getDate = function() {
        return this.date;
    };

    this.getContenu = function() {
        return this.contenu;
    };
}

var messages = new Array();
<%
	for(int i=0;i<messages.size();i++){
%>
	messages[<%=i%>] = new Message("<%=messages.get(i).getTitreMessage()%>","<%=messages.get(i).getDateEnvoyeMessage()%>" , "<%=messages.get(i).getContenuMessage()%>");

<%}%>

function clickOnTitreMessage(idMessage){
	var idMessageCourant = "IdMessage"+idMessage;
	document.getElementById(idMessageCourant).style.background = "#EAEAE0";
	if(idMessageBg!=-1){
		var idMessageBgCourant = "IdMessage"+idMessageBg;
		document.getElementById(idMessageBgCourant).style.background = "none";
	}
	idMessageBg = idMessage;
	var contenuMessage = '<div class="TitreMessage">' + messages[idMessage].getTitre() + '</div><div class="DateMessage"><div class="titreDateMessage">Date:</div><div class="DateMessageDetail">';
	contenuMessage += messages[idMessage].getDate() + '</div></div><div class="espaceMessage"></div><div class="contenuMessageDetail">';
	contenuMessage += messages[idMessage].getContenu() + '</div>';
	document.getElementById("ContenuMessage").innerHTML = contenuMessage;
}
</script>

<jsp:include page="PageBase/Header.jsp" />
<div class="body">
	<div class="bodyMessage">
	<br /><br />
		<div class="DivListeMessage">
			<div class="titleListeMessage">Liste des messages</div>
			<div class="espaceMessage"></div>
			<%
	        messages = (ArrayList<IMessage>)request.getAttribute("ListMessage");
	        if(messages.size()>0){
	        for(int i=0;i<messages.size();i++)
	        {
			%>
			<div class="unMessage" id="IdMessage<%=i%>" onclick="clickOnTitreMessage(<%=i%>)">
				<div class="titleMessage"><%=messages.get(i).getTitreMessage()%></div>
				<div class="expediteur"><%=messages.get(i).getClient().getEmail()%></div>
			</div>
			<% }}%> 
			
			
		</div>
		
		<div class="DivDetailMessage">
			<div class="titleDetailMessage">Le contenu du message</div>
			<div class="espaceMessage"></div>
			<div class="ContenuMessage" id="ContenuMessage">
				<div class="TitreMessage">
				
				<% if(messages.size()>0){ %>
				<%=messages.get(0).getTitreMessage()%>
				</div>
				<div class="DateMessage">
					<div class="titreDateMessage">Date:</div>
					<div class="DateMessageDetail"><%=messages.get(0).getDateEnvoyeMessage()%></div>
				</div>
				<div class="espaceMessage"></div>
				<div class="contenuMessageDetail"><%=messages.get(0).getContenuMessage()%></div>
				<% } %>
			</div>
		</div>
	</div>

</div>

<jsp:include page="PageBase/Footer.jsp" />