package org.geogebra.common.move.ggtapi.models;

import java.util.List;

import org.geogebra.common.move.ggtapi.operations.BackendAPI;
import org.geogebra.common.move.ggtapi.operations.LogInOperation;
import org.geogebra.common.move.ggtapi.operations.URLChecker;
import org.geogebra.common.move.ggtapi.requests.MaterialCallbackI;
import org.geogebra.common.move.ggtapi.requests.SyncCallback;
import org.geogebra.common.util.AsyncOperation;

public class MarvlAPI implements BackendAPI {

	private static final String MARVL_URL = "https://api.geogebra.org/v1.0";
	private final MaterialRestApi materialRestApi;

	public MarvlAPI(URLChecker urlChecker) {
		materialRestApi = new MaterialRestApi(MARVL_URL, urlChecker) ;
	}

	public void getItem(String id, MaterialCallbackI callback) {
		materialRestApi.getItem(id, callback);
	}

	public boolean checkAvailable(LogInOperation logInOperation) {
		return materialRestApi.checkAvailable(logInOperation);
	}

	public String getLoginUrl() {
		return materialRestApi.getLoginUrl();
	}

	public boolean parseUserDataFromResponse(GeoGebraTubeUser guser, String response) {
		return materialRestApi.parseUserDataFromResponse(guser, response);
	}

	public void deleteMaterial(final Material mat, final MaterialCallbackI callback) {
		materialRestApi.deleteMaterial(mat, callback);
	}

	public void authorizeUser(final GeoGebraTubeUser user, final LogInOperation op, final boolean automatic) {
		materialRestApi.authorizeUser(user, op, automatic);
	}

	public void setClient(ClientInfo client) {
		materialRestApi.setClient(client);
	}

	public void sync(long i, SyncCallback syncCallback) {
		materialRestApi.sync(i, syncCallback);
	}

	public boolean isCheckDone() {
		return materialRestApi.isCheckDone();
	}

	public void setUserLanguage(String fontStr, String loginToken) {
		materialRestApi.setUserLanguage(fontStr, loginToken);
	}

	public void shareMaterial(Material material, String to, String message, MaterialCallbackI cb) {
		materialRestApi.shareMaterial(material, to, message, cb);
	}

	public void favorite(int id, boolean favorite) {
		materialRestApi.favorite(id, favorite);
	}

	public String getUrl() {
		return materialRestApi.getUrl();
	}

	public void logout(String token) {
		materialRestApi.logout(token);
	}

	public void uploadLocalMaterial(Material mat, MaterialCallbackI cb) {
		materialRestApi.uploadLocalMaterial(mat, cb);
	}

	public boolean performCookieLogin(final LogInOperation op) {
		return materialRestApi.performCookieLogin(op);
	}

	public void performTokenLogin(LogInOperation op, String token) {
		materialRestApi.performTokenLogin(op, token);
	}

	public void getUsersMaterials(MaterialCallbackI userMaterialsCB, MaterialRequest.Order order) {
		materialRestApi.getUsersMaterials(userMaterialsCB, order);
	}

	public void getFeaturedMaterials(MaterialCallbackI userMaterialsCB) {
		materialRestApi.getFeaturedMaterials(userMaterialsCB);
	}

	public void getUsersOwnMaterials(final MaterialCallbackI userMaterialsCB, MaterialRequest.Order order) {
		materialRestApi.getUsersOwnMaterials(userMaterialsCB, order);
	}

	public void getSharedMaterials(final MaterialCallbackI sharedMaterialsCB, MaterialRequest.Order order) {
		materialRestApi.getSharedMaterials(sharedMaterialsCB, order);
	}

	public void uploadMaterial(String tubeID, String visibility, String text, String base64, MaterialCallbackI materialCallback, Material.MaterialType type) {
		materialRestApi.uploadMaterial(tubeID, visibility, text, base64, materialCallback, type);
	}

	public void uploadRenameMaterial(Material material, MaterialCallbackI materialCallback) {
		materialRestApi.uploadRenameMaterial(material, materialCallback);
	}

	public void copy(Material material, final String title, final MaterialCallbackI materialCallback) {
		materialRestApi.copy(material, title, materialCallback);
	}

	public void setBasicAuth(String base64) {
		materialRestApi.setBasicAuth(base64);
	}

	public void setShared(Material m, String groupID, boolean shared, final AsyncOperation<Boolean> callback) {
		materialRestApi.setShared(m, groupID, shared, callback);
	}

	public void getGroups(String materialID, final AsyncOperation<List<String>> callback) {
		materialRestApi.getGroups(materialID, callback);
	}

	public boolean owns(Material mat) {
		return materialRestApi.owns(mat);
	}

	public boolean canUserShare(boolean student) {
		return materialRestApi.canUserShare(student);
	}

	public boolean anonymousOpen() {
		return materialRestApi.anonymousOpen();
	}

	public URLChecker getURLChecker() {
		return materialRestApi.getURLChecker();
	}
}
