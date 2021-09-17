package com.kite9.server.sources;

import com.kite9.server.domain.RestEntity;
import org.springframework.security.core.Authentication;

public interface DirectoryAPI extends SourceAPI {

	/**
	 * Instead of returning actual content, returns an entity representation of it, for directory listings and so on.
	 */
	public RestEntity getEntityRepresentation(Authentication a) throws Exception;
}
