var ajaxCart = {
	
		//for product page 'add' button...
		//override every button in the page in relation to the cart

		overrideButtonsInThePage : function(){
			
			$('.MyButton').unbind('click').click(function(){
				ajaxCart.add($(this).attr('id'), $('#'+$(this).attr('id')+'qty').attr('value'), 'add', false);
				return false;
			});
		
		},
		
		overrideButtonsInPageDetail : function(){
			//pour la page vin detail
			$('.btAjouterPanier').unbind('click').click(function(){
				ajaxCart.add($(this).attr('id'), '1', 'add', false);
				return false;				
			});
		},
		
		//+/- Stock QTY
		//Start
		overrideButtonsStockMoins : function(){
			//pour la page panier
			$('.StockDiminuerQuantite').unbind('click').click(function(){
				ajaxCart.qtyPlusMoins($(this).attr('id'),true,0);
				//Mettre ajouter le panier session comptoir
				//var cmd = $(this).attr('id').split(";");
				//ajaxCart.add(cmd[0], '1', 'sub', true);
				return false;
			});
		},

		overrideButtonsStockPlus : function(){
			//pour la page panier
			$('.StockAugmenterQuantite').unbind('click').click(function(){
				ajaxCart.qtyPlusMoins($(this).attr('id'),true,0);
				//Mettre ajouter le panier session comptoir
				//var cmd = $(this).attr('id').split(";");
				//ajaxCart.add(cmd[0], '1', 'add', true);
				return false;
			});
		},
		
		stockApprovisionement : function(){
			var objJson = {};
			$('.StockTextQuantiteVin').each(function(){
				var qty = parseInt($('#'+$(this).attr('id')).html());
				var v_id = $(this).attr('id');
				if(qty>0){
					objJson['"' + v_id.substr(0, (v_id.length - 3)) + '"'] = parseInt($('#'+$(this).attr('id')).html());
				}
			});
			console.log(objJson);
			var json = JSON.stringify(objJson);
			console.log(json);
			
			//send the ajax request to the server
			$.ajax({
			type: 'POST',
			url:  'http://localhost:8080/site/VinServlet?action=requestApprovisionement',
			async: true,
			cache: false,
			dataType : "json",
			data: 'request='+json,
			success: function(jsonData_) {
				// Mettre à jour le panier Quantité header
				/*
				$('.ajax_cart_quantity').html(jsonData_.total_qty);
				if(is_maj_prix==true){
					$('.MotantTotal').html(jsonData_.total_price+'&euro;');
					$('#'+idProduct+'prx').html(jsonData_.total_unit_price+'&euro;');
				}
				if(jsonData_.total_qty==0){
					ajaxCart.majHtmlPanierElement();
				}
				*/
			},
			});
			
		},
		
		overriedCMDStockValidate : function(){
			$('#btCMDStockValider').unbind('click').click(function(){
				ajaxCart.stockApprovisionement();
			});
		},
		//End
		//+/- Stock QTY
		
		overrideButtonsPanierMoins : function(){
			//pour la page panier
			$('.diminuerQuantite').unbind('click').click(function(){
				ajaxCart.qtyPlusMoins($(this).attr('id'),true,1);
				//Mettre ajouter le panier session comptoir
				var cmd = $(this).attr('id').split(";");
				ajaxCart.add(cmd[0], '1', 'sub', true);
				return false;
			});
		},
		
		overrideButtonsPanierPlus : function(){
			//pour la page panier
			$('.augmenterQuantite').unbind('click').click(function(){
				ajaxCart.qtyPlusMoins($(this).attr('id'),true,1);
				//Mettre ajouter le panier session comptoir
				var cmd = $(this).attr('id').split(";");
				ajaxCart.add(cmd[0], '1', 'add', true);
				return false;
			});
		},
		
		// supprimer le vin dans le panier
		overrideButtonsDelPanier : function(){
			$('.supprimerCeVin.prix').unbind('click').click(function(){
				var cmd = $(this).attr('id').split(";");
				ajaxCart.add(cmd[0], '1', 'del', true);
				$('#'+cmd[0]+'element').remove();
				console.log("click on me : "+$(this).attr('id'));
				return false;
			});
		},
		
		// add a product in the cart via ajax
		add : function(idProduct, quantity, cmd, is_maj_prix) {
			console.log("product id : " + idProduct + " , quantity : " + quantity);
		
			//send the ajax request to the server
			$.ajax({
			type: 'GET',
			url:  'http://localhost:8080/site/VinServlet?action=PanierSession&',
			async: true,
			cache: false,
			dataType : "json",
			data: 'vid='+idProduct+'&qty='+quantity+'&cmd='+cmd,
			success: function(jsonData_) {
				// Mettre à jour le panier Quantité header
				$('.ajax_cart_quantity').html(jsonData_.total_qty);
				if(is_maj_prix==true){
					$('.MotantTotal').html(jsonData_.total_price+'&euro;');
					$('#'+idProduct+'prx').html(jsonData_.total_unit_price+'&euro;');
				}
				if(jsonData_.total_qty==0){
					ajaxCart.majHtmlPanierElement();
				}
			},
			});
		},
		
		// count produit in panier
		countProduitPanier : function() {
			//send the ajax request to the server
			$.ajax({
			type: 'GET',
			url:  'http://localhost:8080/site/VinServlet?action=countPanierProduit',
			async: true,
			cache: false,
			dataType : "json",
			success: function(jsonData) {
				$('.ajax_cart_quantity').html(jsonData.total_qty);
				var class_element = $('.process_table');
				if(class_element.length && jsonData.total_qty == 0){
					ajaxCart.majHtmlPanierElement();
				}
			},
			});
		},
		
		produitQtyPlusMoins : function(){
			$('.change_qty').unbind('click').click(function(){
				ajaxCart.qtyPlusMoins($(this).attr('id'),false,1);
				return false;
			});
		},
		
		// plus ou moins quantite
		qtyPlusMoins : function(cmd_id,t,limite){
			console.log('click me');
			var val_milite = limite;
			var is_tunnel = t;
			var cmd = cmd_id.split(";");

			var quantite = (is_tunnel!=true)?parseInt($('#'+cmd[0]+'qty').attr('value')):parseInt($('#'+cmd[0]+'qty').html());
			
			if(cmd[1]=='m'){
				if(quantite<=val_milite){
					quantite = val_milite;
				}else{
					quantite -= 1;
				}
				(is_tunnel!=true)?$('#'+cmd[0]+'qty').attr('value',quantite):$('#'+cmd[0]+'qty').html(quantite);
			}else if(cmd[1]=='p'){
					quantite += 1;
					(is_tunnel!=true)?$('#'+cmd[0]+'qty').attr('value',quantite):$('#'+cmd[0]+'qty').html(quantite);
			}
		},
		
		majHtmlPanierElement : function(){
			$('.tableListVins').remove();
			$('.MotantDeCommande').remove();
			$('#frmPanier').remove();
			$('.process_table').css('display', 'block');
		},
		
		//for logout in the header
		//override every button in the page in relation to the cart

		overrideLogOutClick : function(){
			
			$('.logout').unbind('click').click(function(){
				var cmd = $(this).attr('id').split(";");
				console.log('click log out : ' + cmd[0] + ' -- ' + cmd[1]);
				return false;
			});
		
		},		
};

//Chargement quand la page est chargé 

$(document).ready(function(){
	//Mettre à jour le panier Quantité header
	ajaxCart.countProduitPanier();
	
	ajaxCart.overrideButtonsInThePage();
	ajaxCart.overrideButtonsInPageDetail();
	
	ajaxCart.produitQtyPlusMoins();
	
	//Tunnel de commande -> Panier
	ajaxCart.overrideButtonsPanierMoins();
	ajaxCart.overrideButtonsPanierPlus();
	ajaxCart.overrideButtonsDelPanier();
	
	//Connecter Deconnecter
	//ajaxCart.overrideLogOutClick();
	
	//Stock QTY
	ajaxCart.overrideButtonsStockMoins();
	ajaxCart.overrideButtonsStockPlus();
	//ajaxCart.stockApprovisionement();
	ajaxCart.overriedCMDStockValidate();
});
