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

package gov.wa.wsdot.mobile.client.activities.camera;

import java.util.ArrayList;
import java.util.List;

import gov.wa.wsdot.mobile.client.ClientFactory;
import gov.wa.wsdot.mobile.client.event.ActionEvent;
import gov.wa.wsdot.mobile.client.event.ActionNames;
import gov.wa.wsdot.mobile.client.service.WSDOTContract.CamerasColumns;
import gov.wa.wsdot.mobile.client.service.WSDOTDataService;
import gov.wa.wsdot.mobile.shared.CameraItem;

import com.google.code.gwt.database.client.GenericRow;
import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.code.gwt.database.client.service.ListCallback;
import com.google.code.gwt.database.client.service.VoidCallback;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;
import com.googlecode.mgwt.mvp.client.MGWTAbstractActivity;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowStandardHandler;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowStandardHandler.PullActionHandler;

public class CameraActivity extends MGWTAbstractActivity implements
		CameraView.Presenter {

	private final ClientFactory clientFactory;
	private CameraView view;
	private EventBus eventBus;
	private WSDOTDataService dbService;
	private String cameraId;
	private static List<CameraItem> cameraItems = new ArrayList<CameraItem>();
	private boolean isStarred = false;
	
	public CameraActivity(ClientFactory clientFactory) {
		this.clientFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		view = clientFactory.getCameraView();
		dbService = clientFactory.getDbService();
		this.eventBus = eventBus;
		view.setPresenter(this);
		
		Place place = clientFactory.getPlaceController().getWhere();
		
		if (place instanceof CameraPlace) {
			CameraPlace cameraPlace = (CameraPlace) place;
			cameraId = cameraPlace.getId();

		}
		
		PullArrowStandardHandler headerHandler = new PullArrowStandardHandler(
				view.getPullHeader(), view.getPullPanel());

		view.getPullHeader().setHTML("pull down");
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
						getCamera(view, cameraId);
						view.refresh();
						callback.onSuccess(null);
						
					}
				}.schedule(1);
			}
		});
		
		view.setHeaderPullHandler(headerHandler);
		getCamera(view, cameraId);
		panel.setWidget(view);
	}	

	private void getCamera(final CameraView view, String cameraId) {
		dbService.getCamera(cameraId, new ListCallback<GenericRow>() {

			@Override
			public void onFailure(DataServiceException error) {
			}

			@Override
			public void onSuccess(List<GenericRow> result) {
				cameraItems.clear();
				CameraItem c;
				
				for (GenericRow camera: result) {
					c = new CameraItem();
					
					c.setCameraId(camera.getInt(CamerasColumns.CAMERA_ID));
					c.setTitle(camera.getString(CamerasColumns.CAMERA_TITLE));
					c.setImageUrl(camera.getString(CamerasColumns.CAMERA_URL));
					c.setIsStarred(camera.getInt(CamerasColumns.CAMERA_IS_STARRED));

					cameraItems.add(c);						
				}
				
				isStarred = cameraItems.get(0).getIsStarred() != 0;

				view.setTitle(cameraItems.get(0).getTitle());				
				view.toggleStarButton(isStarred);
				view.renderCamera(cameraItems);
				view.refresh();

			}
		});
	}
	
	@Override
	public void onStop() {
		view.setPresenter(null);
	}

	@Override
	public void onBackButtonPressed() {
		ActionEvent.fire(eventBus, ActionNames.BACK);
	}

	@Override
	public void onStarButtonPressed() {
		
		if (isStarred) {
			cameraItems.get(0).setIsStarred(0);
			isStarred = false;
		} else {
			cameraItems.get(0).setIsStarred(1);
			isStarred = true;
		}
		
		dbService.updateStarredCameras(cameraItems, new VoidCallback() {

			@Override
			public void onFailure(DataServiceException error) {
			}

			@Override
			public void onSuccess() {
				view.toggleStarButton(isStarred);				
			}
		});
		
	}
	
}
