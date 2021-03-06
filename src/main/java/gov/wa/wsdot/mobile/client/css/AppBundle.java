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

package gov.wa.wsdot.mobile.client.css;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface AppBundle extends ClientBundle {
	public static final AppBundle INSTANCE = GWT.create(AppBundle.class);
	
	@Source("app.css")
	public Styles css();
	
	/**
	 * Social media icons
	 */
	@Source("blogger.png")
	ImageResource bloggerPNG();
	
	@Source("facebook.png")
	ImageResource facebookPNG();

	@Source("feed.png")
	ImageResource feedPNG();
	
	@Source("flickr.png")
	ImageResource flickrPNG();
	
	@Source("twitter.png")
	ImageResource twitterPNG();
	
	@Source("youtube.png")
	ImageResource youtubePNG();
	
	/**
	 * Twitter account icons
	 */
	@Source("wsdot_ferries.png")
	ImageResource wsdotFerriesPNG();
	
	@Source("wsdot_goodtogo.png")
	ImageResource wsdotGoodToGoPNG();
	
	@Source("wsdot_north.png")
	ImageResource wsdotNorthPNG();
	
	@Source("wsdot_snoqualmie_pass.png")
	ImageResource wsdotSnoqualmiePassPNG();
	
	@Source("wsdot_sw.png")
	ImageResource wsdotSwPNG();
	
	@Source("wsdot_tacoma.png")
	ImageResource wsdotTacomaPNG();
	
	@Source("wsdot_traffic.png")
	ImageResource wsdotTrafficPNG();
	
	@Source("wsdot.png")
	ImageResource wsdotPNG();
	
	/**
	 * Weather icons
	 */
	@Source("cloudy_1_night.png")
	ImageResource cloudy_1_nightPNG();

	@Source("cloudy_1.png")
	ImageResource cloudy_1PNG();

	@Source("cloudy_2_night.png")
	ImageResource cloudy_2_nightPNG();

	@Source("cloudy_2.png")
	ImageResource cloudy_2PNG();
	
	@Source("cloudy_3_night.png")
	ImageResource cloudy_3_nightPNG();

	@Source("cloudy_3.png")
	ImageResource cloudy_3PNG();

	@Source("cloudy_4_night.png")
	ImageResource cloudy_4_nightPNG();

	@Source("cloudy_4.png")
	ImageResource cloudy_4PNG();

	@Source("cloudy_5.png")
	ImageResource cloudy_5PNG();

	@Source("fog_night.png")
	ImageResource fog_nightPNG();

	@Source("fog.png")
	ImageResource fogPNG();

	@Source("hail.png")
	ImageResource hailPNG();

	@Source("light_rain.png")
	ImageResource light_rainPNG();

	@Source("overcast.png")
	ImageResource overcastPNG();

	@Source("shower_3.png")
	ImageResource shower_3PNG();

	@Source("sleet.png")
	ImageResource sleetPNG();

	@Source("snow_4.png")
	ImageResource snow_4PNG();

	@Source("snow_5.png")
	ImageResource snow_5PNG();

	@Source("sunny_night.png")
	ImageResource sunny_nightPNG();

	@Source("sunny.png")
	ImageResource sunnyPNG();

	@Source("tstorm_3.png")
	ImageResource tstorm_3PNG();

	@Source("weather_na.png")
	ImageResource weather_naPNG();
	
	/**
	 * Custom tab icons for iOS
	 */
	@Source("wsdot_logo.png")
	ImageResource logoPNG();
	
	@Source("86-camera.png")
	ImageResource tabBarCameraImage();
	
	@Source("warning.png")
	ImageResource tabBarWarningImage();
	
	@Source("179-notepad.png")
	ImageResource tabBarNotepadImage();
	
	@Source("25-weather.png")
	ImageResource tabBarWeatherImage();
	
	@Source("28-star.png")
	ImageResource tabBarStarImage();
	
	@Source("53-house.png")
	ImageResource tabBarHouseImage();

	@Source("sr520-tab.png")
	ImageResource tabBarSR520Image();
	
	@Source("sr16-tab.png")
	ImageResource tabBarSR16Image();
	
	@Source("sr167-tab.png")
	ImageResource tabBarSR167Image();
	
	@Source("clock.png")
	ImageResource buttonBarClockImage();
	
	@Source("goto.png")
	ImageResource buttonBarGoToImage();
	
	@Source("arrow_down_24.png")
	ImageResource tabBarArrowDownPNG();
	
	@Source("arrow_up_24.png")
	ImageResource tabBarArrowUpPNG();
	
	@Source("flash_24.png")
	ImageResource buttonBarFlashImagePNG();
	
	@Source("warning_24.png")
	ImageResource buttonBarWarningImagePNG();
	
	@Source("flag1_24.png")
	ImageResource buttonBarFlagImagePNG();
	
	@Source("photo_24.png")
	ImageResource buttonBarPhotoImagePNG();
	
	@Source("clock_24.png")
	ImageResource buttonBarClockImagePNG();
	
	/**
	 * Highway icons
	 */
	@Source("i5.png")
	ImageResource i5PNG();
	
	@Source("i90.png")
	ImageResource i90PNG();
	
	@Source("sr9.png")
	ImageResource sr9PNG();
	
	@Source("sr539.png")
	ImageResource sr539PNG();
	
	@Source("sr543.png")
	ImageResource sr543PNG();
	
	@Source("us97.png")
	ImageResource us97PNG();
	
	@Source("bc11.png")
	ImageResource bc11PNG();
	
	@Source("bc13.png")
	ImageResource bc13PNG();
	
	@Source("bc15.png")
	ImageResource bc15PNG();
	
	@Source("bc97.png")
	ImageResource bc97PNG();
	
	@Source("bc99.png")
	ImageResource bc99PNG();	
	
	/**
	 * Home dashboard icons
	 */
	@Source("home_btn_border_default.png")
	ImageResource borderDefaultPNG();

	@Source("home_btn_ferries_default.png")
	ImageResource ferriesDefaultPNG();

	@Source("home_btn_passes_default.png")
	ImageResource passesDefaultPNG();

	@Source("home_btn_social_default.png")
	ImageResource socialDefaultPNG();

	@Source("home_btn_tolling_default.png")
	ImageResource tollingDefaultPNG();

	@Source("home_btn_traffic_default.png")
	ImageResource trafficDefaultPNG();

	/**
	 * Other images
	 */
	@Source("play_overlay.png")
	ImageResource playOverlayPNG();
	
	@Source("btn_alert.png")
	ImageResource btnAlertPNG();
	
	@Source("btn_star_off_normal.png")
	ImageResource btnStarOffNormalPNG();
	
	@Source("btn_star_on_normal.png")
	ImageResource btnStarOnNormalPNG();
	
	@Source("camera.png")
	ImageResource cameraPNG();
	
	@Source("camera_video.png")
	ImageResource cameraVideoPNG();
	
	@Source("camera_shadow.png")
	ImageResource cameraShadowPNG();
	
	@Source("disclosure.png")
	ImageResource disclosurePNG();
	
	@Source("image_placeholder.png")
	ImageResource imagePlaceholderPNG();
	
	/**
	 * Highway alert icons 
	 */
	
	@Source("closed.png")
	ImageResource closedPNG();

	@Source("closed_shadow.png")
	ImageResource closedShadowPNG();
	
	@Source("construction_high.png")
	ImageResource constructionHighPNG();
	
	@Source("alert_highest.png")
	ImageResource alertHighestPNG();
	
	@Source("alert_shadow.png")
	ImageResource alertShadowPNG();
	
	/**
	 * Ferry vesselwatch icons and shadows
	 */
	@Source("ferry_0.png")
	ImageResource ferry0();

	@Source("ferry_0_shadow.png")
	ImageResource ferry0Shadow();
	
	@Source("ferry_30.png")
	ImageResource ferry30();

	@Source("ferry_30_shadow.png")
	ImageResource ferry30Shadow();
	
	@Source("ferry_60.png")
	ImageResource ferry60();

	@Source("ferry_60_shadow.png")
	ImageResource ferry60Shadow();
	
	@Source("ferry_90.png")
	ImageResource ferry90();

	@Source("ferry_90_shadow.png")
	ImageResource ferry90Shadow();
	
	@Source("ferry_120.png")
	ImageResource ferry120();

	@Source("ferry_120_shadow.png")
	ImageResource ferry120Shadow();
	
	@Source("ferry_150.png")
	ImageResource ferry150();

	@Source("ferry_150_shadow.png")
	ImageResource ferry150Shadow();
	
	@Source("ferry_180.png")
	ImageResource ferry180();

	@Source("ferry_180_shadow.png")
	ImageResource ferry180Shadow();
	
	@Source("ferry_210.png")
	ImageResource ferry210();

	@Source("ferry_210_shadow.png")
	ImageResource ferry210Shadow();
	
	@Source("ferry_240.png")
	ImageResource ferry240();

	@Source("ferry_240_shadow.png")
	ImageResource ferry240Shadow();
	
	@Source("ferry_270.png")
	ImageResource ferry270();

	@Source("ferry_270_shadow.png")
	ImageResource ferry270Shadow();
	
	@Source("ferry_300.png")
	ImageResource ferry300();

	@Source("ferry_300_shadow.png")
	ImageResource ferry300Shadow();
	
	@Source("ferry_330.png")
	ImageResource ferry330();

	@Source("ferry_330_shadow.png")
	ImageResource ferry330Shadow();
	
	@Source("ferry_360.png")
	ImageResource ferry360();

	@Source("ferry_360_shadow.png")
	ImageResource ferry360Shadow();
	
	public interface Styles extends CssResource {
		String cellImage();
		String cellDetails();
		String cellDetails2();
		String cellDetails3();
		String logo();
		String dashboard();
		String homeButton();
		String highImpactAlert();
		String highImpactAlerts();
		String noHighImpactAlerts();
		
		String borderButton();
		String ferriesButton();
		String passesButton();
		String socialButton();
		String tollingButton();
		String trafficButton();
		
		String cellTravelTimes();
		String colorGreen();
		String colorRed();
		String colorBlack();
		String cameraImage();
		String cameraImageHidden();
		String img();
		String videoPlayOverlay();
		String cellFerriesRouteSchedules();
		String disclosure();
		String sectionHeader();
		String starButtonOn();
		String starButtonOff();
		
		String bloggerIcon();
		String facebookIcon();
		String feedIcon();
		String flickrIcon();
		String twitterIcon();
		String youtubeIcon();
		
		String i5Icon();
		String i90Icon();
		String sr9Icon();
		String sr539Icon();
		String sr543Icon();
		String us97Icon();
		String bc11Icon();
		String bc13Icon();
		String bc15Icon();
		String bc97Icon();
		String bc99Icon();
		
		String cloudy_1_nightIcon();
		String cloudy_1Icon();
		String cloudy_2_nightIcon();
		String cloudy_2Icon();
		String cloudy_3_nightIcon();
		String cloudy_3Icon();
		String cloudy_4_nightIcon();
		String cloudy_4Icon();
		String cloudy_5Icon();
		String fog_nightIcon();
		String fogIcon();
		String hailIcon();
		String light_rainIcon();
		String overcastIcon();
		String shower_3Icon();
		String sleetIcon();
		String snow_4Icon();
		String snow_5Icon();
		String sunny_nightIcon();
		String sunnyIcon();
		String tstorm_3Icon();
		String weather_naIcon();
		
		String wsdotFerriesIcon();
		String wsdotGoodToGoIcon();
		String wsdotNorthIcon();
		String wsdotSnoqualmiePassIcon();
		String wsdotSwIcon();
		String wsdotTacomaIcon();
		String wsdotTrafficIcon();
		String wsdotIcon();
	}
}
