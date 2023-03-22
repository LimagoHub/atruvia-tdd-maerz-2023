package de.atruvia.gui.presenter;


import de.atruvia.gui.Euro2DollarRechnerView;
import de.atruvia.model.Euro2DollarRechner;

public class Euro2DollarPresenterImpl implements de.atruvia.gui.presenter.Euro2DollarPresenter {
	
	private Euro2DollarRechnerView view;
	private Euro2DollarRechner model;
	
	
	
	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#getView()
	 */
	@Override
	public Euro2DollarRechnerView getView() {
		return view;
	}

	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#setView(de.gui.IEuro2DollarRechnerView)
	 */
	@Override
	public void setView(Euro2DollarRechnerView view) {
		this.view = view;
	}

	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#getModel()
	 */
	@Override
	public Euro2DollarRechner getModel() {
		return model;
	}

	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#setModel(de.model.IEuro2DollarRechner)
	 */
	@Override
	public void setModel(Euro2DollarRechner model) {
		this.model = model;
	}

	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#rechnen()
	 */
	@Override
	public void onRechnen() {

		// Eurowert aus der Maske holen
		// Eurowert wenn möglich in double wandeln
		// Wenn nicht möglich Fehlermeldung in dollarfeld schreiben (return)
		// Eurowert an Model übergeben
		// Ergebnis in String wandeln
		// ergebnis in Maske schreiben
		// Fehler immer in Dollarfeld schreiben
		// RuntimeException abfangen und Meldung im Dollarfeld ausgeben
		try {
			view.setDollar(String.format("%.2f", model.calculateEuro2Dollar(Double.valueOf(view.getEuro()))));
		} catch (NullPointerException e) {
			view.setDollar("Eingabe erforderlich");
		} catch (NumberFormatException e) {
			view.setDollar("Keine Zahl");
		} catch (RuntimeException e) {
			view.setDollar("interner Fehler");
		}
		return;
	}
	
	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#beenden()
	 */
	@Override
	public void onBeenden() {  
		view.close();
	}
	/* (non-Javadoc)
	 * @see de.gui.presenter.IEuro2DollarPresenter#populateItems()
	 */
	@Override
	public void onPopulateItems() {
		// den String "0" jeweils in das Eurofeld und das Dollarfeld der Maske schreiben
		// und den Rechnenbutton enablen
		view.setEuro("0");
		view.setDollar("0");
		view.setRechnenEnabled(true);
	}

	@Override
	public void updateRechnenActionState() {
		try {
			Double.valueOf(view.getEuro());
			view.setRechnenEnabled(true);
		} catch (RuntimeException e) {
			view.setRechnenEnabled(false);
		}
	}
}
