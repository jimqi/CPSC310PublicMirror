package com.google.gwt.killers.client;

/**
 * RPC call for importerservice
 */

import java.util.List;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

public interface ImporterService extends RemoteService {
	void importJSONData(String url);

	void importCSVData(String url);

	String[][] getData();

	String[] get_ColNames();

}
