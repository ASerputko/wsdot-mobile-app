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

package gov.wa.wsdot.mobile.client;

import gov.wa.wsdot.mobile.client.activities.home.HomePlace;
import gov.wa.wsdot.mobile.client.css.AppBundle;
import gov.wa.wsdot.mobile.client.service.WSDOTDataService.Tables;
import gov.wa.wsdot.mobile.shared.CacheItem;
import gov.wa.wsdot.mobile.shared.CameraItem;
import gov.wa.wsdot.mobile.client.service.WSDOTContract.HighwayAlertsColumns;

import java.util.ArrayList;
import java.util.List;

import com.google.code.gwt.database.client.Database;
import com.google.code.gwt.database.client.SQLError;
import com.google.code.gwt.database.client.SQLTransaction;
import com.google.code.gwt.database.client.TransactionCallback;
import com.google.code.gwt.database.client.service.DataServiceException;
import com.google.code.gwt.database.client.service.RowIdListCallback;
import com.google.code.gwt.database.client.service.ScalarCallback;
import com.google.code.gwt.database.client.service.VoidCallback;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.GWT.UncaughtExceptionHandler;
import com.google.gwt.dom.client.StyleInjector;
import com.google.gwt.maps.client.LoadApi;
import com.google.gwt.maps.client.LoadApi.LoadLibrary;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.RootPanel;
import com.googlecode.gwtphonegap.client.PhoneGap;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableEvent;
import com.googlecode.gwtphonegap.client.PhoneGapAvailableHandler;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutEvent;
import com.googlecode.gwtphonegap.client.PhoneGapTimeoutHandler;
import com.googlecode.mgwt.mvp.client.AnimatableDisplay;
import com.googlecode.mgwt.mvp.client.AnimatingActivityManager;
import com.googlecode.mgwt.mvp.client.history.MGWTPlaceHistoryHandler;
import com.googlecode.mgwt.ui.client.MGWT;
import com.googlecode.mgwt.ui.client.MGWTSettings;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort;
import com.googlecode.mgwt.ui.client.MGWTSettings.ViewPort.DENSITY;

public class MobileAppEntryPoint implements EntryPoint {
	
	private void start() {

		final PhoneGap phoneGap = GWT.create(PhoneGap.class);

		phoneGap.addHandler(new PhoneGapAvailableHandler() {

	        @Override
	        public void onPhoneGapAvailable(PhoneGapAvailableEvent event) {
	    		final ClientFactory clientFactory = new ClientFactoryImpl();
	        	((ClientFactoryImpl) clientFactory).setPhoneGap(phoneGap);
	        	buildDisplay(clientFactory);
	        }
	    });	
	    
	    phoneGap.addHandler(new PhoneGapTimeoutHandler() {

	        @Override
	        public void onPhoneGapTimeout(PhoneGapTimeoutEvent event) {
	          Window.alert("Cannot load PhoneGap");
	        }
	    });
		
		phoneGap.initializePhoneGap();

	}

	private void buildDisplay(ClientFactory clientFactory) {

		ViewPort viewPort = new MGWTSettings.ViewPort();
		viewPort.setTargetDensity(DENSITY.MEDIUM);
		
		viewPort.setUserScaleAble(false).setMinimumScale(1.0)
				.setInitialScale(1.0).setMaximumScale(1.0);

		MGWTSettings settings = new MGWTSettings();
		settings.setViewPort(viewPort);
		settings.setIconUrl("logo.png");
		settings.setAddGlosToIcon(true);
		settings.setFullscreen(true);
		settings.setPreventScrolling(true);

		MGWT.applySettings(settings);

		// Create initial database tables
		createDatabaseTables(clientFactory);

		// Start PlaceHistoryHandler with our PlaceHistoryMapper
		AppPlaceHistoryMapper historyMapper = GWT.create(AppPlaceHistoryMapper.class);

		StyleInjector.inject(AppBundle.INSTANCE.css().getText());
		createPhoneDisplay(clientFactory);

		AppHistoryObserver historyObserver = new AppHistoryObserver();

		MGWTPlaceHistoryHandler historyHandler = new MGWTPlaceHistoryHandler(
				historyMapper, historyObserver);
		
		historyHandler.register(clientFactory.getPlaceController(),
				clientFactory.getEventBus(), new HomePlace());
		
		historyHandler.handleCurrentHistory();
	}
	
