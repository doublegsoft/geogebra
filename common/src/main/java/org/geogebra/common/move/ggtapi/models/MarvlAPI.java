package org.geogebra.common.move.ggtapi.models;

import java.util.List;

import org.geogebra.common.move.ggtapi.operations.BackendAPI;
import org.geogebra.common.move.ggtapi.operations.LogInOperation;
import org.geogebra.common.move.ggtapi.operations.URLChecker;
import org.geogebra.common.move.ggtapi.requests.MaterialCallbackI;
import org.geogebra.common.move.ggtapi.requests.SyncCallback;
import org.geogebra.common.util.AsyncOperation;

public class MarvlAPI implements BackendAPI {
	@Override
	public void getItem(String id, MaterialCallbackI callback) {

	}

	@Override
	public boolean checkAvailable(LogInOperation logInOperation) {
		return false;
	}

	@Override
	public String getLoginUrl() {
		return null;
	}

	@Override
	public boolean parseUserDataFromResponse(GeoGebraTubeUser user, String loadLastUser) {
		return false;
	}

	@Override
	public void deleteMaterial(Material mat, MaterialCallbackI cb) {

	}

	@Override
	public void authorizeUser(GeoGebraTubeUser user, LogInOperation logInOperation, boolean automatic) {

	}

	@Override
	public void setClient(ClientInfo clientInfo) {

	}

	@Override
	public void sync(long timestamp, SyncCallback syncCallback) {

	}

	@Override
	public boolean isCheckDone() {
		return false;
	}

	@Override
	public void setUserLanguage(String lang, String token) {

	}

	@Override
	public void shareMaterial(Material material, String to, String message, MaterialCallbackI cb) {

	}

	@Override
	public void favorite(int id, boolean favorite) {

	}

	@Override
	public String getUrl() {
		return null;
	}

	@Override
	public void logout(String token) {

	}

	@Override
	public void uploadLocalMaterial(Material mat, MaterialCallbackI cb) {

	}

	@Override
	public boolean performCookieLogin(LogInOperation op) {
		return false;
	}

	@Override
	public void performTokenLogin(LogInOperation logInOperation, String token) {

	}

	@Override
	public void getUsersMaterials(MaterialCallbackI cb, MaterialRequest.Order order) {

	}

	@Override
	public void getFeaturedMaterials(MaterialCallbackI callback) {

	}

	@Override
	public void getUsersOwnMaterials(MaterialCallbackI cb, MaterialRequest.Order order) {

	}

	@Override
	public void getSharedMaterials(MaterialCallbackI cb, MaterialRequest.Order order) {

	}

	@Override
	public void uploadMaterial(String tubeID, String visibility, String filename, String base64, MaterialCallbackI cb, Material.MaterialType type) {

	}

	@Override
	public void uploadRenameMaterial(Material material, MaterialCallbackI callback) {

	}

	@Override
	public void copy(Material material, String title, MaterialCallbackI materialCallback) {

	}

	@Override
	public void setShared(Material material, String groupID, boolean shared, AsyncOperation<Boolean> callback) {

	}

	@Override
	public void getGroups(String materialID, AsyncOperation<List<String>> asyncOperation) {

	}

	@Override
	public boolean owns(Material mat) {
		return false;
	}

	@Override
	public boolean canUserShare(boolean student) {
		return false;
	}

	@Override
	public boolean anonymousOpen() {
		return false;
	}

	@Override
	public URLChecker getURLChecker() {
		return null;
	}
}
