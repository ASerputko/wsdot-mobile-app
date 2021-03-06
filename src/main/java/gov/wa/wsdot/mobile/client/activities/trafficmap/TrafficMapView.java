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

package gov.wa.wsdot.mobile.client.activities.trafficmap;

import gov.wa.wsdot.mobile.shared.CameraItem;
import gov.wa.wsdot.mobile.shared.HighwayAlertItem;

import java.util.List;

import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLngBounds;
import com.google.gwt.user.client.ui.IsWidget;

public interface TrafficMapView extends IsWidget {
	
	public void setPresenter(Presenter presenter);
	
	public interface Presenter {
		
		public void onBackButtonPressed();
		
		public void onCameraButtonPressed(boolean showCameras);
		
		public void onTravelTimesButtonPressed();
		
		public void onGoToLocationButtonPressed();
		
		public void onCameraSelected(int cameraId);
		
		public void onAlertSelected(int alertId);
		
		public void onLocateButtonPressed();
		
		public void onMapIsIdle();
		
		public void onSeattleExpressLanesButtonPressed();
		
		public void onSeattleTrafficAlertsButtonPressed();
		
	}
	
	public void showProgressBar();

	public void hideProgressBar();
	
	public LatLngBounds getViewportBounds();
	
	public MapWidget getMapWidget();
	
	public void hideCameras();
	
	public void showCameras();
	
	public void deleteCameras();
	
	public void hideAlerts();
	
	public void showAlerts();
	
	public void deleteAlerts();
	
	public void drawCameras(List<CameraItem> cameras);
	
	public void drawAlerts(List<HighwayAlertItem> alerts);
	
	public void setTitle(String title);

	public void setMapLocation(double latitude, double longitude, int zoom);
	
	public void setMapLocation();
	
}