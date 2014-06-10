<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Vin Du Monde Chez Vous</title>
<link href="<%=request.getAttribute("linkCSS")%>" rel="stylesheet"	type="text/css" />
<link href="Styles/Header.css" rel="stylesheet"	type="text/css" />
<link href="Styles/Footer.css" rel="stylesheet"	type="text/css" />
<link href="Styles/Content.css" rel="stylesheet"	type="text/css" />
<link href="Styles/Menu.css" rel="stylesheet"	type="text/css" />
<script type="text/javascript" src="Scripts/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="Scripts/js_module.js"></script>
</head>
<body>
<!-- <div class="Content"> -->
<div class="Header">
<a id="header_logo" href="VinServlet?action=GetListeVins" title="Vin Du Monde Chez Vous">Vin Du Monde Chez Vous</a>
<div id="header_user">
	<ul id="header_nav">
		<li id="shopping_cart">
			<span class="ajax_cart_quantity hidden" style="display: inline;">0</span>
<!-- 			<span class="ajax_cart_product_txt hidden" style="display: none;">produit</span>
			<span class="ajax_cart_product_txt_s hidden" style="display: inline;">produits</span> -->
			<span class="ajax_cart_no_product" style="display: none;">(vide)</span>
		</li>
			</ul>
	<p id="header_user_info">
		<span class="welcome">Bienvenue</span>
<% if(session.getAttribute("session_user_name")!=null){ %>
	<a href="javascript:void(0);" title="Identifiez-vous" class="login" rel="nofollow"><span><%=session.getAttribute("session_user_name")%></span></a>
	<a href="/site/VinServlet?action=Logout" title="Me d&eacute;connecter" class="logout" id="<%=session.getAttribute("session_user_name")%>;<%=session.getAttribute("session_user_id")%>" rel="nofollow">d&eacute;connexion</a>
<% }else{%>
	<a href="/site/VinServlet?action=CreerUnCompte" title="Identifiez-vous" class="login" rel="nofollow">Identifiez-vous</a>
<% } %>
			</p>
	<p id="my_cart"><a href="/site/VinServlet?action=panier" title="Voir mon panier" rel="nofollow">Voir mon panier</a></p>
</div>
</div>
<jsp:include page="Menu.jsp" />