	private void createDatabaseTables(final ClientFactory clientFactory) {

	    if (clientFactory.getDbService().getDatabase().getVersion().equals("")) {
	        clientFactory.getDbService().getDatabase()
	                .changeVersion("", "1.0", new TransactionCallback() {

                @Override
                public void onTransactionStart(SQLTransaction transaction) {
                    clientFactory.getDbService().createCachesTable(new VoidCallback() {

                        @Override
                        public void onFailure(DataServiceException error) {
                        }

                        @Override
                        public void onSuccess() {
                            
                            clientFactory.getDbService().getCachesTableCount(new ScalarCallback<Integer>() {

                                @Override
                                public void onFailure(DataServiceException error) {
                                }

                                @Override
                                public void onSuccess(Integer result) {
                                    if (result == 0) initCachesTable(clientFactory);
                                }
                            });
                        }
                    
                    });
                    
                    clientFactory.getDbService().createCamerasTable(new VoidCallback() {

                        @Override
                        public void onFailure(DataServiceException error) {
                        }

                        @Override
                        public void onSuccess() {
                            clientFactory.getDbService().getCamerasTableCount(new ScalarCallback<Integer>() {

                                @Override
                                public void onFailure(DataServiceException error) {
                                }

                                @Override
                                public void onSuccess(Integer result) {
                                    if (result == 0) initCamerasTable(clientFactory);
                                }
                            });
                        }
                    
                    });
                    
                    clientFactory.getDbService().createHighwayAlertsTable(new VoidCallback() {

                        @Override
                        public void onFailure(DataServiceException error) {
                        }

                        @Override
                        public void onSuccess() {
                        }

                    });
                    
                    clientFactory.getDbService().createMountainPassesTable(new VoidCallback() {

                        @Override
                        public void onFailure(DataServiceException error) {
                        }

                        @Override
                        public void onSuccess() {
                        }

                    });
                    
                    clientFactory.getDbService().createTravelTimesTable(new VoidCallback() {

                        @Override
                        public void onFailure(DataServiceException error) {
                        }

                        @Override
                        public void onSuccess() {
                        }

                    });
                    
                    clientFactory.getDbService().createFerriesSchedulesTable(new VoidCallback() {

                        @Override
                        public void onFailure(DataServiceException error) {
                        }

                        @Override
                        public void onSuccess() {
                        }

                    });
                    
                    clientFactory.getDbService().createBorderWaitTable(new VoidCallback() {

                        @Override
                        public void onFailure(DataServiceException error) {
                        }

                        @Override
                        public void onSuccess() {
                        }

                    });
                    
                }

                @Override
                public void onTransactionSuccess() {
                    clientFactory.getDbService().getDatabase()
                            .changeVersion("1.0", "1.1", new TransactionCallback() {

                        @Override
                        public void onTransactionStart(SQLTransaction transaction) {
                            transaction.executeSql("ALTER TABLE " + Tables.HIGHWAY_ALERTS
                                    + " ADD COLUMN " + HighwayAlertsColumns.HIGHWAY_ALERT_LAST_UPDATED + " TEXT", null);
                            
                        }

                        @Override
                        public void onTransactionSuccess() {
                        }

                        @Override
                        public void onTransactionFailure(SQLError error) {
                        }
                        
                    });
                    
                }

                @Override
                public void onTransactionFailure(SQLError error) {
                }
                
            });
	    }
	    
	    if (clientFactory.getDbService().getDatabase().getVersion().equals("1.0")) {
            clientFactory.getDbService().getDatabase()
                    .changeVersion("1.0", "1.1", new TransactionCallback() {

                @Override
                public void onTransactionStart(SQLTransaction transaction) {
                    transaction.executeSql("ALTER TABLE " + Tables.HIGHWAY_ALERTS
                            + " ADD COLUMN " + HighwayAlertsColumns.HIGHWAY_ALERT_LAST_UPDATED + " TEXT", null);
                    
                }

                @Override
                public void onTransactionSuccess() {
                }

                @Override
                public void onTransactionFailure(SQLError error) {
                }
                
            });
	    }
	
	}
	
