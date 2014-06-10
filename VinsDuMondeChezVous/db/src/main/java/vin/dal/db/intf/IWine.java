package vin.dal.db.intf;

import java.util.Collection;
import java.util.Date;

public interface IWine {
	String getFBId();
	
	Date getVintage();
	
	boolean hasVintage();
	
	Collection<IVarietyComposition> getComposition();
	
	String getProducer();
	
	ILocation getLocation();
	
	String getAppellation();
	
	String getVineyard();
	
	WineColor getColor();
	
	float getPercentageAlcohol();
	
	WineType getWineType();
	
	String getWineStyle();
}
