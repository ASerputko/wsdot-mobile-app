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

package gov.wa.wsdot.mobile.client.activities.socialmedia.youtube;

import gov.wa.wsdot.mobile.client.ClientFactory;
import gov.wa.wsdot.mobile.client.event.ActionEvent;
import gov.wa.wsdot.mobile.client.event.ActionNames;
import gov.wa.wsdot.mobile.shared.YouTubeFeed;
import gov.wa.wsdot.mobile.shared.YouTubeItem;

import java.util.ArrayList;

import com.google.gwt.jsonp.client.JsonpRequestBuilder;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.inappbrowser.InAppBrowser;
import com.googlecode.gwtphonegap.client.notification.AlertCallback;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowStandardHandler;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowStandardHandler.PullActionHandler;

public class YouTubeActivity extends MGWTAbstractActivity implements
		YouTubeView.Presenter {

	private final ClientFactory clientFactory;
	private YouTubeView view;
	private EventBus eventBus;
	private PhoneGap phoneGap;
	private InAppBrowser inAppBrowser;
	private static ArrayList<YouTubeItem> youTubeItems = new ArrayList<YouTubeItem>();
	private static final String YOUTUBE_FEED_URL = "http://gdata.youtube.com/feeds/api/users/wsdot/uploads?v=2&alt=jsonc&max-results=10";
	
	public YouTubeActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, final EventBus eventBus) {
		view = clientFactory.getYouTubeView();
		this.eventBus = eventBus;
		phoneGap = clientFactory.getPhoneGap();
		inAppBrowser = phoneGap.getInAppBrowser();
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
						createPostList(view);							
						view.refresh();
						callback.onSuccess(null);
					}
					
				}.schedule(1);
			}
			
		});
		
		view.setHeaderPullHandler(headerHandler);
		createPostList(view);
		
		panel.setWidget(view);
	}
	
	@Override
	public void onStop() {
		view.setPresenter(null);
	}

	@Override
	public void onItemSelected(int index) {
		YouTubeItem item = youTubeItems.get(index);

		inAppBrowser.open("http://m.youtube.com/watch?v=" + item.getId(), "",
				"enableViewportScale=yes,transitionstyle=fliphorizontal");

	}

	@Override
	public void onBackButtonPressed() {
		ActionEvent.fire(eventBus, ActionNames.BACK);
	}
	
	private void createPostList(final YouTubeView view) {
		youTubeItems.clear();
		view.showProgressBar();
		JsonpRequestBuilder jsonp = new JsonpRequestBuilder();
		// Set timeout for 30 seconds (30000 milliseconds)
		jsonp.setTimeout(30000);
		jsonp.requestObject(YOUTUBE_FEED_URL, new AsyncCallback<YouTubeFeed>() {

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
			public void onSuccess(YouTubeFeed result) {
				YouTubeItem item = null;
				
				if (result.getData() != null) {
					int numEntries = result.getData().getItems().length();
					for (int i = 0; i < numEntries; i++) {
						item = new YouTubeItem();
						
						item.setId(result.getData().getItems().get(i).getId());
						item.setTitle(result.getData().getItems().get(i).getTitle());
						item.setDescription(result.getData().getItems().get(i).getDescription());
						item.setViewCount(result.getData().getItems().get(i).getViewCount());
						item.setThumbnailUrl(result.getData().getItems().get(i).getThumbnail().getHqDefault());
						item.setUploaded(result.getData().getItems().get(i).getUploaded());
						
						youTubeItems.add(item);
					}
					
					view.hideProgressBar();
					view.render(youTubeItems);
					view.refresh();
				}
				
			}
		});
		
	}

}
