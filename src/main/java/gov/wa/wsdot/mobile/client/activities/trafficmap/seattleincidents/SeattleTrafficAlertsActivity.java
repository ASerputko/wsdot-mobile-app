/*
 * Copyright (c) 2013 Washington State Department of Transportation
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 */

package gov.wa.wsdot.mobile.client.activities.trafficmap.seattleincidents;

import gov.wa.wsdot.mobile.client.ClientFactory;
import gov.wa.wsdot.mobile.client.event.ActionEvent;
import gov.wa.wsdot.mobile.client.event.ActionNames;
import gov.wa.wsdot.mobile.client.util.Consts;
import gov.wa.wsdot.mobile.shared.SeattleIncidentItem;
import gov.wa.wsdot.mobile.shared.SeattleIncidentsFeed;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.notification.AlertCallback;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowStandardHandler;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowStandardHandler.PullActionHandler;

public class SeattleTrafficAlertsActivity extends MGWTAbstractActivity implements
		SeattleTrafficAlertsView.Presenter {

	private final ClientFactory clientFactory;
	private SeattleTrafficAlertsView view;
	private EventBus eventBus;
	private PhoneGap phoneGap;
	private static ArrayList<SeattleIncidentItem> seattleIncidentItems = new ArrayList<SeattleIncidentItem>();
	private static List<Integer> blockingCategory = new ArrayList<Integer>();
    private static List<Integer> constructionCategory = new ArrayList<Integer>();
    private static List<Integer> specialCategory = new ArrayList<Integer>();
    private static final String SEATTLE_INCIDENTS_URL = Consts.HOST_URL + "/traveler/api/seattleincidents";
	
	public SeattleTrafficAlertsActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, final EventBus eventBus) {
		view = clientFactory.getSeattleTrafficAlertsView();
		this.eventBus = eventBus;
		view.setPresenter(this);
		view.getPullHeader().setHTML("pull down");

		PullArrowStandardHandler headerHandler = new PullArrowStandardHandler(
				view.getPullHeader(), view.getPullPanel());
		
		headerHandler.setErrorText("Error");
		headerHandler.setLoadingText("Loading");
		headerHandler.setNormalText("pull down");
		headerHandler.setPulledText("release to load");
		headerHandler.setPullActionHandler(new PullActionHandler() {

			@Override
			public void onPullAction(final AsyncCallback<Void> callback) {

				new Timer() {

					@Override
					public void run() {
						createTopicsList();							
						view.refresh();
						callback.onSuccess(null);
					}
					
				}.schedule(1);
			}
			
		});
		
		view.setHeaderPullHandler(headerHandler);
		
		buildCategories();
		createTopicsList();
		panel.setWidget(view);

	}

	private void buildCategories() {
		
		blockingCategory.add(0); // Traffic conditions
		blockingCategory.add(4); // Incident
		blockingCategory.add(5); // Collision
		blockingCategory.add(6); // Disabled vehicle
		blockingCategory.add(10); // Water over roadway
		blockingCategory.add(11); // Obstruction
		blockingCategory.add(30); // Fallen tree

		constructionCategory.add(7); // Closures
		constructionCategory.add(8); // Road work
		constructionCategory.add(9); // Maintenance

		specialCategory.add(2); // Winter driving restriction
		specialCategory.add(12); // Sporting event
		specialCategory.add(13); // Seahawks game
		specialCategory.add(28); // Sounders game
		specialCategory.add(14); // Mariners game
		specialCategory.add(15); // Special event
		specialCategory.add(16); // Restriction
		specialCategory.add(17); // Flammable restriction
		specialCategory.add(29); // Huskies game
		
	}
	
	private void createTopicsList() {
		seattleIncidentItems.clear();
		view.showProgressBar();
		
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		// Set timeout for 30 seconds (30000 milliseconds)
		jsonp.setTimeout(30000);
		jsonp.requestObject(SEATTLE_INCIDENTS_URL, new AsyncCallback<SeattleIncidentsFeed>() {

			@Override
			public void onFailure(Throwable caught) {
				view.hideProgressBar();
				phoneGap.getNotification()
				.alert("Can't load data. Check your connection.",
						new AlertCallback() {
							@Override
							public void onOkButtonClicked() {
								// TODO Auto-generated method stub
							}
						}, "Connection Error");
			}

			@Override
			public void onSuccess(SeattleIncidentsFeed result) {
				SeattleIncidentItem item = null;
				
				if (result.getSeattleIncidents() != null) {
					int numEntries = result.getSeattleIncidents().getItems().length();
					for (int i = 0; i < numEntries; i++) {
						item = new SeattleIncidentItem();
						
						item.setCategory(result.getSeattleIncidents().getItems().get(i).getCategory());
						item.setDescription(result.getSeattleIncidents().getItems().get(i).getDescription());
						
						seattleIncidentItems.add(item);
					}
					
					view.hideProgressBar();
					categorizeAlerts();
					view.refresh();
				}
				
			}
		});
		
	}
	
	private void categorizeAlerts() {

		Stack<String> amberalert = new Stack<String>();
		Stack<String> blocking = new Stack<String>();
    	Stack<String> construction = new Stack<String>();
    	Stack<String> special = new Stack<String>();
    	
    	ArrayList<SeattleIncidentItem> amberAlerts = new ArrayList<SeattleIncidentItem>();
    	ArrayList<SeattleIncidentItem> blockingIncidents = new ArrayList<SeattleIncidentItem>();
    	ArrayList<SeattleIncidentItem> constructionClosures = new ArrayList<SeattleIncidentItem>();
    	ArrayList<SeattleIncidentItem> specialEvents = new ArrayList<SeattleIncidentItem>();
    	
    	for (SeattleIncidentItem item : seattleIncidentItems) {
			// Check if there is an active amber alert
			if (item.getCategory().equals(24)) {
				amberalert.push(item.getDescription());
			}
			else if (blockingCategory.contains(item.getCategory())) {
				blocking.push(item.getDescription());
			}
            else if (constructionCategory.contains(item.getCategory())) {
                construction.push(item.getDescription());
            }
            else if (specialCategory.contains(item.getCategory())) {
                special.push(item.getDescription());
            }
    	}
    	
    	if (amberalert != null && amberalert.size() != 0) {
    		while (!amberalert.empty()) {
    			amberAlerts.add(new SeattleIncidentItem(amberalert.pop()));
    		}
    		view.showAmberAlerts();
    		view.renderAmberAlerts(amberAlerts);
    	} else {
    	    view.hideAmberAlerts();
    	}
    	
		if (blocking.empty()) {
			blockingIncidents.add(new SeattleIncidentItem("None reported"));
		} else {
			while (!blocking.empty()) {
				blockingIncidents.add(new SeattleIncidentItem(blocking.pop()));
			}					
		}

		view.renderBlocking(blockingIncidents);
		
		if (construction.empty()) {
			constructionClosures.add(new SeattleIncidentItem("None reported"));
		} else {
			while (!construction.empty()) {
				constructionClosures.add(new SeattleIncidentItem(construction.pop()));
			}					
		}
		
		view.renderConstruction(constructionClosures);

		if (special.empty()) {
			specialEvents.add(new SeattleIncidentItem("None reported"));
		} else {
			while (!special.empty()) {
				specialEvents.add(new SeattleIncidentItem(special.pop()));
			}					
		}
		
		view.renderSpecial(specialEvents);
    	
	}
	
	@Override
	public void onStop() {
		view.setPresenter(null);
	}
	
	@Override
	public void onDoneButtonPressed() {
		ActionEvent.fire(eventBus, ActionNames.BACK);
	}

}