	private void initCachesTable(ClientFactory clientFactory) {

		List<CacheItem> cacheItems = new ArrayList<CacheItem>();
		cacheItems.add(new CacheItem("cameras", 0));
		cacheItems.add(new CacheItem("highway_alerts", 0));
		cacheItems.add(new CacheItem("mountain_passes", 0));
		cacheItems.add(new CacheItem("travel_times", 0));
		cacheItems.add(new CacheItem("ferries_schedules", 0));
		cacheItems.add(new CacheItem("border_wait", 0));
		
		clientFactory.getDbService().initCachesTable(cacheItems, new RowIdListCallback() {

			@Override
			public void onFailure(DataServiceException error) {
			}

			@Override
			public void onSuccess(List<Integer> rowIds) {
			}
		
		});

	}
	
	private void initCamerasTable(ClientFactory clientFactory) {
		
		List<CameraItem> cameraItems = new ArrayList<CameraItem>();
		
		cameraItems.add(new CameraItem(
				1134,
				"White Pass Summit on US12 @ MP 150.9",
				"http://images.wsdot.wa.gov/SC/012VC15094.jpg",
				46.63404, -121.40514, 0, "US 12", 0));
		cameraItems.add(new CameraItem(
				1138,
				"US 97 MP 164 Blewett Pass Summit",
				"http://images.wsdot.wa.gov/us97/blewett/sumtnorth.jpg",
				47.334975, -120.578397, 0, "US 97", 0));
		cameraItems.add(new CameraItem(
				4030,
				"SR 20 MP 214.5 (View East)",
				"http://images.wsdot.wa.gov/sr20/louploup/sr20louploup_east.jpg",
				48.3904, -119.87925, 0, "SR 20", 0));
		cameraItems.add(new CameraItem(
				1127,
				"Manastash Ridge Summit on I-82 @ MP 7",
				"http://images.wsdot.wa.gov/rweather/UMRidge_medium.jpg",
				46.89184, -120.43773, 0, "I-82", 0));
		cameraItems.add(new CameraItem(
				1137,
				"Satus Pass on US 97 @ MP 27",
				"http://images.wsdot.wa.gov/satus/satus1.jpg",
				45.98296, -120.65381, 0, "US 97", 0));
		cameraItems.add(new CameraItem(
				1161,
				"Sherman Pass on SR-20 @ MP 320",
				"http://images.wsdot.wa.gov/rweather/shermanpass_medium.jpg",
				48.604742, -118.459912, 0, "SR 20", 0));
		cameraItems.add(new CameraItem(
				9029,
				"Denny Creek on I-90 @ MP46.8",
				"http://images.wsdot.wa.gov/sc/090VC04680.jpg",
				47.396441, -121.49935, 0, "I-90", 0));
		cameraItems.add(new CameraItem(1099,
				"Franklin Falls on I-90 @ MP51.3",
				"http://images.wsdot.wa.gov/sc/090VC05130.jpg",
				47.42246, -121.40991, 0, "I-90", 0));
		cameraItems.add(new CameraItem(
				1100,
				"Snoqualmie Summit on I-90 @ MP52",
				"http://images.wsdot.wa.gov/sc/090VC05200.jpg",
				47.40818, -121.40592, 0, "I-90", 0));
		cameraItems.add(new CameraItem(
				1102,
				"Hyak on I-90 @ MP55.2",
				"http://images.wsdot.wa.gov/sc/090VC05517.jpg",
				47.37325, -121.37699, 0, "I-90", 0));
		cameraItems.add(new CameraItem(
				9018,
				"Price Creek on I-90 @ MP61",
				"http://images.wsdot.wa.gov/sc/090VC06100.jpg",
				47.326814, -121.332553, 0, "I-90", 0));
		cameraItems.add(new CameraItem(
				9019,
				"Easton Hill on I-90 @ MP67.4",
				"http://images.wsdot.wa.gov/sc/090VC06740.jpg",
				47.264479, -121.284702, 0, "I-90", 0));
		cameraItems.add(new CameraItem(
				1103,
				"Easton on I-90 @ MP70.6",
				"http://images.wsdot.wa.gov/sc/090VC07060.jpg",
				47.280581, -121.185882, 0, "I-90", 0));
		cameraItems.add(new CameraItem(
				8062,
				"US 2 MP 64 Stevens Pass Summit",
				"http://images.wsdot.wa.gov/us2/stevens/sumteast.jpg",
				47.7513, -121.10619, 0, "US 2", 0));
		cameraItems.add(new CameraItem(
				8063,
				"US 2 MP 65 Stevens Pass Ski Lodge",
				"http://images.wsdot.wa.gov/us2/stvldg/sumtwest.jpg",
				47.7513, -121.10619, 0, "US 2", 0));
		cameraItems.add(new CameraItem(
				9145,
				"US 2 MP 62 Old Faithful Avalanche Zone",
				"http://images.wsdot.wa.gov/us2/oldfaithful/oldfaithful.jpg",
				47.724431, -121.134085, 0, "US 2", 0));
		
		clientFactory.getDbService().initCamerasTable(cameraItems, new RowIdListCallback() {

			@Override
			public void onFailure(DataServiceException error) {
			}

			@Override
			public void onSuccess(List<Integer> rowIds) {
			}

		});

	}

