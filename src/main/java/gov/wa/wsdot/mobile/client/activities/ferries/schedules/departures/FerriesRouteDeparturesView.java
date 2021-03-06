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

package gov.wa.wsdot.mobile.client.activities.ferries.schedules.departures;

import gov.wa.wsdot.mobile.shared.FerriesScheduleTimesItem;

import java.util.List;

import com.google.gwt.user.client.ui.IsWidget;

public interface FerriesRouteDeparturesView extends IsWidget {
	
	public void setPresenter(Presenter presenter);
	
	public interface Presenter {
		
		public void onBackButtonPressed();
		
		public void onDayOfWeekSelected(int position);
		
	}
	
	public void render(List<FerriesScheduleTimesItem> createTopicsList);
	
	public void renderDaysOfWeek(List<String> days);
	
	public void setTitle(String title);
	
	public int getDayOfWeekSelected();
	
	public void setDayOfWeekSelected(int index);
	
	public void showProgressBar();
	
	public void hideProgressBar();
	
	public void refresh();
	
}
