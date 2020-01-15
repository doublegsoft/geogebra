package org.geogebra.web.shared.ggtapi.models;

import java.util.List;

import org.geogebra.common.move.ggtapi.models.ClientInfo;
import org.geogebra.common.move.ggtapi.models.GeoGebraTubeUser;
import org.geogebra.common.move.ggtapi.models.Material;
import org.geogebra.common.move.ggtapi.models.MaterialRequest;
import org.geogebra.common.move.ggtapi.operations.BackendAPI;
import org.geogebra.common.move.ggtapi.operations.LogInOperation;
import org.geogebra.common.move.ggtapi.operations.URLChecker;
import org.geogebra.common.move.ggtapi.requests.MaterialCallbackI;
import org.geogebra.common.move.ggtapi.requests.SyncCallback;
import org.geogebra.common.util.AsyncOperation;

public class NotesTubeApi implements BackendAPI {
	private GeoGebraTubeAPIW api;

	public NotesTubeApi(GeoGebraTubeAPIW api) {
		this.api = api;
	}

	public String getLoginUrl() {
		return api.getLoginUrl();
	}

	public String getUrl() {
		return api.getUrl();
	}

	public void authorizeUser(final GeoGebraTubeUser user, final LogInOperation op, final boolean automatic) {
		api.authorizeUser(user, op, automatic);
	}

	public boolean checkAvailable(LogInOperation op) {
		return api.checkAvailable(op);
	}

	public void setUserLanguage(String lang, String token) {
		api.setUserLanguage(lang, token);
	}

	public void logout(String token) {
		api.logout(token);
	}

	public void favorite(int id, boolean favorite) {
		api.favorite(id, favorite);
	}

	public void uploadRenameMaterial(Material mat, final MaterialCallbackI cb) {
		api.uploadRenameMaterial(mat, cb);
	}

	public void uploadLocalMaterial(final Material mat, final MaterialCallbackI cb) {
		api.uploadLocalMaterial(mat, cb);
	}

	public void deleteMaterial(Material material, final MaterialCallbackI cb) {
		api.deleteMaterial(material, cb);
	}

	public void shareMaterial(Material material, String to, String message, final MaterialCallbackI cb) {
		api.shareMaterial(material, to, message, cb);
	}

	public void getUsersMaterials(MaterialCallbackI cb, MaterialRequest.Order order) {
		api.getUsersMaterialsGgs(cb, order);
	}

	public void getUsersOwnMaterials(MaterialCallbackI cb, MaterialRequest.Order order) {
		api.getUsersOwnMaterials(cb, order);
	}

	public void getSharedMaterials(final MaterialCallbackI cb, MaterialRequest.Order order) {
		api.getSharedMaterials(cb, order);
	}

	public void uploadMaterial(String tubeID, String visibility, final String filename, String base64, final MaterialCallbackI cb, Material.MaterialType type) {
		api.uploadMaterial(tubeID, visibility, filename, base64, cb, type);
	}

	public void getFeaturedMaterials(MaterialCallbackI callback) {
		api.getFeaturedNotesMaterials(callback);
	}

	public void getItem(String id, MaterialCallbackI callback) {
		api.getItem(id, callback);
	}

	public boolean isCheckDone() {
		return api.isCheckDone();
	}

	public void sync(long timestamp, final SyncCallback cb) {
		api.sync(timestamp, cb);
	}

	public void setClient(ClientInfo clientInfo) {
		api.setClient(clientInfo);
	}

	public void performTokenLogin(LogInOperation logInOperation, String token) {
		api.performTokenLogin(logInOperation, token);
	}

	public void copy(final Material material, final String title, final MaterialCallbackI copyCallback) {
		api.copy(material, title, copyCallback);
	}

	public void setShared(Material m, String groupID, boolean shared, AsyncOperation<Boolean> callback) {
		api.setShared(m, groupID, shared, callback);
	}

	public void getGroups(String materialID, AsyncOperation<List<String>> asyncOperation) {
		api.getGroups(materialID, asyncOperation);
	}

	public boolean owns(Material mat) {
		return api.owns(mat);
	}

	public boolean canUserShare(boolean student) {
		return api.canUserShare(student);
	}

	public boolean anonymousOpen() {
		return api.anonymousOpen();
	}

	public boolean parseUserDataFromResponse(GeoGebraTubeUser user, String result) {
		return api.parseUserDataFromResponse(user, result);
	}

	public boolean performCookieLogin(LogInOperation op) {
		return api.performCookieLogin(op);
	}

	public URLChecker getURLChecker() {
		return api.getURLChecker();
	}
}
