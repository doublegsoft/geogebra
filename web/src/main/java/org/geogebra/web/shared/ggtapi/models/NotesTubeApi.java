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

	@Override
	public String getLoginUrl() {
		return api.getLoginUrl();
	}

	@Override
	public String getUrl() {
		return api.getUrl();
	}

	@Override
	public void authorizeUser(final GeoGebraTubeUser user, final LogInOperation op,
							  final boolean automatic) {
		api.authorizeUser(user, op, automatic);
	}

	@Override
	public boolean checkAvailable(LogInOperation op) {
		return api.checkAvailable(op);
	}

	@Override
	public void setUserLanguage(String lang, String token) {
		api.setUserLanguage(lang, token);
	}

	@Override
	public void logout(String token) {
		api.logout(token);
	}

	@Override
	public void favorite(int id, boolean favorite) {
		api.favorite(id, favorite);
	}

	@Override
	public void uploadRenameMaterial(Material mat, final MaterialCallbackI cb) {
		api.uploadRenameMaterial(mat, cb);
	}

	@Override
	public void uploadLocalMaterial(final Material mat, final MaterialCallbackI cb) {
		api.uploadLocalMaterial(mat, cb);
	}

	@Override
	public void deleteMaterial(Material material, final MaterialCallbackI cb) {
		api.deleteMaterial(material, cb);
	}

	@Override
	public void shareMaterial(Material material, String to, String message,
							  final MaterialCallbackI cb) {
		api.shareMaterial(material, to, message, cb);
	}

	@Override
	public void getUsersMaterials(MaterialCallbackI cb, MaterialRequest.Order order) {
		api.getUsersMaterialsForNotes(cb);
	}

	@Override
	public void getUsersOwnMaterials(MaterialCallbackI cb, MaterialRequest.Order order) {
		api.getUsersOwnMaterials(cb, order);
	}

	@Override
	public void getSharedMaterials(final MaterialCallbackI cb, MaterialRequest.Order order) {
		api.getSharedMaterials(cb, order);
	}

	@Override
	public void uploadMaterial(String tubeID, String visibility, final String filename,
							   String base64, final MaterialCallbackI cb,
							   Material.MaterialType type) {
		api.uploadMaterial(tubeID, visibility, filename, base64, cb, type);
	}

	@Override
	public void getFeaturedMaterials(MaterialCallbackI callback) {
		api.getFeaturedNotesMaterials(callback);
	}

	@Override
	public void getItem(String id, MaterialCallbackI callback) {
		api.getItem(id, callback);
	}

	@Override
	public boolean isCheckDone() {
		return api.isCheckDone();
	}

	@Override
	public void sync(long timestamp, final SyncCallback cb) {
		api.sync(timestamp, cb);
	}

	@Override
	public void setClient(ClientInfo clientInfo) {
		api.setClient(clientInfo);
	}

	@Override
	public void performTokenLogin(LogInOperation logInOperation, String token) {
		api.performTokenLogin(logInOperation, token);
	}

	@Override
	public void copy(final Material material, final String title,
					 final MaterialCallbackI copyCallback) {
		api.copy(material, title, copyCallback);
	}

	@Override
	public void setShared(Material m, String groupID, boolean shared,
						  AsyncOperation<Boolean> callback) {
		api.setShared(m, groupID, shared, callback);
	}

	@Override
	public void getGroups(String materialID, AsyncOperation<List<String>> asyncOperation) {
		api.getGroups(materialID, asyncOperation);
	}

	@Override
	public boolean owns(Material mat) {
		return api.owns(mat);
	}

	@Override
	public boolean canUserShare(boolean student) {
		return api.canUserShare(student);
	}

	@Override
	public boolean anonymousOpen() {
		return api.anonymousOpen();
	}

	@Override
	public boolean parseUserDataFromResponse(GeoGebraTubeUser user, String result) {
		return api.parseUserDataFromResponse(user, result);
	}

	@Override
	public boolean performCookieLogin(LogInOperation op) {
		return api.performCookieLogin(op);
	}

	@Override
	public URLChecker getURLChecker() {
		return api.getURLChecker();
	}
}
