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

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Widget;
import com.googlecode.mgwt.dom.client.event.tap.TapEvent;
import com.googlecode.mgwt.ui.client.widget.HeaderButton;

public class YouTubeDetailsViewGwtImpl extends Composite implements
		YouTubeDetailsView {

	/**
	 * The UiBinder interface.
	 */	
	interface YouTubeDetailsViewGwtImplUiBinder extends
			UiBinder<Widget, YouTubeDetailsViewGwtImpl> {
	}

	/**
	 * The UiBinder used to generate the view.
	 */
	private static YouTubeDetailsViewGwtImplUiBinder uiBinder = GWT
			.create(YouTubeDetailsViewGwtImplUiBinder.class);

	@UiField
	HeaderButton backButton;
	
	@UiField
	HTML title;
	
	@UiField
	HTML embedContent;
	
	@UiField
	HTML description;
	
	private Presenter presenter;
	
	public YouTubeDetailsViewGwtImpl() {

		initWidget(uiBinder.createAndBindUi(this));

	}

	@UiHandler("backButton")
	protected void onBackButtonPressed(TapEvent event) {
		if (presenter != null) {
			presenter.onBackButtonPressed();
		}
	}
	
	@Override
	public void setTitle(String title) {
		this.title.setText(title);
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setEmbedContent(String videoId) {
		this.embedContent
				.setHTML("<iframe id='ytplayer' type='text/html' src='http://www.youtube.com/embed/"
						+ videoId + "' frameborder='0' />");
	}

	@Override
	public void setDescription(String description) {
		this.description.setHTML(description);
	}

}