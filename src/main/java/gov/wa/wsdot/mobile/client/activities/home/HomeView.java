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

package gov.wa.wsdot.mobile.client.activities.home;

import gov.wa.wsdot.mobile.shared.CameraItem;
import gov.wa.wsdot.mobile.shared.FerriesRouteItem;
import gov.wa.wsdot.mobile.shared.HighwayAlertItem;
import gov.wa.wsdot.mobile.shared.MountainPassItem;
import gov.wa.wsdot.mobile.shared.TravelTimesItem;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;
import com.googlecode.mgwt.ui.client.widget.base.HasRefresh;
import com.googlecode.mgwt.ui.client.widget.base.PullArrowWidget;
import com.googlecode.mgwt.ui.client.widget.base.PullPanel.Pullhandler;

public interface HomeView extends IsWidget {
	
	public void setPresenter(Presenter presenter);
	
	public interface Presenter {
		
		public void onAboutButtonPressed();
		
		public void onTrafficMapButtonPressed();
		
		public void onFerriesButtonPressed();
		
		public void onMountainPassesButtonPressed();
		
		public void onSocialMediaButtonPressed();
		
		public void onTollRatesButtonPressed();
		
		public void onBorderWaitButtonPressed();
		
		public void onHighImpactAlertSelected(int alertId);
		
		public void onCameraSelected(int index);

		public void onFerriesSelected(int index);
		
		public void onMountainPassSelected(int index);
		
		public void onTravelTimeSelected(int index);
		
	}
	
	public void render(List<HighwayAlertItem> createAlertsList);
	
	public void renderCameras(List<CameraItem> createCameraList);
	
	public void renderFerries(List<FerriesRouteItem> createFerriesList);
	
	public void renderMountainPasses(List<MountainPassItem> createMountainPassList);
	
	public void renderTravelTimes(List<TravelTimesItem> createTravelTimesList);

	public void showCamerasHeader();
	
	public void hideCamerasHeader();
	
	public void showCamerasList();
	
	public void hideCamerasList();
	
	public void showFerriesHeader();
	
	public void hideFerriesHeader();
	
	public void showFerriesList();
	
	public void hideFerriesList();
	
	public void showMountainPassesHeader();
	
	public void hideMountainPassesHeader();
	
	public void showMountainPassesList();
	
	public void hideMountainPassesList();

	public void showTravelTimesHeader();
	
	public void hideTravelTimesHeader();
	
	public void showTravelTimesList();
	
	public void hideTravelTimesList();
	
	public void showEmptyFavoritesMessage();
	
	public void hideEmptyFavoritesMessage();
	
	public void refresh();
	
	public void clear();
	
	public void showProgressBar();
	
	public void hideProgressBar();
	
	public void setHeaderPullHandler(Pullhandler pullHandler);
	
	public PullArrowWidget getPullHeader();
	
	public HasRefresh getPullPanel();

}