	private void createPhoneDisplay(ClientFactory clientFactory) {
		
		AnimatableDisplay display = GWT.create(AnimatableDisplay.class);
		PhoneActivityMapper appActivityMapper = new PhoneActivityMapper(clientFactory);
		PhoneAnimationMapper appAnimationMapper = new PhoneAnimationMapper();

		AnimatingActivityManager activityManager = new AnimatingActivityManager(
				appActivityMapper, appAnimationMapper,
				clientFactory.getEventBus());

		activityManager.setDisplay(display);

		RootPanel.get().add(display);

	}

	private void loadMapApi() {

		boolean sensor = true;

		// load all the libs for use
		ArrayList<LoadLibrary> loadLibraries = new ArrayList<LoadApi.LoadLibrary>();
		//loadLibraries.add(LoadLibrary.ADSENSE);
		//loadLibraries.add(LoadLibrary.DRAWING);
		//loadLibraries.add(LoadLibrary.GEOMETRY);
		//loadLibraries.add(LoadLibrary.PANORAMIO);
		//loadLibraries.add(LoadLibrary.PLACES);

		Runnable onLoad = new Runnable() {
			public void run() {
				start();
			}
		};

		LoadApi.go(onLoad, loadLibraries, sensor);
	}	
	
	@Override
	public void onModuleLoad() {

		if (!Database.isSupported()) {
			Window.alert("HTML 5 Database is NOT supported in this browser!");

			return;
		}

		GWT.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {

			@Override
			public void onUncaughtException(Throwable e) {
				// TODO put in my own meaninful handler
				Window.alert("uncaught: " + e.getMessage());
				e.printStackTrace();

			}
		});

		new Timer() {
			
			@Override
			public void run() {
				loadMapApi();

			}
			
		}.schedule(1);

	}

}
