package de.atruvia.gui.presenter;


import de.atruvia.gui.Euro2DollarRechnerView;
import de.atruvia.model.Euro2DollarRechner;

public interface Euro2DollarPresenter {

	Euro2DollarRechnerView getView();

	void setView(Euro2DollarRechnerView view);

	Euro2DollarRechner getModel();

	void setModel(Euro2DollarRechner model);

	void onRechnen();

	void onBeenden();

	void onPopulateItems();
	
	void updateRechnenActionState(); // Nicht beachten

}